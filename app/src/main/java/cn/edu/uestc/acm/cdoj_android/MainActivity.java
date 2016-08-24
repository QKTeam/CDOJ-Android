package cn.edu.uestc.acm.cdoj_android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.uestc.acm.cdoj_android.layout.details.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListContainerFragment;
import cn.edu.uestc.acm.cdoj_android.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements Selection {

    private TabLayout tab_bottom;
    private DetailsContainerFragment detailsContainer_Fragment;
    private ListContainerFragment listContainer_Fragment;
    private FragmentManager fragmentManager;
    private boolean isTwoPane;
    String TAG = "activitylist";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.currentMainActivity = this;
        setContentView(R.layout.activity_main);
        isTwoPane = findViewById(R.id.landAndPadMark) != null;
        initStatusBar();
        tab_bottom = (TabLayout)findViewById(R.id.tabLayout_bottom);
        fragmentManager = getFragmentManager();
        if (savedInstanceState == null) {
            Global.netContent = new NetContent();
            if (isTwoPane) {
                initViewsTwoPane();
            }else {
                initViewsSinglePane();
            }
        } else {
            findBackContainerFragment();
        }
        setupTabLayout();
    }

    private void initStatusBar() {
        if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe() ||(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)){
            StatusBarUtil.setStatusBarColor(this,R.color.statusBar_background_white);
            StatusBarUtil.StatusBarLightMode(this);
        }
        else {
            StatusBarUtil.setStatusBarColor(this,R.color.statusBar_background_gray);
        }
    }

    private void initViewsTwoPane(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        listContainer_Fragment = new ListContainerFragment();
        detailsContainer_Fragment = new DetailsContainerFragment();
        detailsContainer_Fragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: setSelectionList(0);break;
                    case 1: setSelectionList(1);break;
                    case 2: setSelectionList(2);break;
//                    default: setSelection(3);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        transaction.add(R.id.details_container, detailsContainer_Fragment, "detailsContainer_Fragment");
        transaction.add(R.id.list_container, listContainer_Fragment, "listContainer_Fragment");
        transaction.commit();
    }

    private void initViewsSinglePane() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        listContainer_Fragment = new ListContainerFragment();
        listContainer_Fragment.setScrollable(true);
        transaction.add(R.id.list_container, listContainer_Fragment, "listContainer_Fragment");
        transaction.commit();
    }

    private void setupTabLayout() {
        if (isTwoPane) {
            detailsContainer_Fragment.setupWithTabLayout(tab_bottom);
            detailsContainer_Fragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
                @Override
                public void onPageSelected(int position) {
                    switch (position){
                        case 0: setSelectionList(0);break;
                        case 1: setSelectionList(1);break;
                        case 2: setSelectionList(2);break;
//                    default: setSelection(3);
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {}
            });
            return;
        }
        listContainer_Fragment.setupWithTabLayout(tab_bottom);
    }

    private void findBackContainerFragment() {
        listContainer_Fragment = (ListContainerFragment) fragmentManager.findFragmentByTag("listContainer_Fragment");
        if (isTwoPane) {
            if (detailsContainer_Fragment == null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                detailsContainer_Fragment = new DetailsContainerFragment();
                detailsContainer_Fragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
                    @Override
                    public void onPageSelected(int position) {
                        switch (position){
                            case 0: setSelectionList(0);break;
                            case 1: setSelectionList(1);break;
                            case 2: setSelectionList(2);break;
//                    default: setSelection(3);
                        }
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });
                transaction.add(R.id.details_container, detailsContainer_Fragment, "detailsContainer_Fragment");
                transaction.commit();
            }else {
                detailsContainer_Fragment = (DetailsContainerFragment) fragmentManager.findFragmentByTag("detailsContainer_Fragment");
            }
        }
    }

    @Override
    public boolean isTwoPane() {
        return isTwoPane;
    }

    @Override
    public DetailsContainerFragment getDetailsContainer() {
        return detailsContainer_Fragment;
    }

    @Override
    public ListContainerFragment getListContainer() {
        return listContainer_Fragment;
    }

    @Override
    public void setSelectionList(final int position) {
        switch (position){
            case 0:
                listContainer_Fragment.setCurrentItem(0);
                break;
            case 1:
                listContainer_Fragment.setCurrentItem(1);
                break;
            case 2:
                listContainer_Fragment.setCurrentItem(2);
                break;
        }
    }
}
