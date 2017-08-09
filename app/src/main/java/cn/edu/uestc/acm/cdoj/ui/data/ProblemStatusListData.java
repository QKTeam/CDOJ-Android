package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.contest.Contest;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemStatusListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.StatusListAdapter;

/**
 * Created by 14779 on 2017-8-9.
 */

public class ProblemStatusListData extends AbsDataList<ProblemStatusListItem> {
    private static final String TAG = "ProblemStatusListData";
    private int id;

    public ProblemStatusListData(Context context, int problemId) {
        super(context);
        id = problemId;
        Connection.instance.getProblemStatus(id, 1, this);
    }

    @Override
    protected void createAdapter() {
        adapter = new StatusListAdapter(context, data);
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.getCurrentPage() < mPageInfo.getTotalPages()){
            Connection.instance.getProblemStatus(id, mPageInfo.getCurrentPage()+1, this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.getProblemStatus(1, id, this);
        isRefreshing = true;
    }
}
