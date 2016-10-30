package cn.edu.uestc.acm.cdoj.ui.notice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
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
public class ArticleView extends LinearLayout implements ConvertNetData{
    private DetailWebView mWebView;
    private TextView titleView;
    private ArticleData articleData;

    String TAG = "文章";

    public ArticleView(Context context) {
        this(context, null);
    }

    public ArticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        titleView = (TextView) mInflater.inflate(R.layout.title_text_view, this, false);
        addView(titleView);
        mWebView = new DetailWebView(context, NetData.ARTICLE_DETAIL);
        addView(mWebView);
    }

    public ArticleView setTitle(String title) {
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public void setArticleData(ArticleData articleData) {
        if (articleData == null) return;
        this.articleData = articleData;
        mWebView.setJsonString(articleData.jsonString);
        setTitle(articleData.getTitle());
    }

    public ArticleData getArticleData() {
        return articleData;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ArticleReceive articleReceive = JSON.parseObject(jsonString, ArticleReceive.class);
        if (!articleReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }
        articleData = articleReceive.getArticle();
        articleData.jsonString = JSON.toJSONString(articleData);
        result.setStatus(Result.SUCCESS);
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getStatus()) {
            case Result.SUCCESS:
                setTitle(articleData.getTitle());
                mWebView.setJsonString(articleData.jsonString);
                break;
            case Result.FALSE:
        }
    }

    public ArticleView refresh(int id) {
        NetDataPlus.getArticleDetail(getContext(), id, this);
        return this;
    }
}
