package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.io.IOException;
import java.io.InputStream;
import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 14779 on 2017-7-27.
 */

public class ContestProblemFrg extends Fragment {
    private WebView webView;
    private String problemReceived;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_problem_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        problemReceived = getArguments().getString("contest_problem_list_fragment");
        webView = view.findViewById(R.id.problem_detail_wedView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        Resources resources = getContext().getResources();
        InputStream input;
        try {
            input = resources.getAssets().open("problemRender.html");
            byte[] in = new byte[input.available()];
            input.read(in);
            String html = new String(in);
            html = html.replace("{{{replace_data_here}}}", problemReceived);
            webView.loadDataWithBaseURL(null,html , "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
