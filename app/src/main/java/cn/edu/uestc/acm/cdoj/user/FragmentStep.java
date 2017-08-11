package cn.edu.uestc.acm.cdoj.user;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.article.Article;

/**
 * Created by lagranmoon on 2017/8/10.
 * method:GET
 * url:http://acm.uestc.edu.cn/article/data/6
 */

public class FragmentStep extends FragmentWebView implements ReceivedCallback<Article> {
    private Article articleReceived;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Connection.instance.getArticleContent(6, this);
    }

    @Override
    public void onDataReceived(Article article) {
        articleReceived = article;
        Resources resources;
        InputStream inputStream;
        try {
            resources = getContext().getResources();
            inputStream = resources.getAssets().open("articleRender.html");
            byte[] in = new byte[inputStream.available()];
            inputStream.read(in);
            String html = new String(in);
            html = html.replace("{{{replace_data_here}}}", JSON.toJSONString(articleReceived));
            webView.loadDataWithBaseURL(null,html , "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
