package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj_android.GetInformation;
import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.list.ArticleListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ContestListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ProblemListFragment;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class ListContainer extends Fragment {
    private ArticleListFragment articleList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;
    private ViewPager viewPager;
    private TabLayout bottomTab;
    private View rootView;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private DetailsContainer detailsContainer;

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
            Global.netContent.addList(articleList);
            Global.netContent.addList(problemList);
            Global.netContent.addList(contestList);
            rootView = inflater.inflate(R.layout.list_container, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            viewPager = (ViewPager) (rootView.findViewById(R.id.listViewPager));
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
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return 3;
                }
            });
        }
        if (onPageChangeListener != null) {viewPager.addOnPageChangeListener(onPageChangeListener);}
        if (bottomTab != null) {setupTabLayout();}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        detailsContainer = ((GetInformation) Global.currentMainActivity).getDetailsContainer();
        boolean isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
        if (isTwoPane && detailsContainer != null) {
            if (onPageChangeListener == null) {
                onPageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        detailsContainer.setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };
            }
            viewPager.addOnPageChangeListener(onPageChangeListener);
        } else {viewPager.removeOnPageChangeListener(onPageChangeListener);}
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
//        bottomTab.getTabAt(3).setIcon(R.drawable.ic_action_user);
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
