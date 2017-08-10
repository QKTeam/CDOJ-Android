package cn.edu.uestc.acm.cdoj.ui.detailFragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.problem.Problem;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemListData;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemStatusListData;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail.ContestStatusFrg;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemDetailFrg extends Fragment implements ReceivedCallback<Problem>{
    private WebView webView;
    private Problem problemReceived;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton submitBt;
    private FloatingActionButton historyBt;
    private boolean isFbVisible;
    private String type = "problem";

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

    public ProblemDetailFrg(boolean isFBVisible){
        this.isFbVisible = isFBVisible;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.problem_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFloatingActionButton(view);

        int transData = getArguments().getInt("problem");
        int id = ProblemListData.getData().get(transData).getProblemId();
        Connection.instance.getProblemContent(id, this);

        webView = view.findViewById(R.id.problem_detail_wedView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
    }

    private void initFloatingActionButton(View view) {
        floatingActionMenu = view.findViewById(R.id.problem_detail_fragment_floating_button_menu);
        submitBt = view.findViewById(R.id.problem_submit_button);
        historyBt = view.findViewById(R.id.problem_submit_history_button);
        if (isFbVisible){
            floatingActionMenu.setVisibility(0);
        } else {
            floatingActionMenu.setVisibility(4);
        }
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                CodeFragment fragment = new CodeFragment(problemReceived.getProblemId(),type);
                transaction.add(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        historyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ContestStatusFrg fragment = new ContestStatusFrg(getContext(), "problem_status_fragment");
                new ProblemStatusListData(getContext(),problemReceived.getProblemId()).setUpList(fragment);
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onDataReceived(Problem problem) {
        problemReceived = problem;
        handler.obtainMessage(1, problemReceived).sendToTarget();
    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
