package cn.edu.uestc.acm.cdoj_android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ListContainerFragment;

public class MainActivity extends AppCompatActivity implements GetInformation,Selection {

    private TabLayout tab_bottom;
    private DetailsContainerFragment detailsContainer_Fragment;
    private ListContainerFragment listContainer_Fragment;
    private FragmentManager fragmentManager;
    private Information information;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        if (savedInstanceState == null){
            initViews();
        }else {
            findBackFragment();
        }

    }

    private void findBackFragment() {
        detailsContainer_Fragment = (DetailsContainerFragment) fragmentManager.findFragmentByTag("detailsContainer_Fragment");
        listContainer_Fragment = (ListContainerFragment) fragmentManager.findFragmentByTag("listContainer_Fragment");
    }

    private void initViews(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        detailsContainer_Fragment = new DetailsContainerFragment();
        listContainer_Fragment = new ListContainerFragment();
        transaction.add(R.id.details_container, detailsContainer_Fragment, "detailsContainer_Fragment");
        transaction.add(R.id.list_container, listContainer_Fragment, "listContainer_Fragment");
        transaction.commit();
        information = new Information(listContainer_Fragment,detailsContainer_Fragment);
    }

    @Override
    public void initTab(ViewPager detailsContainer_ViewPager) {
        tab_bottom = (TabLayout)findViewById(R.id.tabLayout_bottom);
        tab_bottom.setupWithViewPager(detailsContainer_ViewPager);
        tab_bottom.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tab_bottom.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        tab_bottom.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
//        tab_bottom.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    @Override
    public void setSelectionList(final int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
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
        transaction.commit();
    }

    @Override
    public Information getInformation() {
        return information;
    }

}
