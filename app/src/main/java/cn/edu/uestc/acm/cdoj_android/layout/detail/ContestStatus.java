package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.list.PullUpLoadListView;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Status;

/**
 * Created by great on 2016/8/25.
 */
public class ContestStatus extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID = -1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (contestID != -1) refresh();
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    continuePullUpLoad();
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage != getPageInfo().totalPages) {
                        NetData.getContestStatusList(contestID, getPageInfo().currentPage + 1, ContestStatus.this);
                    }else {
                        stopPullUpLoad();
                    }
                }
            });
        }
    }

    @Override
    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter == null) {
            createAdapter();
        }
        adapter.notifyDataSetChanged();
        super.notifyDataSetChanged();
    }

    private void createAdapter() {
        adapter = new SimpleAdapter(
                Global.currentMainActivity, listItems, R.layout.contest_status_item_list,
                new String[]{"result", "language", "user", "cost", "probOrder", "submitDate"},
                new int[]{R.id.contestStatus_result, R.id.contestStatus_language,
                        R.id.contestStatus_user, R.id.contestStatus_cost,
                        R.id.contestStatus_probOrder, R.id.contestStatus_submitDate});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<Status> infoList_status = ((InfoList) data).getInfoList();
            for (Status tem : infoList_status) {
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("probOrder", tem.problemId);
                listItem.put("result", tem.returnType);
                listItem.put("submitDate", tem.timeString);
                listItem.put("language", tem.language+"  "+tem.length+"B");
                listItem.put("cost", tem.timeCost+"ms "+tem.memoryCost+"KB");
                listItem.put("user", tem.nickName);
                addListItem(listItem);
            }
        } else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }

    public void refresh()  {
        if (contestID == -1) throw new IllegalStateException("Status' contestID is null");
        listItems.clear();
        NetData.getContestStatusList(contestID, 1, this);
    }
}
