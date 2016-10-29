package cn.edu.uestc.acm.cdoj.ui.modules.detail;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by great on 2016/8/16.
 */
public class DetailWebView extends WebView {
    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    private String htmlString;
    private String jsonString;

    @IntDef({NetData.ARTICLE_DETAIL, NetData.PROBLEM_DETAIL, NetData.CONTEST_DETAIL})
    @Retention(RetentionPolicy.SOURCE)
    @interface detail{}

    public DetailWebView(Context context) {
        this(context, null);
    }

    public DetailWebView(Context context, @detail int htmlType) {
        this(context, null);
        switchType(htmlType);
    }

    public DetailWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSettings().setJavaScriptEnabled(true);
    }

    public DetailWebView switchType(@detail int htmlType) {
        switch (htmlType) {
            case NetData.ARTICLE_DETAIL:
                htmlString = Global.getHtmldataArticle();
                break;
            case NetData.PROBLEM_DETAIL:
                htmlString = Global.getHtmldataProblem();
                break;
            case NetData.CONTEST_DETAIL:
                htmlString = Global.getHtmldataContest();
        }
        if (jsonString != null) showContent();
        return this;
    }

    public DetailWebView setJsonString(String jsonString) {
        this.jsonString = jsonString;
        if (htmlString != null) showContent();
        return this;
    }

    private void showContent() {
        String webData = htmlString.replace("{{{replace_data_here}}}", jsonString);
        loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
    }
}
