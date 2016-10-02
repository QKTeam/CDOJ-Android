package cn.edu.uestc.acm.cdoj.layout.detail.contest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.Rank;
import cn.edu.uestc.acm.cdoj.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.statusBar.StatusBarUtil;

/**
 * Created by Grea on 2016/9/30.
 */

public class ItemContestRankActivity extends AppCompatActivity {
    Intent intent;
    ListView mListView;
    LinearLayout mListViewHeader;
    Rank.Performance rankItem;

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
        mListView = (ListView) findViewById(R.id.contestRankItem_listView);
        mListViewHeader = (LinearLayout) getLayoutInflater().inflate(R.layout.contest_rank_list_item_detail_header, mListView);
        mListView.addHeaderView(mListViewHeader);
    }
}
