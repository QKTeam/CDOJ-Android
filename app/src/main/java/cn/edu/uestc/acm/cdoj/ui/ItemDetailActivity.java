package cn.edu.uestc.acm.cdoj.ui;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.notice.ArticleFragment;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestFragment;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemFragment;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;

/**
 * Created by great on 2016/8/21.
 */
public class ItemDetailActivity extends AppCompatActivity {

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initStatusBar();
        if (savedInstanceState == null) {
            initViews();
        }
    }

    private void initStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View statusBarBackGroundView = StatusBarUtil
                .setStatusBarColor(this, R.color.main_background, R.color.statusBar_background_gray);
        ((ViewGroup) findViewById(R.id.activity_detail_status)).addView(statusBarBackGroundView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }

    private void initViews() {
        intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        int id = intent.getIntExtra("id", 1);
        Fragment detail = null;
        switch (type) {
            case ViewHandler.ARTICLE_DETAIL:
                detail = new ArticleFragment();
                ((ArticleFragment) detail).setTitle(intent.getStringExtra("title"))
                        .refresh(id);
                break;
            case ViewHandler.PROBLEM_DETAIL:
                detail = new ProblemFragment();
                ((ProblemFragment) detail).setTitle(intent.getStringExtra("title"))
                        .refresh(id);
                break;
            case ViewHandler.CONTEST_DETAIL:
                detail = new ContestFragment();
                ((ContestFragment) detail).refresh(id);
                break;
    }
        getFragmentManager().beginTransaction()
                .replace(R.id.detail_activity, detail)
                .commit();
    }
}
