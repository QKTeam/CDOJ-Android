package cn.edu.uestc.acm.cdoj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;
import cn.edu.uestc.acm.cdoj.ui.ViewPagerAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.ArticleListData;
import cn.edu.uestc.acm.cdoj.ui.data.ContestListData;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemListData;
import cn.edu.uestc.acm.cdoj.ui.data.RecentContestListData;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ArticleDetailFrg;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ContestDetailFrg;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.ProblemDetailFrg;
import cn.edu.uestc.acm.cdoj.user.FragmentAbout;
import cn.edu.uestc.acm.cdoj.user.FragmentStep;
import cn.edu.uestc.acm.cdoj.user.UserConnection;
import cn.edu.uestc.acm.cdoj.user.UserInfo;
import cn.edu.uestc.acm.cdoj.user.FragmentFAQ;
import cn.edu.uestc.acm.cdoj.user.FragmentRecentContest;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.FileUtil;
import cn.edu.uestc.acm.cdoj.utils.ImageUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GeneralFragment.TransItemDataListener {

    private static final String TAG = "MainActivity";
    public static boolean isLogin = false;
    public static String current_user;


    private String userName;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private List<String> tab_main_item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CookieSyncManager.createInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setWindowStatus();
        initDrawer();
        initViewPager();
        initLoginStatus();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        userName = getIntent().getStringExtra("userName");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences = this.getSharedPreferences(DigestUtil.md5("User"), MODE_APPEND);
        if (!sharedPreferences.contains("current_user")) {
            resetUserInfo();
            isLogin = false;
        } else {
            if (new File(this.getFilesDir() + "/UserInfo/" + userName).exists()) {
                UserInfo userInfo = JSON.parseObject(
                        FileUtil.readFile(this, "UserInfo", userName),
                        UserInfo.class);
                current_user = userInfo.getUserName();
                isLogin = true;
                initUserInfo(userInfo);
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        if (current_user != null) {
//            SharedPreferenceUtil.save_single_sp(this, "User", "current_user", current_user);
//        }
//        super.onDestroy();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //绘制ToolBar上面的menu
//        getMenuInflater().inflate(R.menu.toolbar_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            //ToolBar工具栏点击事件
//        }
//        return true;
//    }

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_recent_test:
                GeneralFragment fragment_recent_contest = new FragmentRecentContest(MainActivity.this, "recent_contest_fragment");
                new RecentContestListData(MainActivity.this).setUpList(fragment_recent_contest);
                transaction.replace(R.id.container, fragment_recent_contest).addToBackStack(null).commit();
                drawer.closeDrawers();
                break;
            case R.id.nav_FAQ:
                Fragment fragment_faq = new FragmentFAQ();
                transaction.replace(R.id.container, fragment_faq).addToBackStack(null).commit();
                drawer.closeDrawers();
                break;
            case R.id.nav_Step_By_Step:
                Fragment fragment_step = new FragmentStep();
                transaction.replace(R.id.container, fragment_step).addToBackStack(null).commit();
                drawer.closeDrawers();
                break;
            case R.id.nav_about:
                transaction.replace(R.id.container, new FragmentAbout());
                transaction.addToBackStack(null);
                drawer.closeDrawers();
                transaction.commit();
                break;
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
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("isLogin", isLogin);
                startActivity(intent);
            }
        });

    }

    private void initViewPager() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_main);
        tab_main_item.add("公告");
        tab_main_item.add("题目");
        tab_main_item.add("比赛");

        List<Fragment> fragment_list = new ArrayList<>();
        fragment_list.add(initArticleFragment(MainActivity.this));
        fragment_list.add(initProblemFragment(MainActivity.this));
        fragment_list.add(initContestFragment(MainActivity.this));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragment_list, tab_main_item);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initLoginStatus() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(DigestUtil.md5("User"), MODE_APPEND);
        if (sharedPreferences.contains("current_user")) {
            current_user = sharedPreferences.getString("current_user", null);
            String userName = sharedPreferences.getString(DigestUtil.md5(current_user), null);
            String password = sharedPreferences.getString(DigestUtil.md5(current_user), null);
            String[] key = new String[]{"userName", "password"};
            Object[] value = new Object[]{userName, password};
            UserConnection.getInstance().login_background(JsonUtil.getJsonString(key, value));
            if (new File(this.getFilesDir() + "/UserInfo/" + current_user).exists()) {
                UserInfo userInfo = JSON.parseObject(
                        FileUtil.readFile(this, "UserInfo", current_user),
                        UserInfo.class);
                isLogin = true;
                initUserInfo(userInfo);
            }
        }
    }

    private void initUserInfo(UserInfo userInfo) {
        String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(userInfo.getEmail()), 120);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView avatar = headerView.findViewById(R.id.avatar);
        Glide.with(this).load(url).into(avatar);
//        String uri = this.getFilesDir() + "/Images/" + DigestUtil.md5(url) + ".jpg";
//        TextView user_name = headerView.findViewById(R.id.user_name);
//        TextView user_motto = headerView.findViewById(R.id.user_motto);
//        user_name.setText(userInfo.getName());
//        user_motto.setText(userInfo.getMotto());
//        avatar.setImageBitmap(ImageUtil.readImage(uri));
    }

    private void resetUserInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main);
        View headerView = navigationView.getHeaderView(0);
        ImageView avatar = headerView.findViewById(R.id.avatar);
//        TextView user_name = headerView.findViewById(R.id.user_name);
//        TextView user_motto = headerView.findViewById(R.id.user_motto);
//        user_motto.setText("");
//        user_name.setText("");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_avatar);
        avatar.setImageBitmap(bitmap);
    }

    private void setWindowStatus() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private GeneralFragment initArticleFragment(Context context) {
        GeneralFragment articleFragment = new GeneralFragment(context, "article_fragment");
        new ArticleListData(context).setUpList(articleFragment);
        return articleFragment;
    }

    private GeneralFragment initProblemFragment(Context context) {
        GeneralFragment problemFragment = new GeneralFragment(context, "problem_fragment");
        new ProblemListData(context).setUpList(problemFragment);
        return problemFragment;
    }

    private GeneralFragment initContestFragment(Context context) {
        GeneralFragment contestFragment = new GeneralFragment(context, "contest_fragment");
        new ContestListData(context).setUpList(contestFragment);
        return contestFragment;
    }

    @Override
    public void onTranItemData(int position, String type) {
        Bundle arg = new Bundle();
        Fragment detailFragment = null;
        switch (type) {
            case "articleFragment":
                arg.putInt("article", position);
                detailFragment = new ArticleDetailFrg();
                break;
            case "problemFragment":
                arg.putInt("problem", position);
                ProblemDetailFrg detailFragment1 = new ProblemDetailFrg(true);
                detailFragment = detailFragment1;

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
}
