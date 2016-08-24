package cn.edu.uestc.acm.cdoj_android.layout.list;

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

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class ListContainerFragment extends Fragment {
    ListFragmentWithGestureLoad[] list_Fragment;
    ViewPager listContainer_ViewPager;
    private boolean isSetupTabLayout;
    private TabLayout tabLayout;
    private boolean isScrollable;
    private View realView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            list_Fragment = new ListFragmentWithGestureLoad[3];
            for (int i = 0; i != 3; ++i) {
                switch (i) {
                    case 0:
                        list_Fragment[i] = new ArticleListFragment();
                        break;
                    case 1:
                        list_Fragment[i] = new ProblemListFragment();
                        break;
                    case 2:
                        list_Fragment[i] = new ContestListFragment();
                }
            }
            Global.netContent.addListFragments(list_Fragment);
            realView = inflater.inflate(R.layout.list_container, container, false);
        }
        return realView;
//        return inflater.inflate(R.layout.list_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            listContainer_ViewPager = (ViewPager) (getView().findViewById(R.id.listViewPager));
            listContainer_ViewPager.setOffscreenPageLimit(2);
            listContainer_ViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        case 0:
                            return list_Fragment[0];
                        case 1:
                            return list_Fragment[1];
                        case 2:
                            return list_Fragment[2];
                        default:
                            return null;
                    }
                }
                @Override
                public int getCount() {return 3;}
            });
        }
        if (isSetupTabLayout) {setupTabLayout();}
    }

    public void setCurrentItem(int item) {
        if (listContainer_ViewPager != null) {
            listContainer_ViewPager.setCurrentItem(item, false);
        }
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public void setupWithTabLayout(TabLayout tabLayout) {
        isSetupTabLayout = true;
        this.tabLayout = tabLayout;
        if (listContainer_ViewPager != null) {
            setupTabLayout();
        }
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(listContainer_ViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
//        tab_bottom.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    @Override
    public void onDestroyView() {
        isSetupTabLayout = false;
        super.onDestroyView();
    }

    @IntDef({ViewHandler.ARTICLE_LIST, ViewHandler.PROBLEM_LIST, ViewHandler.CONTEST_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface lists {}

    public ListFragmentWithGestureLoad getListFragment(@lists int which) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                return list_Fragment[0];
            case ViewHandler.PROBLEM_LIST:
                return list_Fragment[1];
            case ViewHandler.CONTEST_LIST:
                return list_Fragment[2];
        }
        return null;
    }
}
