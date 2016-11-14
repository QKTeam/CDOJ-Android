package cn.edu.uestc.acm.cdoj.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.Collections;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestList;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistory;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistoryManager;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchResultActivity;
import cn.edu.uestc.acm.cdoj.ui.notice.NoticeList;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemList;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;


/**
 * Created by Grea on 2016/10/3.
 */

public class  MainUIActivity extends AppCompatActivity {

    public static final int NOTICELIST = 0;
    public static final int PROBLEMLIST = 1;
    public static final int CONTESTLIST = 2;
    private static final String TAG = "主界面";

    private Toolbar mToolbar;
    private FloatingSearchView mSearchView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View[] listViews;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_main);
        setupSystemBar();
        mToolbar = (Toolbar) findViewById(R.id.ui_main_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.ui_main_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.ui_main_nav_view);
        mSearchView = (FloatingSearchView) findViewById(R.id.ui_main_floating_search_view);
        mViewPager = (ViewPager) findViewById(R.id.ui_main_list_ViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.ui_main_tabLayout);
        Resource.setIsTwoPane(false);
        initViews();
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

    @Override
    protected void onDestroy() {
        SearchHistoryManager.addSuggestion("problem", Resource.getProblemSearchHistory(), true);
        SearchHistoryManager.addSuggestion("contest_button_container", Resource.getContestSearchHistory(), true);
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
        mSearchView.setVisibility(View.GONE);
        mToolbar.setTitle(getString(R.string.notice));
        setupList();
        setupSearchView();
        setupViewPager();
        setupNavigationView();
        setupTabLayout();
    }

    private void setupList() {
        NoticeList noticeList = new NoticeList(this);
        ProblemList problemList = new ProblemList(this);
        ContestList contestList = new ContestList(this);

        listViews = new View[]{noticeList, problemList, contestList};
        NetDataPlus.getArticleList(this, 1, noticeList);
        NetDataPlus.getProblemList(this, 1, problemList);
        NetDataPlus.getContestList(this, 1, contestList);
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(Resource.getNoticeListIcon_selected());
        mTabLayout.getTabAt(1).setIcon(Resource.getProblemListIcon_unselect());
        mTabLayout.getTabAt(2).setIcon(Resource.getContestListIcon_unselect());
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(Resource.getNoticeListIcon_selected());
                        break;
                    case 1:
                        tab.setIcon(Resource.getProblemListIcon_selected());
                        break;
                    case 2:
                        tab.setIcon(Resource.getContestListIcon_selected());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(Resource.getNoticeListIcon_unselect());
                        break;
                    case 1:
                        tab.setIcon(Resource.getProblemListIcon_unselect());
                        break;
                    case 2:
                        tab.setIcon(Resource.getContestListIcon_unselect());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
        NavigationView a;
        mNavigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_user){
                    Log.d("nav_item", "onNavigationItemSelected: ");
                    startUserActivity();
                }
                return true;
            }
        });
    }

    private void setupSearchView() {
        mSearchView.setSearchHint(getString(R.string.searchWithId));

        mSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                switch (mViewPager.getCurrentItem()) {
                    case 1:
                        Collections.reverse(Resource.getProblemSearchHistory());
                        mSearchView.swapSuggestions(Resource.getProblemSearchHistory());
                        break;
                    case 2:
                        Collections.reverse(Resource.getContestSearchHistory());
                        mSearchView.swapSuggestions(Resource.getContestSearchHistory());
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
                            Collections.reverse(Resource.getProblemSearchHistory());
                            mSearchView.swapSuggestions(Resource.getProblemSearchHistory());
                            break;
                        case 2:
                            Collections.reverse(Resource.getContestSearchHistory());
                            mSearchView.swapSuggestions(Resource.getContestSearchHistory());
                    }
                }else {
                    List<SearchSuggestion> histories = null;
                    switch (mViewPager.getCurrentItem()) {
                        case 1:
                            histories = Resource.getProblemSearchHistory();
                            break;
                        case 2:
                            histories = Resource.getContestSearchHistory();
                    }
                    SearchHistoryManager.findMatchHistories(histories, newQuery, 5, new SearchHistoryManager.OnFindHistoriesListener() {
                        @Override
                        public void onResults(List<SearchSuggestion> results) {
                            Collections.reverse(results);
                            mSearchView.swapSuggestions(results);
                        }
                    });
                }
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(com.arlib.floatingsearchview.suggestions.model.SearchSuggestion searchSuggestion) {
                showSearchResult(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                switch (mViewPager.getCurrentItem()) {
                    case PROBLEMLIST:
                        Resource.getProblemSearchHistory().add(new SearchHistory(currentQuery));
                        SearchHistoryManager.addSuggestion("problem", currentQuery);
                        break;
                    case CONTESTLIST:
                        Resource.getContestSearchHistory().add(new SearchHistory(currentQuery));
                        SearchHistoryManager.addSuggestion("contest", currentQuery);
                }
                showSearchResult(currentQuery);
            }
        });

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, com.arlib.floatingsearchview.suggestions.model.SearchSuggestion item, int itemPosition) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));
                leftIcon.setAlpha(.36f);
                textView.setTextColor(Color.parseColor("#000000"));
            }

        });
    }

    private void showSearchResult(String str) {
        Intent intent = new Intent(MainUIActivity.this, SearchResultActivity.class);
        intent.putExtra("keyword", str);
        intent.putExtra("page", mViewPager.getCurrentItem());
        startActivity(intent);
    }

    private void setupViewPager() {
        mViewPager.setOffscreenPageLimit(2);
        setViewPagerAdapter();
        addViewPagerOnPageChangeListener();
    }

    private void setViewPagerAdapter() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(listViews[position]);
                return listViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(listViews[position]);
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
                        mSearchView.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mSearchView.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mSearchView.setVisibility(View.VISIBLE);
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
        /*ImageView bgImageView = (ImageView) findViewById(R.id.main_detail_bg);
        float[] colorMatrix = RGBAColor.getColorMatrixWithPercentAlpha(0, 0, 0, 0.7f, true);
        bgImageView.setImageBitmap(DrawImage.draw(this, R.drawable.logo_orange, colorMatrix));*/
    }
    private void startUserActivity(){
        Intent intent = new Intent(this,AboutMeActivity.class);
        startActivity(intent);
    }
}
