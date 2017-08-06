package cn.edu.uestc.acm.cdoj.ui.data.contestData;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.RefreshLoadListener;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListItem;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListOverview;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;
import cn.edu.uestc.acm.cdoj.ui.adapter.RankListAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.GeneralList;

/**
 * Created by 14779 on 2017-8-5.
 */

public class ContestRankListData implements ReceivedCallback<RankListOverview>, RefreshLoadListener{
    private int id;
    private List<Bitmap> avatarList = new ArrayList<>();
    private Context context;
    private GeneralList list;
    private List<RankListItem> data = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private boolean isRefreshing = false;
    private boolean isFirstLoad = true;

    public ContestRankListData(Context context, int id) {
        this.context = context;
        this.id = id;
        Connection.instance.getRank(id, this);
    }

    public void setUpList(GeneralList list){
        this.list = list;
        list.setRefreshLoadListener(this);
        createAdapter();
    }

    protected void createAdapter() {
        adapter = new RankListAdapter(context, data, avatarList);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        Connection.instance.getRank(id, this);
        isRefreshing = true;
    }

    @Override
    public void onDataReceived(RankListOverview rankListOverview) {
        if (isRefreshing == true){
            data.clear();
            isRefreshing = false;
        }
        data.addAll(rankListOverview.getRankList());
        if (isFirstLoad == true){
            list.setListAdapter(adapter);
            isFirstLoad = false;
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < data.size(); i++){
            UserConnection.getInstance().saveAvatar(context, data.get(i).getEmail(), 160);
            avatarList.add(UserConnection.getInstance().getAvatar(context, data.get(i).getEmail(), 160));
        }

    }
}
