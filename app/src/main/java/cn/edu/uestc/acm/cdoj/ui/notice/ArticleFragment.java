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
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ArticleData;
import cn.edu.uestc.acm.cdoj.net.data.ArticleReceive;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment implements ConvertNetData{
    private View rootView;
    private DetailWebView mWebView;
    private TextView titleView;
    private String title;
    private String jsonString;
    private ArticleData articleData;
    private Context context;

    String TAG = "文章";

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
            if (jsonString != null) mWebView.setJsonString(jsonString);

            titleView = (TextView) rootView.findViewById(R.id.title_article);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public ArticleFragment setJsonString(String jsonString) {
        this.jsonString = jsonString;
        if (mWebView != null) mWebView.setJsonString(this.jsonString);
        return this;
    }

    public ArticleFragment setTitle(String title) {
        this.title =title;
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public ArticleFragment refresh(int id) {
        if (context == null) return this;
        NetDataPlus.getArticleDetail(context, id, this);
        return this;
    }

    public ArticleFragment refresh(Context context, int id) {
        NetDataPlus.getArticleDetail(context, id, this);
        return this;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ArticleReceive articleReceive = JSON.parseObject(jsonString, ArticleReceive.class);
        articleData = articleReceive.getArticle();
        articleData.jsonString = JSON.toJSONString(articleData);
        this.jsonString = articleData.jsonString;

        if (articleReceive.getResult().equals("success")) {
            result.setStatus(Result.SUCCESS);
        } else {
            result.setStatus(Result.FALSE);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getStatus()) {
            case Result.SUCCESS:
                if (mWebView != null) {
                    mWebView.setJsonString(jsonString);
                }
                break;
            case Result.FALSE:
        }
    }
}
