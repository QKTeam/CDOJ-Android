package cn.edu.uestc.acm.cdoj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;
import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;
import cn.edu.uestc.acm.cdoj.net.user.UserInfoReceived;
import cn.edu.uestc.acm.cdoj.ui.ViewPagerAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.ArticleListData;
import cn.edu.uestc.acm.cdoj.ui.data.ContestListData;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemListData;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ArticleDetailFrg;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ContestDetailFrg;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ProblemDetailFrg;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.ImageUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GeneralFragment.TransItemDataListener,UserInfoCallback {

    private static final String TAG = "MainActivity";
    public static  boolean isLogin = false;
    private static final String userName = "";

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragment_list;

    private String[] tab_main_item = {"公告","题目","比赛"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CookieSyncManager.createInstance(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        initDrawer();
        initViewPager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //绘制ToolBar上面的menu
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //ToolBar工具栏点击事件
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_rate:

        }
        return true;
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView avatar = navigationView.getHeaderView(0).findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if (resultCode==RESULT_OK){
                    String username = data.getStringExtra("username");
                    isLogin = true;
                    UserConnection.getInstance().getUserInfo(MainActivity.this,username,this,120);
                }
        }
    }

    private void initViewPager() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);

        fragment_list = new ArrayList<>();
        fragment_list.add(initArticleFragment(MainActivity.this));
        fragment_list.add(initProblemFragment(MainActivity.this));
        fragment_list.add(initContestFragment(MainActivity.this));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragment_list,tab_main_item);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private GeneralFragment initArticleFragment(Context context){
        GeneralFragment articleFragment = new GeneralFragment(context, "article_fragment");
        new ArticleListData(context).setUpList(articleFragment);
        return articleFragment;
    }

    private GeneralFragment initProblemFragment(Context context){
        GeneralFragment problemFragment = new GeneralFragment(context, "problem_fragment");
        new ProblemListData(context).setUpList(problemFragment);
        return problemFragment;
    }

    private GeneralFragment initContestFragment(Context context){
        GeneralFragment contestFragment = new GeneralFragment(context, "contest_fragment");
        new ContestListData(context).setUpList(contestFragment);
        return contestFragment;
    }

    @Override
    public void onTranItemData(int position, String type) {
        Bundle arg = new Bundle();
        Fragment detailFragment = null;
        switch (type){
            case "articleFragment":
                arg.putInt("article", position);
                detailFragment = new ArticleDetailFrg();
                break;
            case "problemFragment":
                arg.putInt("problem", position);
                detailFragment = new ProblemDetailFrg();
                break;
            case "contestFragment":
                arg.putInt("contest", position);
                detailFragment = new ContestDetailFrg();
                break;
        }
        detailFragment.setArguments(arg);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void UserInfoChange(UserInfoReceived.UserBean userBean,Bitmap bitmap) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main);
        View headerView = navigationView.getHeaderView(0);
        TextView user_name = headerView.findViewById(R.id.user_name);
        TextView user_motto = headerView.findViewById(R.id.user_motto);
        ImageView avatar = headerView.findViewById(R.id.avatar);
        user_name.setText(userBean.getName());
        user_motto.setText(userBean.getMotto());
        avatar.setImageBitmap(bitmap);
        String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(userBean.getEmail()), 120);
        String uri = this.getFilesDir() + "/images/" + DigestUtil.md5(url) + ".jpg";
        ImageUtil.saveImage(MainActivity.this,bitmap,uri);
    }

}
