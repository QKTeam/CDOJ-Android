package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/16.
 */
public class DetailWebViewFragment extends android.webkit.WebViewFragment {
    final String acmWebUrl = "http://acm.uestc.edu.cn/";
    final String mimeType = "text/html";
    final String encoding = "utf-8";
    String HTMLData;
    String webData;
    WebView webView;
    int HTMLType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            webView = getWebView();
            webView.getSettings().setJavaScriptEnabled(true);
            try {
                InputStream input;
                byte[] in;
                switch (HTMLType) {
                    case ViewHandler.ARTICLE_DETAIL:
                        input = getResources().getAssets().open("articleRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        HTMLData = new String(in);
                        break;
                    case ViewHandler.PROBLEM_DETAIL:
                        input = getResources().getAssets().open("problemRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        HTMLData = new String(in);
                        break;
                    case ViewHandler.CONTEST_DETAIL:
                        input = getResources().getAssets().open("contestOverviewRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        HTMLData = new String(in);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (webData != null) {webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);}
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
        webData = HTMLData.replace("{{{replace_data_here}}}", jsData);
        if (webView != null) {
            webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
        }
    }
}
