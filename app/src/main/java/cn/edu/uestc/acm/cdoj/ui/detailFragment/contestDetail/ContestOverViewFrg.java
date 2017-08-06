package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 14779 on 2017-7-25.
 */

public class ContestOverViewFrg extends android.support.v4.app.Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contest_detail_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String contestReceived = getArguments().getString("contest_detail");

        webView = view.findViewById(R.id.contest_detail_overview_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        Resources resources = getContext().getResources();
        InputStream input;
        try {
            input = resources.getAssets().open("contestOverviewRender.html");
            byte[] in = new byte[input.available()];
            input.read(in);
            String html = new String(in);
            html = html.replace("{{{replace_data_here}}}", contestReceived);
            webView.loadDataWithBaseURL(null,html , "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
