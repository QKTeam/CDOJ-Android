package cn.edu.uestc.acm.cdoj.layout.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment implements ViewHandler{
    private View rootView;
    private DetailWebViewFragment webViewFragment;
    private Toolbar toolbar;
    private String title;
    private String jsData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.article, container, false);
            webViewFragment = new DetailWebViewFragment();
            webViewFragment.switchHTMLData(ViewHandler.ARTICLE_DETAIL);
            if (jsData != null) webViewFragment.addJSData(jsData);
            getFragmentManager().beginTransaction()
                    .add(R.id.webViewFragment_article,webViewFragment)
                    .commit();
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_article);
            toolbar.setTitleTextColor(ContextCompat.getColor(container.getContext(), R.color.main_blue));
            if (title != null) toolbar.setTitle(title);
        }
        return rootView;
    }

    public void addJSData(String jsData) {
        this.jsData = jsData;
        if (webViewFragment != null) webViewFragment.addJSData(this.jsData);
    }

    public ArticleFragment setTitle(String title) {
        this.title =title;
        if (toolbar != null) toolbar.setTitle(title);
        return this;
    }

    public ArticleFragment refresh(int id) {
        NetData.getArticleDetail(id, this);
        return this;
    }

    @Override
    public void show(int which, Object data, long time) {
        addJSData(((Article) data).getContentString());
    }
}
