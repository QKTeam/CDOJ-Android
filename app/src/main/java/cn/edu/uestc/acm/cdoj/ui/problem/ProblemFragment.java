package cn.edu.uestc.acm.cdoj.ui.problem;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.getbase.floatingactionbutton.FloatingActionButton;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.net.data.ProblemReceive;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemFragment extends Fragment implements ConvertNetData{
    private View rootView;
    private DetailWebView mWebView;
    private TextView titleView;
    private String title;
    private FloatingActionButton button_addCode;
    private FloatingActionButton button_checkResult;
    private Context context;
    private ProblemData problemData;

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
            rootView = inflater.inflate(R.layout.problem, container, false);
            mWebView = (DetailWebView) rootView.findViewById(R.id.problem_detailWebView);
            mWebView.switchType(NetData.PROBLEM_DETAIL);
            titleView = (TextView) rootView.findViewById(R.id.problem_list_item_title);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public void setupFloatingButton() {
        button_addCode = (FloatingActionButton) rootView.findViewById(R.id.problem_button_addCede);
        button_checkResult = (FloatingActionButton) rootView.findViewById(R.id.problem_button_checkResult);
    }

    public void addJSData(String jsData) {
        mWebView.setJsonString(jsData);
    }

    public ProblemFragment setTitle(String title) {
        this.title = title;
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public ProblemFragment refresh(int id) {
        if (context == null) return this;
        NetDataPlus.getProblemDetail(context, id, this);
        return this;
    }

    public ProblemFragment refresh(Context context, int id) {
        NetDataPlus.getProblemDetail(context, id, this);
        return this;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ProblemReceive problemReceive = JSON.parseObject(jsonString, ProblemReceive.class);
        ProblemData problemData = problemReceive.getProblem();
        problemData.jsonString = JSON.toJSONString(problemData);
        result.setContent(problemData);

        if (problemReceive.getResult().equals("success")) {
            result.setStatus(Result.SUCCESS);
        } else {
            result.setStatus(Result.FALSE);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        if (result.getStatus() == Result.SUCCESS) {
            this.problemData = (ProblemData) result.getContent();
            addJSData(problemData.jsonString);
        }
    }

    public ProblemData getProblem() {
        return problemData;
    }

    public void setProblem(ProblemData problemData) {
        this.problemData = problemData;
        addJSData(problemData.jsonString);
    }
}
