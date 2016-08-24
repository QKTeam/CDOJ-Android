package cn.edu.uestc.acm.cdoj_android;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.edu.uestc.acm.cdoj_android.layout.details.DetailsWebViewFragment;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.StatusBarUtil;

/**
 * Created by great on 2016/8/21.
 */
public class ItemDetailActivity extends AppCompatActivity {

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initStatusBar();
        if (savedInstanceState == null) {
            initViews();
        }
    }

    private void initStatusBar() {
        if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe() ||(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)){
            StatusBarUtil.setStatusBarColor(this,R.color.statusBar_background_white);
            StatusBarUtil.StatusBarLightMode(this);
        }
        else {
            StatusBarUtil.setStatusBarColor(this,R.color.statusBar_background_gray);
        }
    }

    private void initViews() {
        intent = getIntent();
        DetailsWebViewFragment webViewFragment = new DetailsWebViewFragment();
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case ViewHandler.ARTICLE_DETAIL:
                webViewFragment.switchHTMLData(type);
                break;
            case ViewHandler.PROBLEM_DETAIL:
                webViewFragment.switchHTMLData(type);
                break;
            case ViewHandler.CONTEST_DETAIL:
                webViewFragment.switchHTMLData(type);
                break;
        }
        Global.netContent.getContent(webViewFragment, intent.getIntExtra("id", 1));
        getFragmentManager().beginTransaction()
                .replace(R.id.details_container, webViewFragment)
                .commit();
    }
}
