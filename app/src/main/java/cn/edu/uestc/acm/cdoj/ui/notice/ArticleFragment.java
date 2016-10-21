package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.tools.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment implements ViewHandler{
    private View rootView;
    private DetailWebView mWebView;
    private TextView titleView;
    private String title;
    private String jsData;
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
            mWebView.switchType(ViewHandler.ARTICLE_DETAIL);
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

    @Override
    public void show(int which, Result result, long time) {
        addJSData(((Article) result.getContent()).getContentString());
    }
}
