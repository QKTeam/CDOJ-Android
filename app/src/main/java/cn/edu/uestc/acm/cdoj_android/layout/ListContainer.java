package cn.edu.uestc.acm.cdoj_android.layout;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.customtoolbar.ToolBarWithSearch;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.list.ArticleListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ContestListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ProblemListFragment;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;

/**
 * Created by great on 2016/8/18.
 */
public class ListContainer extends Fragment implements ViewHandler{
    private ArticleListFragment articleList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;
    private User user;
    private ViewPager viewPager;
    private TabLayout bottomTab;
    private View rootView;
    private ToolBarWithSearch toolBarWithSearch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            articleList = new ArticleListFragment();
            problemList = new ProblemListFragment();
            contestList = new ContestListFragment();
            user = new User();
            Global.netContent.addListFragment(articleList);
            Global.netContent.addListFragment(problemList);
            Global.netContent.addListFragment(contestList);
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
            if (bottomTab != null) {
                setupTabLayout();
            }
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
                        problemList.continuePullUpLoad();
                        problemList.clearList();
                        NetData.getProblemList(1, key, ListContainer.this);
                        break;
                    case 2:
                        contestList.setKey(key);
                        contestList.continuePullUpLoad();
                        contestList.clearList();
                        NetData.getContestList(1, key, ListContainer.this);
                }
            }
        });
        toolBarWithSearch.setCloseListener(new ToolBarWithSearch.OnSearchCloseListener() {
            @Override
            public void recovery() {
                switch (viewPager.getCurrentItem()) {
                    case 1:
                        problemList.setKey(null);
                        problemList.continuePullUpLoad();
                        problemList.clearList();
                        NetData.getProblemList(1, null, ListContainer.this);
                        break;
                    case 2:
                        contestList.setKey(null);
                        contestList.continuePullUpLoad();
                        contestList.clearList();
                        NetData.getContestList(1, null, ListContainer.this);
                }
            }
        });
    }

    private void configureViewPager() {
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
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

        if (Global.isTwoPane) {
            final DetailsContainer detailsContainer = Global.detailsContainer;
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
                            detailsContainer.show(ViewHandler.ARTICLE_DETAIL);
                            break;
                        case 1:
                            toolBarWithSearch.setTitle(getString(R.string.problem));
                            toolBarWithSearch.setSearchViewEnable(true);
                            detailsContainer.show(ViewHandler.PROBLEM_DETAIL);
                            break;
                        case 2:
                            toolBarWithSearch.setSearchViewEnable(true);
                            toolBarWithSearch.setTitle(getString(R.string.contest));
                            detailsContainer.show(ViewHandler.CONTEST_DETAIL);
                            break;
                        case 3:
                            toolBarWithSearch.setSearchViewEnable(false);
                            toolBarWithSearch.setTitle(getString(R.string.user));
                            detailsContainer.show(ViewHandler.USER);
                            /*if (Global.userManager.isLogin()) {
                                user.setLogin(true);
                            }else {
                                user.setLogin(false);
                            }*/
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
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
                            break;
                        case 1:
                            toolBarWithSearch.setTitle(getString(R.string.problem));
                            toolBarWithSearch.setSearchViewEnable(true);
                            break;
                        case 2:
                            toolBarWithSearch.setSearchViewEnable(true);
                            toolBarWithSearch.setTitle(getString(R.string.contest));
                            break;
                        case 3:
                            toolBarWithSearch.setSearchViewEnable(false);
                            toolBarWithSearch.setTitle(getString(R.string.user));
                            /*if (Global.userManager.isLogin()) {
                                user.setLogin(true);
                            }else {
                                user.setLogin(false);
                            }*/
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
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
        if (viewPager != null) {setupTabLayout();}
    }

    private void setupTabLayout() {
        bottomTab.setupWithViewPager(viewPager);
        bottomTab.getTabAt(0).setIcon(R.drawable.ic_action_home);
        bottomTab.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        bottomTab.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
        bottomTab.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.PROBLEM_LIST:
                if (((InfoList) data).result) {
                    problemList.setPageInfo(((InfoList) data).pageInfo);
                    ArrayList<ProblemInfo> infoList_P = ((InfoList) data).getInfoList();
                    for (ProblemInfo tem : infoList_P) {
                        String number = "" + tem.solved + "/" + tem.tried;
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("title", tem.title);
                        listItem.put("source", tem.source);
                        listItem.put("id", "" + tem.problemId);
                        listItem.put("number", number);
                        problemList.addListItem(listItem);
                    }
                } else {
                    problemList.getDataFailure();
                }
                problemList.notifyDataSetChanged();
                break;

            case ViewHandler.CONTEST_LIST:
                if (((InfoList) data).result) {
                    contestList.setPageInfo(((InfoList) data).pageInfo);
                    ArrayList<ContestInfo> infoList_C = ((InfoList) data).getInfoList();
                    for (ContestInfo tem : infoList_C) {
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("title", tem.title);
                        listItem.put("date", tem.timeString);
                        listItem.put("timeLimit", tem.lengthString);
                        listItem.put("id", "" + tem.contestId);
                        listItem.put("status", tem.status);
                        listItem.put("permission", tem.typeName);
                        contestList.addListItem(listItem);
                    }
                } else {
                    contestList.getDataFailure();
                }
                contestList.notifyDataSetChanged();
                break;
        }

    }

    @IntDef({ViewHandler.ARTICLE_LIST, ViewHandler.PROBLEM_LIST, ViewHandler.CONTEST_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface lists {}

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
