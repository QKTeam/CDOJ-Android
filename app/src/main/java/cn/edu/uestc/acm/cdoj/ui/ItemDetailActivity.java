package cn.edu.uestc.acm.cdoj.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.data.ArticleData;
import cn.edu.uestc.acm.cdoj.ui.notice.ArticleFragment;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestView;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemFragment;
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
        Fragment detail = null;
        switch (type) {
            case NetData.ARTICLE_DETAIL:
                ArticleData articleData = (ArticleData) intent.getSerializableExtra("article");
                ArticleFragment articleFragment = new ArticleFragment();
                articleFragment.setTitle(articleData.getTitle());
                if (articleData.isHasMore()) {
                    articleFragment.refresh(this, articleData.getArticleId());
                } else {
                    articleFragment.setJsonString(articleData.jsonString);
                }
                detail = articleFragment;
                break;
            case NetData.PROBLEM_DETAIL:
                detail = new ProblemFragment();
                ((ProblemFragment) detail).setTitle(intent.getStringExtra("title"))
                        .refresh(this, id);
                break;
            case NetData.CONTEST_DETAIL:
                View detailView = new ContestView(this, id).refresh();
                ((ViewGroup) findViewById(R.id.detail_activity)).addView(detailView);
                return;
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.detail_activity, detail)
                .commit();
    }
}
