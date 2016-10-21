package cn.edu.uestc.acm.cdoj.ui.problem;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.tools.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemFragment extends Fragment implements ViewHandler{
    private View rootView;
    private DetailWebView mWebView;
    private TextView titleView;
    private String title;
    private FloatingActionButton button_addCode;
    private FloatingActionButton button_checkResult;
    private Context context;

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
            mWebView.switchType(ViewHandler.PROBLEM_DETAIL);
            titleView = (TextView) rootView.findViewById(R.id.problem_title);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public void setupFloatingButton() {
        button_addCode = (FloatingActionButton) rootView.findViewById(R.id.problem_button_addCede);
        button_checkResult = (FloatingActionButton) rootView.findViewById(R.id.problem_button_checkResult);
    }

    public void addJSData(String jsData) {
        mWebView.addJSData(jsData);
    }

    public ProblemFragment setTitle(String title) {
        this.title = title;
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public ProblemFragment refresh(int id) {
        NetDataPlus.getProblemDetail(context, id, this);
        return this;
    }

    @Override
    public void show(int which, Result result, long time) {
        addJSData(((Problem) result.getContent()).getContentString());
    }
}
