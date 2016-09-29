package cn.edu.uestc.acm.cdoj;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import cn.edu.uestc.acm.cdoj.layout.DetailsContainer;
import cn.edu.uestc.acm.cdoj.layout.ListContainer;
import cn.edu.uestc.acm.cdoj.net.UserManager;
import cn.edu.uestc.acm.cdoj.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.statusBar.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements GetInformation {

    private DetailsContainer detailsContainer;
    private ListContainer listContainer;
    private FragmentManager fragmentManager;
    private boolean isTwoPane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.currentMainActivity = this;
        setContentView(R.layout.activity_main);
        isTwoPane = findViewById(R.id.landAndPadMark) != null;
        Global.isTwoPane = isTwoPane;
        initStatusBar();
        TabLayout bottomTab = (TabLayout) findViewById(R.id.tabLayout_bottom);
        fragmentManager = getFragmentManager();
        if (savedInstanceState == null) {
            Global.userManager = new UserManager(this);
            if (Global.userManager.isLogin()) Global.userManager.keepLogin();
            Global.netContent = new NetContent();
            initViews();
        } else findBackContainerFragment();
        listContainer.setupWithTabLayout(bottomTab);
    }

    @Override
    protected void onRestart() {
        Global.userManager.keepLogin();
        super.onRestart();
    }

    private void initStatusBar() {
        if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white);
            StatusBarUtil.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_gray);
        }
    }

    private void initViews(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isTwoPane) {
            detailsContainer = new DetailsContainer();
            Global.detailsContainer = detailsContainer;
            transaction.add(R.id.details_container, detailsContainer, "detailsContainer");
        }
        listContainer = new ListContainer();
        Global.listContainer = listContainer;
        transaction.add(R.id.list_container, listContainer, "listContainer");
        transaction.commit();
    }

    private void findBackContainerFragment() {
        listContainer = (ListContainer) fragmentManager.findFragmentByTag("listContainer");
        detailsContainer = (DetailsContainer) fragmentManager.findFragmentByTag("detailsContainer");
        if (detailsContainer == null && isTwoPane) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            detailsContainer = new DetailsContainer();
            transaction.add(R.id.details_container, detailsContainer, "detailsContainer");
            transaction.commit();
        }
    }

    @Override
    public boolean isTwoPane() {
        return isTwoPane;
    }

    @Override
    public DetailsContainer getDetailsContainer() {
        return detailsContainer;
    }

    @Override
    public ListContainer getListContainer() {
        return listContainer;
    }

}
