package cn.edu.uestc.acm.cdoj.ui.data.contestData;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.contest.comment.ContestCommentListItem;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;
import cn.edu.uestc.acm.cdoj.ui.adapter.ContestCommentAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.AbsDataList;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentListData extends AbsDataList<ContestCommentListItem> {
    private int id;
    private List<Bitmap> listAvatar = new ArrayList<>();

    public ContestCommentListData(Context context, int id) {
        super(context);
        this.id = id;
        Connection.instance.getContestComment(1, id, this);
    }

    @Override
    protected void createAdapter() {
        super.adapter = new ContestCommentAdapter(context, super.data, listAvatar);
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

    @Override
    public void onDataReceived(ListReceived<ContestCommentListItem> contestCommentListItemListReceived) {
        super.onDataReceived(contestCommentListItemListReceived);
        for (int i = 0; i<contestCommentListItemListReceived.getList().size(); i++) {
            UserConnection.getInstance().saveAvatar(context, contestCommentListItemListReceived.getList().get(i).getOwnerEmail(),120);
            listAvatar.add(UserConnection.getInstance().getAvatar(context, contestCommentListItemListReceived.getList().get(i).getOwnerEmail(),120));
        }
    }
}
