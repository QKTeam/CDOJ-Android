package cn.edu.uestc.acm.cdoj.ui.data.contestData;

import android.content.Context;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.contest.status.ContestStatusListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.StatusListAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.AbsDataList;

/**
 * Created by 14779 on 2017-8-4.
 */

public class StatusListData extends AbsDataList<ContestStatusListItem> {
    private static final String TAG = "StatusListData";
    private int id;

    public StatusListData(Context context, int id) {
        super(context);
        this.id = id;
        Connection.instance.getContestStatus(1, id, this);
    }

    @Override
    protected void createAdapter() {
        super.adapter = new StatusListAdapter(context, super.data);
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.getCurrentPage() < mPageInfo.getTotalPages()){
            Connection.instance.getContestStatus(mPageInfo.getCurrentPage()+1, id, this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.getContestStatus(1, id, this);
        isRefreshing = true;
    }
}
