package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Locale;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.tools.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestListFragment;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemListFragment;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;

/**
 * Created by Great on 2016/10/12.
 */

public class SearchResult extends AppCompatActivity {
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setupSystemBar();
        if (savedInstanceState == null) {
            initViews();
        }
    }

    private void setupSystemBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white, R.color.statusBar_background_gray, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }

    private void initViews() {
        intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        String key = intent.getStringExtra("key");
        TextView textView = (TextView) findViewById(R.id.searchResult_title);
        textView.setText(String.format(Locale.CHINA, "\"%s\" search result:", key));
        ViewHandler result = null;
        switch (type) {
            case ViewHandler.PROBLEM_LIST:
                result = new ProblemListFragment();
                int problemId = intent.getIntExtra("problemId", 0);
                if (problemId != 0) key = "";
                NetDataPlus.getProblemList(this, 1, key, problemId, result);
                break;
            case ViewHandler.CONTEST_LIST:
                result = new ContestListFragment();
                NetDataPlus.getContestList(this, 1, key, result);
                break;
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.searchResult_container, (Fragment) result)
                .commit();
    }
}
