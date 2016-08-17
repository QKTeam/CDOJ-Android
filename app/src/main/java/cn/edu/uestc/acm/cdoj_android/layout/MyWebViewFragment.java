package cn.edu.uestc.acm.cdoj_android.layout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewFragment;

/**
 * Created by great on 2016/8/16.
 */
public class MyWebViewFragment extends WebViewFragment {
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWebView().loadUrl(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
