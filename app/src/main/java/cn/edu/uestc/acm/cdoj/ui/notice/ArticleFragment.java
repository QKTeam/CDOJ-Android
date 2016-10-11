package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebViewFragment;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment implements ViewHandler{
    private View rootView;
    private DetailWebViewFragment webViewFragment;
    private TextView titleView;
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
            titleView = (TextView) rootView.findViewById(R.id.title_article);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public void addJSData(String jsData) {
        this.jsData = jsData;
        if (webViewFragment != null) webViewFragment.addJSData(this.jsData);
    }

    public ArticleFragment setTitle(String title) {
        this.title =title;
        if (titleView != null) titleView.setText(title);
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
