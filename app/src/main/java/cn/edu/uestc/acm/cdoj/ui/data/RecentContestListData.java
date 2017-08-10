package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.homePage.RecentContestListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.RecentContestAdapter;

/**
 * Created by 14779 on 2017-8-10.
 */

public class RecentContestListData implements ReceivedCallback<List<RecentContestListItem>>{
    private static final String TAG = "RecentContestListData";
    private Context context;
    private GeneralList list;
    private RecyclerView.Adapter adapter;
    private List<RecentContestListItem> data = new ArrayList<>();

    public RecentContestListData(Context context) {
        this.context = context;
        Connection.instance.getRecentContest(this);
    }

    public void setUpList(GeneralList list){
        this.list = list;
        createAdapter();
    }

    protected void createAdapter() {
        adapter = new RecentContestAdapter(context, data);
    }



    @Override
    public void onDataReceived(List<RecentContestListItem> recentContestListItems) {
        data.addAll(recentContestListItems);
        list.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
