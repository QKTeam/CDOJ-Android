package cn.edu.uestc.acm.cdoj.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.UserManager;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestListFragment;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.MainList;
import cn.edu.uestc.acm.cdoj.ui.notice.NoticeListFragment;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemListFragment;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;
import cn.edu.uestc.acm.cdoj.ui.user.User;


/**
 * Created by Grea on 2016/10/3.
 */

public class MainUIActivity extends AppCompatActivity implements ViewHandler {

    public static final int NOTICELIST = 0;
    public static final int PROBLEMLIST = 1;
    public static final int CONTESTLIST = 2;
    public static final int SELECT = 0;
    public static final int NOTSELECT = 1;

    @IntDef({NOTICELIST, PROBLEMLIST, CONTESTLIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface list {
    }

    @IntDef({SELECT, NOTSELECT})
    @Retention(RetentionPolicy.SOURCE)
    @interface selectStatus {
    }

    private int[] listInts = new int[]{NOTICELIST, PROBLEMLIST, CONTESTLIST};
    private NoticeListFragment noticeList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;
    private User user;
    private Toolbar mToolbar;
    private ImageButton[] bottomButtons = new ImageButton[4];
    private FloatingSearchView mSearchView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mSearchFrameLayout;
    private MainList searchResult;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_main);
        mToolbar = (Toolbar) findViewById(R.id.ui_main_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.ui_main_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.ui_main_nav_view);
        mSearchView = (FloatingSearchView) findViewById(R.id.ui_main_floating_search_view);
        mSearchFrameLayout = (FrameLayout) findViewById(R.id.ui_main_search_frame);
        mViewPager = (ViewPager) findViewById(R.id.ui_main_list_ViewPager);
        Global.currentMainUIActivity = this;
        Global.isTwoPane = findViewById(R.id.landAndPadMark) != null;
        Global.userManager = new UserManager(this);
        if (Global.userManager.isLogin()) {
            Global.userManager.keepLogin();
        }
        initViews();
    }

    @Override
    protected void onRestart() {
        Global.userManager.keepLogin();
        super.onRestart();
    }

    private void initViews() {
        mSearchFrameLayout.setVisibility(View.INVISIBLE);
        mToolbar.setTitle(getString(R.string.notice));
        noticeList = new NoticeListFragment().refresh();
        problemList = new ProblemListFragment().refresh();
        contestList = new ContestListFragment().refresh();
        user = new User();
        configStatusBar();
        configSearchView();
        configureViewPager((ViewPager) findViewById(R.id.ui_main_list_ViewPager));
        if (Global.isTwoPane) configTwoPane();
    }

    private void configStatusBar() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.ui_main_drawer_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.StatusBarLightMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.setStatusBarBackground(R.color.statusBar_background_gray);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            drawerLayout.setFitsSystemWindows(false);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ui_main_content);
            linearLayout.setFitsSystemWindows(true);
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
            }
        }
    }

    private void configSearchView() {
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        searchResult = new ProblemListFragment();
                        break;
                    case 2:
                        searchResult = new ContestListFragment();
                        break;
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_main_search_result, (Fragment) searchResult)
                        .commit();
                searchResult.setProgressContainerVisibility(View.INVISIBLE);
                searchResult.setRefreshProgressListener(new MainList.OnRefreshProgressListener() {
                    @Override
                    public void start() {
                        mSearchView.showProgress();
                    }

                    @Override
                    public void end() {
                        mSearchView.hideProgress();
                    }
                });
            }

            @Override
            public void onFocusCleared() {
                mSearchView.clearQuery();
                getFragmentManager()
                        .beginTransaction()
                        .remove((Fragment) searchResult)
                        .commit();
            }
        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        ((ProblemListFragment) searchResult).setKey(newQuery);
                        ((ProblemListFragment) searchResult).refresh();
                        break;
                    case 2:
                        ((ContestListFragment) searchResult).setKey(newQuery);
                        ((ContestListFragment) searchResult).refresh();
                }
            }
        });


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
            }
        });
    }

    private void configureViewPager(ViewPager mViewPager) {
        mViewPager.setOffscreenPageLimit(2);
        setViewPagerAdapter(mViewPager);
        addViewPagerOnPageChangeListener(mViewPager);
        configBottomButton(mViewPager);
    }

    private void setViewPagerAdapter(ViewPager mViewPager) {
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return noticeList;
                    case 1:
                        return problemList;
                    case 2:
                        return contestList;
                    case 3:
                        return user;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

    private void addViewPagerOnPageChangeListener(final ViewPager mViewPager) {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mSearchFrameLayout.setVisibility(View.INVISIBLE);
                        changeButtonColor(NOTICELIST);
                        break;
                    case 1:
                        mSearchFrameLayout.setVisibility(View.VISIBLE);
                        changeButtonColor(PROBLEMLIST);
                        break;
                    case 2:
                        mSearchFrameLayout.setVisibility(View.VISIBLE);
                        changeButtonColor(CONTESTLIST);
                        break;
                    case 3:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void configTwoPane() {
        ImageView bgImageView = (ImageView) findViewById(R.id.main_detail_bg);
        float[] colorMatrix = new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 0.7f, 0};
        bgImageView.setImageBitmap(DrawImage.draw(this, R.drawable.logo_orange, colorMatrix));
    }

    private void configBottomButton(final ViewPager mViewPager) {
        ImageButton buttonNotice = (ImageButton) findViewById(R.id.ui_main_button_notice);
        bottomButtons[0] = buttonNotice;
        buttonNotice.setImageBitmap(getIcon(NOTICELIST, SELECT));
        buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(NOTICELIST);
                mViewPager.setCurrentItem(NOTICELIST);
            }
        });

        ImageButton buttonProblem = (ImageButton) findViewById(R.id.ui_main_button_problem);
        bottomButtons[1] = buttonProblem;
        buttonProblem.setImageBitmap(getIcon(PROBLEMLIST, NOTSELECT));
        buttonProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(PROBLEMLIST);
                mViewPager.setCurrentItem(PROBLEMLIST);
            }
        });

        ImageButton buttonContest = (ImageButton) findViewById(R.id.ui_main_button_contest);
        bottomButtons[2] = buttonContest;
        buttonContest.setImageBitmap(getIcon(CONTESTLIST, NOTSELECT));
        buttonContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(CONTESTLIST);
                mViewPager.setCurrentItem(CONTESTLIST);
            }
        });
    }

    private void changeButtonColor(@list int currentPage) {
        for (int page : listInts) {
            if (page == currentPage) {
                bottomButtons[page].setImageBitmap(getIcon(page, SELECT));
                continue;
            }
            bottomButtons[page].setImageBitmap(getIcon(page, NOTSELECT));
        }
    }

    private Bitmap getIcon(@list int whichButton, @selectStatus int selectStatus) {
        switch (whichButton) {
            case NOTICELIST:
                switch (selectStatus) {
                    case SELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_notice_selected, true);
                    case NOTSELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_notice, true);
                }
                break;
            case PROBLEMLIST:
                switch (selectStatus) {
                    case SELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_problem_selected, true);
                    case NOTSELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_problem, true);
                }
                break;
            case CONTESTLIST:
                switch (selectStatus) {
                    case SELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_contestt_selected, true);
                    case NOTSELECT:
                        return DrawImage.draw(this, R.drawable.ic_list_contest, true);
                }
                break;
        }
        return null;
    }

    @Override
    public void show(int which, Object data, long time) {
        Object[] dataReceive = (Object[]) data;
        switch (which) {
            case ViewHandler.AVATAR:
                ((ImageView) findViewById(R.id.main_ui_nav_view_image)).setImageBitmap((Bitmap) dataReceive[1]);
        }
    }
}
