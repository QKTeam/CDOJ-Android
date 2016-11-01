package cn.edu.uestc.acm.cdoj.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Locale;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.ui.contest.StatusView;
import cn.edu.uestc.acm.cdoj.ui.notice.ArticleView;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestView;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemView;
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
        StatusBarUtil.setStatusBarColor(this, R.color.main_background, R.color.statusBar_background_gray, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }

    private void initViews() {
        intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        int id = intent.getIntExtra("id", 1);
        View detail = null;
        switch (type) {
            case NetData.ARTICLE_DETAIL:
                detail = new ArticleView(this).refresh(id);
                break;
            case NetData.PROBLEM_DETAIL:
                detail = new ProblemView(this).refresh(id);
                break;
            case NetData.CONTEST_DETAIL:
                detail = new ContestView(this).refresh(id);
                break;
            case NetData.STATUS_LIST:
                TextView title = (TextView) findViewById(R.id.activity_detail_title);
                String probTitle = intent.getStringExtra("title");
                title.setVisibility(View.VISIBLE);
                title.setText(String.format(Locale.CHINA, "\"%s\"提交结果", probTitle));
                detail = new StatusView(this);
                ((StatusView) detail).setProblemId(id);
                ((StatusView) detail).refresh();
        }
        ((ViewGroup) findViewById(R.id.activity_detail_content_container)).addView(detail);
    }
}
