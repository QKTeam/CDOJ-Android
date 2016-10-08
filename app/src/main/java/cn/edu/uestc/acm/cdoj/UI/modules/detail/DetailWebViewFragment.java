package cn.edu.uestc.acm.cdoj.ui.modules.detail;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

/**
 * Created by great on 2016/8/16.
 */
public class DetailWebViewFragment extends WebViewFragment {
    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";
    private String HTMLData;
    private String jsData;
    private String webData;
    private WebView webView;
    private int HTMLType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            switch (HTMLType) {
                case ViewHandler.ARTICLE_DETAIL:
                    HTMLData = Global.HTMLDATA_ARTICLE;
                    break;
                case ViewHandler.PROBLEM_DETAIL:
                    HTMLData = Global.HTMLDATA_PROBLEM;
                    break;
                case ViewHandler.CONTEST_DETAIL:
                    HTMLData = Global.HTMLDATA_CONTEST;
            }
            webView = getWebView();
            webView.getSettings().setJavaScriptEnabled(true);
            if (jsData != null) webData = HTMLData.replace("{{{replace_data_here}}}", jsData);
            if (webData != null) webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
        }

    }

    public DetailWebViewFragment switchHTMLData(int type) {
        this.HTMLType = type;
        return this;
    }

    public int getHTMLType() {
        return HTMLType;
    }

    public void addJSData(String jsData) {
        if (HTMLData != null) {
            webData = HTMLData.replace("{{{replace_data_here}}}", jsData);
            webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
            return;
        }
        this.jsData = jsData;
    }

    public void setFragmentRetainInstance(boolean retain){
        setRetainInstance(retain);
    }
}
