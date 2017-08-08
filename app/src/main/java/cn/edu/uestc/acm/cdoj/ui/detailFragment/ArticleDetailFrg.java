package cn.edu.uestc.acm.cdoj.ui.detailFragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.article.Article;
import cn.edu.uestc.acm.cdoj.ui.data.ArticleListData;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ArticleDetailFrg extends Fragment implements ReceivedCallback<Article>{
    private static final String TAG = "ArticleDetailFrg";
    private Article articleReceived = new Article();
    private WebView webView;

    android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Resources resources = getContext().getResources();
            InputStream input;
            try {
                input = resources.getAssets().open("articleRender.html");
                byte[] in = new byte[input.available()];
                input.read(in);
                String html = new String(in);
                html = html.replace("{{{replace_data_here}}}", JSON.toJSONString(articleReceived));
                webView.loadDataWithBaseURL(null,html , "text/html", "UTF-8", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_detail_fragmnet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int transData = getArguments().getInt("article");
        int id = ArticleListData.getData().get(transData).getArticleId();
        Connection.instance.getArticleContent(id, this);

        webView = view.findViewById(R.id.article_detail_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
    }


    @Override
    public void onDataReceived(Article article) {
        articleReceived = article;
        handler.obtainMessage(1, articleReceived).sendToTarget();
    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
