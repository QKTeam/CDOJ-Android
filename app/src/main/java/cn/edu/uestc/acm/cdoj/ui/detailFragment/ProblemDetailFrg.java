package cn.edu.uestc.acm.cdoj.ui.detailFragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
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
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.problem.Problem;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemListData;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemDetailFrg extends Fragment implements ReceivedCallback<Problem>{
    private WebView webView;
    private Problem problemReceived;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Resources resources = getContext().getResources();
            InputStream input;
            try {
                input = resources.getAssets().open("problemRender.html");
                byte[] in = new byte[input.available()];
                input.read(in);
                String html = new String(in);
                html = html.replace("{{{replace_data_here}}}", JSON.toJSONString(problemReceived));
                webView.loadDataWithBaseURL(null,html , "text/html", "UTF-8", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.problem_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int transData = getArguments().getInt("problem");
        int id = ProblemListData.getData().get(transData).getProblemId();
        Connection.instance.getProblemContent(id, this);

        webView = view.findViewById(R.id.problem_detail_wedView);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onDataReceived(Problem problem) {
        problemReceived = problem;
        handler.obtainMessage(1, problemReceived).sendToTarget();
    }
}
