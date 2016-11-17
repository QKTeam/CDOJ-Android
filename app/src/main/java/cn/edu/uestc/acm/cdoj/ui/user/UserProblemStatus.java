package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserProblemStatus extends LinearLayout {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Integer> mData;
    private UserProblemsStatus status;

    public UserProblemStatus(Context context) {
        super(context);
        initView();
    }

    public UserProblemStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        inflate(getContext(), R.layout.user_problem_status,this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_status);
        status = new UserProblemsStatus();
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_status);
        setUp();
    }
    private void setUp(){
        initData();

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_status);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));
        recyclerView.setAdapter(new UserProblemStatusAdapter(getContext(), (ArrayList<Integer>) mData,status));
    }
    private void initData() {
        mData = new ArrayList<Integer>();
        for (int i = 1;i < 1494;i++){
            mData.add(i);
        }
    }
}
