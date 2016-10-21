package cn.edu.uestc.acm.cdoj.ui.modules.detail;

import android.content.Context;
import android.support.annotation.IntDef;
import android.webkit.WebView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

/**
 * Created by great on 2016/8/16.
 */
public class DetailWebView extends WebView {
    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";
    private String htmlData;
    private String webData;
    int htmlType = -1;

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL, ViewHandler.CONTEST_DETAIL})
    @Retention(RetentionPolicy.SOURCE)
    @interface detail{}

    public DetailWebView(Context context) {
        super(context);
    }

    public DetailWebView(Context context, @detail int htmlType) {
        super(context);
        this.htmlType = htmlType;
        init(htmlType);
    }

    public void init(@detail int htmlType) {
        switch (htmlType) {
            case ViewHandler.ARTICLE_DETAIL:
                htmlData = Global.HTMLDATA_ARTICLE;
                break;
            case ViewHandler.PROBLEM_DETAIL:
                htmlData = Global.HTMLDATA_PROBLEM;
                break;
            case ViewHandler.CONTEST_DETAIL:
                htmlData = Global.HTMLDATA_CONTEST;
        }
        getSettings().setJavaScriptEnabled(true);
    }

    public int getHtmlType() {
        return htmlType;
    }

    public DetailWebView switchType(@detail int htmlType) {
        this.htmlType = htmlType;
        init(htmlType);
        return this;
    }

    public DetailWebView addJSData(String jsData) {
        if (htmlData != null) {
            webData = htmlData.replace("{{{replace_data_here}}}", jsData);
            loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
        }
        return this;
    }
}
