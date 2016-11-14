package cn.edu.uestc.acm.cdoj.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;
import cn.edu.uestc.acm.cdoj.ui.user.User;
import cn.edu.uestc.acm.cdoj.ui.user.UserInfo;

/**
 * Created by 13662 on 2016/11/1.
 */

public class AboutMeActivity extends AppCompatActivity {
    private LinearLayout container;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_container);
        setupSystemBar();
        container = (LinearLayout) findViewById(R.id.container_user_info);
        container.addView(new UserInfo(this,user));
    }

    private void setupSystemBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.ui_main_drawer_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.StatusBarLightMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
                drawerLayout.setStatusBarBackground(R.color.statusBar_background_white);
            }else {
                drawerLayout.setStatusBarBackground(R.color.statusBar_background_gray);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            drawerLayout.setFitsSystemWindows(false);
            FrameLayout linearLayout = (FrameLayout) findViewById(R.id.ui_main_content);
            linearLayout.setFitsSystemWindows(true);
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
            }
        }
    }
}
