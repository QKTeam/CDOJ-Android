package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.widget.Toast;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.contest.ContestCommentListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.ContestCommentAdapter;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentListData extends AbsDataList<ContestCommentListItem> {
    private int id;

    public ContestCommentListData(Context context, int id) {
        super(context);
        this.id = id;
        Connection.instance.getContestComment(1, id, this);
    }

    @Override
    protected void createAdapter() {
        super.adapter = new ContestCommentAdapter(context, super.data);
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.currentPage < mPageInfo.getTotalPages()) {
            Connection.instance.getContestComment(mPageInfo.getCurrentPage() + 1, id, this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.getContestComment(1, id, this);
        isRefreshing = true;
    }
}
