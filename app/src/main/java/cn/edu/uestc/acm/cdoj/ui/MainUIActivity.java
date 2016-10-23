package cn.edu.uestc.acm.cdoj.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.NetDataPlus;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestListFragment;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.user.UserInfo;
import cn.edu.uestc.acm.cdoj.ui.user.UserInfoManager;
import cn.edu.uestc.acm.cdoj.ui.modules.list.MainList;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistory;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistoryManager;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchResult;
import cn.edu.uestc.acm.cdoj.ui.notice.NoticeListFragment;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemListFragment;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;


/**
 * Created by Grea on 2016/10/3.
 */

public class MainUIActivity extends AppCompatActivity implements  {

    public static final int NOTICELIST = 0;
    public static final int PROBLEMLIST = 1;
    public static final int CONTESTLIST = 2;
    public static final int SELECT = 0;
    public static final int NOTSELECT = 1;

    public void loginOrExit(View view) {
        if (Global.userManager.isLogin()) {
            Global.userManager.logout(new UserInfoManager());
            ((ImageView) view).setImageResource(R.drawable.logo_orange);
            ((TextView) findViewById(R.id.main_ui_nav_view_nickName)).setText("");
            ((TextView) findViewById(R.id.main_ui_nav_view_userName)).setText("");
            new AlertDialog.Builder(this)
                    .setTitle("已退出登录")
                    .setNeutralButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }else{
            Intent intent = new Intent(MainUIActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @IntDef({NOTICELIST, PROBLEMLIST, CONTESTLIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface list {}
    @IntDef({SELECT, NOTSELECT})
    @Retention(RetentionPolicy.SOURCE)
    @interface selectStatus {

    }
    private int[] listInts = new int[]{NOTICELIST, PROBLEMLIST, CONTESTLIST};

    private NoticeListFragment noticeList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;
    private Toolbar mToolbar;
    private ImageButton[] bottomButtons = new ImageButton[4];
    private FloatingSearchView mSearchView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mSearchFrameLayout;
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
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupNavigationView();
    }

    @Override
    protected void onRestart() {
        Global.userManager.keepLogin();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        SearchHistoryManager.addSuggestion("problem", Global.problemSearchHistory, true);
        SearchHistoryManager.addSuggestion("contest", Global.contestSearchHistory, true);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {
        mSearchFrameLayout.setVisibility(View.GONE);
        mToolbar.setTitle(getString(R.string.notice));
        setupList();
        setupSystemBar();
        setupSearchView();
        setupViewPager();
        if (Global.isTwoPane) setupTwoPane();
    }

    private void setupList() {
        noticeList = new NoticeListFragment();
        problemList = new ProblemListFragment();
        contestList = new ContestListFragment();
        NetDataPlus.getArticleList(this, 1, true, noticeList);
        NetDataPlus.getProblemList(this, 1, true, problemList);
        NetDataPlus.getContestList(this, 1, true, contestList);
    }

    private void setupSystemBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.ui_main_drawer_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.StatusBarLightMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
                drawerLayout.setStatusBarBackground(R.color.statusBar_background_white);
            }else {
                drawerLayout.setStatusBarBackground(R.color.statusBar_background_gray);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            drawerLayout.setFitsSystemWindows(false);
            FrameLayout linearLayout = (FrameLayout) findViewById(R.id.ui_main_content);
            linearLayout.setFitsSystemWindows(true);
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
            }
        }
    }

    private void setupNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (UserInfoManager.hasUserInfo()) {
                    UserInfo userInfo = UserInfoManager.getUserInfo();
                    ((TextView) findViewById(R.id.main_ui_nav_view_nickName)).setText(userInfo.getNickName());
                    ((TextView) findViewById(R.id.main_ui_nav_view_userName)).setText(userInfo.getUserName());
                    ((ImageView) findViewById(R.id.main_ui_nav_view_header)).setImageBitmap(userInfo.getHeader());
                }
            }
        };
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        NavigationView a;
    }

    private void setupSearchView() {

        mSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        Collections.reverse(Global.problemSearchHistory);
                        mSearchView.swapSuggestions(Global.problemSearchHistory);
                        break;
                    case 2:
                        Collections.reverse(Global.contestSearchHistory);
                        mSearchView.swapSuggestions(Global.contestSearchHistory);
                }
            }

            @Override
            public void onFocusCleared() {
                mSearchView.clearQuery();
            }
        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    switch (mViewPager.getCurrentItem()) {
                        case 1:
                            Collections.reverse(Global.problemSearchHistory);
                            mSearchView.swapSuggestions(Global.problemSearchHistory);
                            break;
                        case 2:
                            Collections.reverse(Global.contestSearchHistory);
                            mSearchView.swapSuggestions(Global.contestSearchHistory);
                    }
                }else {
                    ArrayList<SearchHistory> histories = null;
                    switch (mViewPager.getCurrentItem()) {
                        case 1:
                            histories = Global.problemSearchHistory;
                            break;
                        case 2:
                            histories = Global.contestSearchHistory;
                    }
                    SearchHistoryManager.findMatchHistories(histories, newQuery, 5, new SearchHistoryManager.OnFindHistoriesListener() {
                        @Override
                        public void onResults(ArrayList<SearchHistory> results) {
                            Collections.reverse(results);
                            mSearchView.swapSuggestions(results);
                        }
                    });
                }
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                showSearchResult(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        Global.problemSearchHistory.add(new SearchHistory(currentQuery));
                        break;
                    case 2:
                        Global.contestSearchHistory.add(new SearchHistory(currentQuery));
                }
                showSearchResult(currentQuery);
            }
        });

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));
                leftIcon.setAlpha(.36f);
                textView.setTextColor(Color.parseColor("#000000"));
            }

        });
    }

    private void showSearchResult(String str) {
        Intent intent = new Intent(MainUIActivity.this, SearchResult.class);
        intent.putExtra("key", str);
        switch (mViewPager.getCurrentItem()) {
            case 1:
                if (isId(str)) {
                    intent.putExtra("problemId", Integer.valueOf(str.replaceFirst("#", "")));
                }
                intent.putExtra("type", ViewHandler.PROBLEM_LIST);
                break;
            case 2:
                intent.putExtra("type", ViewHandler.CONTEST_LIST);
        }
        startActivity(intent);
    }

    private boolean isId(String string) {
        Pattern pattern = Pattern.compile("^#[0-9]*");
        return pattern.matcher(string).matches();
    }

    private void setupViewPager() {
        mViewPager.setOffscreenPageLimit(2);
        setViewPagerAdapter();
        addViewPagerOnPageChangeListener();
        setupBottomButton();
    }

    private void setViewPagerAdapter() {
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
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    private void addViewPagerOnPageChangeListener() {
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
                        mSearchView.setSearchHint(getString(R.string.searchWithId));
                        changeButtonColor(PROBLEMLIST);
                        break;
                    case 2:
                        mSearchFrameLayout.setVisibility(View.VISIBLE);
                        mSearchView.setSearchHint(getString(R.string.search));
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

    private void setupTwoPane() {
        ImageView bgImageView = (ImageView) findViewById(R.id.main_detail_bg);
        float[] colorMatrix = RGBAColor.getColorMatrixWithPercentAlpha(0, 0, 0, 0.7, true);
        bgImageView.setImageBitmap(DrawImage.draw(this, R.drawable.logo_orange, colorMatrix));
    }

    private void setupBottomButton() {
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
}
