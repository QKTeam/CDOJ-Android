package cn.edu.uestc.acm.cdoj.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.customtoolbar.ToolBarWithSearch;
import cn.edu.uestc.acm.cdoj.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj.layout.list.ArticleListFragment;
import cn.edu.uestc.acm.cdoj.layout.list.ContestListFragment;
import cn.edu.uestc.acm.cdoj.layout.list.ProblemListFragment;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class ListContainer extends Fragment {
    private ToolBarWithSearch toolBarWithSearch;
    private ArticleListFragment articleList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;
    private User user;
    private DetailsContainer detailsContainer;
    private ViewPager viewPager;
    private TabLayout bottomTab;
    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (Global.detailsContainer != null) detailsContainer = Global.detailsContainer;
            articleList = new ArticleListFragment().refresh();
            problemList = new ProblemListFragment().refresh();
            contestList = new ContestListFragment().refresh();
            user = new User();
            rootView = inflater.inflate(R.layout.list_container, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            toolBarWithSearch = (ToolBarWithSearch) rootView.findViewById(R.id.toolbar_list);
            configureToolBar();
            viewPager = (ViewPager) (rootView.findViewById(R.id.listViewPager));
            configureViewPager();
            if (bottomTab != null) setupTabLayout();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void configureToolBar() {
        toolBarWithSearch.setTitleTextColor(ContextCompat.getColor(rootView.getContext(), R.color.main_blue));
        toolBarWithSearch.setTitle(getString(R.string.article));
        toolBarWithSearch.setSearchListener(new ToolBarWithSearch.ToolBarWithSearchListener() {
            @Override
            public void search(String key) {
                switch (viewPager.getCurrentItem()) {
                    case 1:
                        problemList.setKey(key);
                        problemList.refresh();
                        break;
                    case 2:
                        contestList.setKey(key);
                        contestList.refresh();
                }
            }
        });
        toolBarWithSearch.setCloseListener(new ToolBarWithSearch.OnSearchCloseListener() {
            @Override
            public void recovery() {
                switch (viewPager.getCurrentItem()) {
                    case 1:
                        problemList.setKey(null);
                        problemList.refresh();
                        break;
                    case 2:
                        contestList.setKey(null);
                        contestList.refresh();
                }
            }
        });
    }

    private void configureViewPager() {
        viewPager.setOffscreenPageLimit(2);
        setViewPagerAdapter();
        addViewPagerOnPageChangeListener();
    }

    private void setViewPagerAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return articleList;
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

    private void addViewPagerOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        toolBarWithSearch.setSearchViewEnable(false);
                        toolBarWithSearch.setTitle(getString(R.string.Article));
                        if (detailsContainer != null)
                            detailsContainer.show(ViewHandler.ARTICLE_DETAIL);
                        break;
                    case 1:
                        toolBarWithSearch.setTitle(getString(R.string.problem));
                        toolBarWithSearch.setSearchViewEnable(true);
                        if (detailsContainer != null)
                            detailsContainer.show(ViewHandler.PROBLEM_DETAIL);
                        break;
                    case 2:
                        toolBarWithSearch.setSearchViewEnable(true);
                        toolBarWithSearch.setTitle(getString(R.string.contest));
                        if (detailsContainer != null)
                            detailsContainer.show(ViewHandler.CONTEST_DETAIL);
                        break;
                    case 3:
                        toolBarWithSearch.setSearchViewEnable(false);
                        toolBarWithSearch.setTitle(getString(R.string.user));
                        if (detailsContainer != null)
                            detailsContainer.show(ViewHandler.USER);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomTab = null;
    }

    public void setCurrentItem(int item) {
        if (viewPager != null) {
            viewPager.setCurrentItem(item, false);
        }
    }


    public void setupWithTabLayout(TabLayout bottomTab) {
        this.bottomTab = bottomTab;
        if (viewPager != null) {
            setupTabLayout();
        }
    }

    private void setupTabLayout() {
        bottomTab.setupWithViewPager(viewPager);
        bottomTab.getTabAt(0).setIcon(R.drawable.ic_action_home);
        bottomTab.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        bottomTab.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
        bottomTab.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    @IntDef({ViewHandler.ARTICLE_LIST, ViewHandler.PROBLEM_LIST, ViewHandler.CONTEST_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface lists {
    }

    public ListFragmentWithGestureLoad getList(@lists int which) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                return articleList;
            case ViewHandler.PROBLEM_LIST:
                return problemList;
            case ViewHandler.CONTEST_LIST:
                return contestList;
        }
        return null;
    }
}
