package cn.edu.uestc.acm.cdoj;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.edu.uestc.acm.cdoj.layout.detail.ArticleFragment;
import cn.edu.uestc.acm.cdoj.layout.detail.ContestFragment;
import cn.edu.uestc.acm.cdoj.layout.detail.ProblemFragment;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.statusBar.StatusBarUtil;

/**
 * Created by great on 2016/8/21.
 */
public class ItemContentActivity extends AppCompatActivity {

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
        if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white);
            StatusBarUtil.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_gray);
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
                ((ContestFragment) detail).setContestID(id)
                        .refresh();
                break;
    }
        getFragmentManager().beginTransaction()
                .replace(R.id.detail_activity, detail)
                .commit();
    }
}
