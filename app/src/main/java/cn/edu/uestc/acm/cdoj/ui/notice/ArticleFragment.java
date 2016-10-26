package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.Article;
import cn.edu.uestc.acm.cdoj.net.data.ArticleReceived;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment implements ConvertNetData{
    private View rootView;
    private DetailWebView mWebView;
    private TextView titleView;
    private String title;
    private String jsData;
    private Article article;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.article, container, false);
            mWebView = (DetailWebView) rootView.findViewById(R.id.article_detailWebView);
            mWebView.switchType(NetData.ARTICLE_DETAIL);
            if (jsData != null) mWebView.addJSData(jsData);
            titleView = (TextView) rootView.findViewById(R.id.title_article);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public void addJSData(String jsData) {
        this.jsData = jsData;
        if (mWebView != null) mWebView.addJSData(this.jsData);
    }

    public ArticleFragment setTitle(String title) {
        this.title =title;
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public ArticleFragment refresh(int id) {
        NetDataPlus.getArticleDetail(context, id, this);
        return this;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ArticleReceived articleReceived = JSON.parseObject(jsonString, ArticleReceived.class);
        Article article = articleReceived.getArticle();
        article.addJsonString(JSON.toJSONString(article));
        result.setContent(article);

        if (articleReceived.getResult().equals("success")) {
            result.setStatus(NetHandler.Status.SUCCESS);
        } else {
            result.setStatus(NetHandler.Status.FALSE);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        if (result.getStatus() == NetHandler.Status.SUCCESS) {
            this.article = (Article) result.getContent();
            addJSData(article.obtainJsonString());
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
        addJSData(article.obtainJsonString());
    }
}
