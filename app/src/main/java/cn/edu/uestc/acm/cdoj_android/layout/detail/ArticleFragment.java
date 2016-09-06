package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/25.
 */
public class ArticleFragment extends Fragment {
    private View rootView;
    private DetailWebViewFragment webViewFragment;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.article, container, false);
            webViewFragment = new DetailWebViewFragment();
            webViewFragment.switchHTMLData(ViewHandler.ARTICLE_DETAIL);
            getFragmentManager().beginTransaction()
                    .add(R.id.webViewFragment_article,webViewFragment)
                    .commit();
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_article);
        }
        return rootView;
    }

    public void addJSData(String jsData) {
        webViewFragment.addJSData(jsData);
    }
}
