package cn.edu.uestc.acm.cdoj.ui.problem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebViewFragment;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemFragment extends Fragment implements ViewHandler{
    private View rootView;
    private DetailWebViewFragment webViewFragment;
    private TextView titleView;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.problem, container, false);
            webViewFragment = new DetailWebViewFragment();
            webViewFragment.switchHTMLData(ViewHandler.PROBLEM_DETAIL);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.webViewFragment_problem, webViewFragment)
                    .commit();
            titleView = (TextView) rootView.findViewById(R.id.title_problem);
            if (title != null) titleView.setText(title);
        }
        return rootView;
    }

    public void addJSData(String jsData) {
        webViewFragment.addJSData(jsData);
    }

    public ProblemFragment setTitle(String title) {
        this.title = title;
        if (titleView != null) titleView.setText(title);
        return this;
    }

    public ProblemFragment refresh(int id) {
        NetData.getProblemDetail(id, this);
        return this;
    }

    @Override
    public void show(int which, Object data, long time) {
        addJSData(((Problem) data).getContentString());
    }
}
