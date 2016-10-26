package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by QK on 2016/10/21.
 */

public class ContestOverview extends Fragment {
    private DetailWebView mWebView;
    private String jsData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mWebView = (DetailWebView) inflater.inflate(R.layout.article, container, false);
            mWebView.switchType(NetData.ARTICLE_DETAIL);
            if (jsData != null) mWebView.addJSData(jsData);
        }
        return mWebView;
    }

    public void addJSData(String jsData) {
        this.jsData = jsData;
        if (mWebView != null) mWebView.addJSData(this.jsData);
    }
}
