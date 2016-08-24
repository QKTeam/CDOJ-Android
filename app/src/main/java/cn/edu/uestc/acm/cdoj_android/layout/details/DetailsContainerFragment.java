package cn.edu.uestc.acm.cdoj_android.layout.details;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.Selection;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class DetailsContainerFragment extends Fragment {
    private DetailsWebViewFragment[] details_Fragment;
    private ViewPager detailsContainer_ViewPager;
    private boolean isScroll;
    private TabLayout tabLayout;
    private boolean isSetupTabLayout;
    private int which = -1;
    private String JSData;
    private int currentItem;
    private ViewPager.OnPageChangeListener onPageChangeListener;
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
            details_Fragment = new DetailsWebViewFragment[3];
            for (int i = 0; i != 3; ++i) {
                details_Fragment[i] = new DetailsWebViewFragment();
                switch (i) {
                    case 0:
                        details_Fragment[i].switchHTMLData(ViewHandler.ARTICLE_DETAIL);
                        break;
                    case 1:
                        details_Fragment[i].switchHTMLData(ViewHandler.PROBLEM_DETAIL);
                        break;
                    case 2:
                        details_Fragment[i].switchHTMLData(ViewHandler.CONTEST_DETAIL);
                        break;
                }
            }
            Global.netContent.addDetailsFragments(details_Fragment);
            realView = inflater.inflate(R.layout.details_container, container, false);
        }
        return realView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            if (which != -1) {details_Fragment[which].addJSData(JSData);}
            detailsContainer_ViewPager = (ViewPager) (getView().findViewById(R.id.detailsViewPager));
            detailsContainer_ViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        case 0:
                            return details_Fragment[0];
                        case 1:
                            return details_Fragment[1];
                        case 2:
                            return details_Fragment[2];
                        default:
                            return null;
                    }
                }
                @Override
                public int getCount() {
                    return 3;
                }
            });
            detailsContainer_ViewPager.setOffscreenPageLimit(2);
            if (onPageChangeListener != null) {
                detailsContainer_ViewPager.addOnPageChangeListener(onPageChangeListener);
            }
            if (isSetupTabLayout && ((Selection) Global.currentMainActivity).isTwoPane()) {setupTabLayout();}
        }

    }

    @Override
    public void onDestroyView() {
        isSetupTabLayout = false;
        onPageChangeListener = null;
        super.onDestroyView();
    }

    public DetailsContainerFragment setScrollAble(boolean isScroll) {
        this.isScroll = isScroll;
        return this;
    }

    public DetailsContainerFragment setCurrentItem(int position) {
        if (detailsContainer_ViewPager == null) {
            currentItem = position;
            return this;
        }
        detailsContainer_ViewPager.setCurrentItem(position);
        return this;
    }

    public void setupWithTabLayout(TabLayout tabLayout) {
        isSetupTabLayout = true;
        this.tabLayout = tabLayout;
        if (detailsContainer_ViewPager != null) {
            setupTabLayout();
        }
    }

    private void setupTabLayout() {
        Log.d("绑定tab", "setupTabLayout: ");
        tabLayout.setupWithViewPager(detailsContainer_ViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
//        tab_bottom.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (detailsContainer_ViewPager != null) {
            detailsContainer_ViewPager.addOnPageChangeListener(onPageChangeListener);
            return;
        }
        this.onPageChangeListener = onPageChangeListener;
    }

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL, ViewHandler.CONTEST_DETAIL})
    @Retention(RetentionPolicy.SOURCE)
    @interface details {}

    public DetailsWebViewFragment getDetailsFragment(@details int which) {
        switch (which) {
            case ViewHandler.ARTICLE_DETAIL:
                return details_Fragment[0];
            case ViewHandler.PROBLEM_DETAIL:
                return details_Fragment[1];
            case ViewHandler.CONTEST_DETAIL:
                return details_Fragment[2];
        }
        return null;
    }


}
