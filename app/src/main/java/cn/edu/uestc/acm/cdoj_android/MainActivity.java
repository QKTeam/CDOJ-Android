package cn.edu.uestc.acm.cdoj_android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj_android.layout.ArticleListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ContestListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ProblemListFragment;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.NetData_1;

public class MainActivity extends AppCompatActivity implements Selection,GetInformation,ShowTestText {

    private TabLayout tab_bottom;
    private DetailsContainerFragment detailsContainer_Fragment;
    private ListFragment[] list_Fragment;
    private FragmentManager fragmentManager;
    private Information information;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("进行", "onCreate: ");
        fragmentManager = getFragmentManager();
        list_Fragment = new ListFragment[3];
        if (savedInstanceState == null){
            initViews();
        }else {
            findBackFragment();
        }

    }

    private void findBackFragment() {
        detailsContainer_Fragment = (DetailsContainerFragment)fragmentManager.findFragmentByTag("detailsContainer_Fragment");
        detailsContainer_Fragment.addSelection(this);
        for (int i = 0; i != 3; ++i) {
            list_Fragment[i] = (ListFragment)fragmentManager.findFragmentByTag("list_Fragment"+i);
        }
    }

    private void initViews(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        detailsContainer_Fragment = new DetailsContainerFragment();
        detailsContainer_Fragment.addSelection(this);
        transaction.add(R.id.details_container,detailsContainer_Fragment,"detailsContainer_Fragment");
        for (int i = 0; i != 3; ++i) {
            switch (i) {
                case 0:
                    list_Fragment[i] = (new ArticleListFragment()).createAdapter(this);
                    break;
                case 1:
                    list_Fragment[i] = (new ProblemListFragment()).createAdapter(this);
                    break;
                case 2:
                    list_Fragment[i] = (new ContestListFragment()).createAdapter(this);
                    break;
            }
            transaction.add(R.id.list_main,list_Fragment[i],"list_Fragment"+i);
        }
        information = new Information(this,list_Fragment,detailsContainer_Fragment);
        NetData.getArticleList(1, information);
        NetData.getProblemList(1, "", information);
        NetData.getContestList(1, "", information);
        transaction.commit();
        setDefaultFragment();
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
    private void setDefaultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(list_Fragment[0]);
        transaction.commit();
    }

    @Override
    public void setSelectionList(final int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideListFragment();
        switch (position){
            case 0:
                transaction.show(list_Fragment[position]);
            case 1:
                transaction.show(list_Fragment[position]);
                break;
            case 2:
                transaction.show(list_Fragment[position]);
                break;
        }
        transaction.commit();
    }

    private void hideListFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i != 3; ++i) {
            transaction.hide(list_Fragment[i]);
        }
        transaction.commit();
    }

    @Override
    public Information getInformation() {
        return information;
    }

    @Override
    public void showTestText(String str) {
        ((TextView)findViewById(R.id.testTextView)).setText(str);
    }
}
