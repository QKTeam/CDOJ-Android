package cn.edu.uestc.acm.cdoj_android.layout.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj_android.ShowTestText;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/16.
 */
public class DetailsWebViewFragment extends WebViewFragment {
    final String acmWebUrl = "http://acm.uestc.edu.cn/";
    final String mimeType = "text/html";
    final String encoding = "utf-8";
    String HTMLData;
    String webData;
    WebView webView;
    int HTMLType = 0;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
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

    public void switchHTMLData(int type) {
        this.HTMLType = type;
    }

    public int getHTMLType() {
        return HTMLType;
    }

    public void addJSData(String JSData) {
        webData = HTMLData.replace("{{{replace_data_here}}}", JSData);
        if (webView != null) {
            webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
        }
    }
}
