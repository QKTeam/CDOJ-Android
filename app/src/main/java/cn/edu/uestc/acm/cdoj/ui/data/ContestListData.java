package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.contest.ContestListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.ContestAdapter;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestListData extends AbsDataList<ContestListItem>{
    private static final String TAG = "ContestListData";
    public static List<ContestListItem> data = new ArrayList<>();

    public ContestListData(Context context){
        super(context);
        Connection.instance.searchContest(1, this);
        ContestListData.setData(super.data);
    }

    @Override
    protected void createAdapter() {
        ContestAdapter contestAdapter = new ContestAdapter(context, super.data);
        contestAdapter.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                transItemDataListener.onTranItemData(position, "contestFragment");
            }
        });
        adapter = contestAdapter;
    }

    public static List<ContestListItem> getData(){
        return data;
    }

    public static void setData(List<ContestListItem> data){
        ContestListData.data = data;
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.currentPage < mPageInfo.getTotalPages()){
            Connection.instance.searchContest(mPageInfo.currentPage+1, "time", this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.searchContest(1, this);
        isfreshing = true;
    }
}
