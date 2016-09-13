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
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    continuePullUpLoad();
                    Global.netContent.getContestPart(ViewHandler.STATUS_LIST, contestID, 1);
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage != getPageInfo().totalPages) {
                        Global.netContent.getContestPart(ViewHandler.STATUS_LIST, contestID, getPageInfo().currentPage + 1);
                    }else {
                        stopPullUpLoad();
                    }
                }
            });
            if (contestID != -1) {
                Global.netContent.getContestPart(ViewHandler.STATUS_LIST, contestID, 1);
            }
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
                new String[]{"prob", "result", "length", "submitTime", "language", "time", "memory"},
                new int[]{R.id.contestStatus_prob, R.id.contestStatus_result,
                        R.id.contestStatus_length, R.id.contestStatus_submitTime,
                        R.id.contestStatus_language, R.id.contestStatus_time, R.id.contestStatus_memory});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
        if (getListView() != null) {
            Global.netContent.getContestPart(ViewHandler.STATUS_LIST, contestID, 1);
        }
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<Status> infoList_status = ((InfoList) data).getInfoList();
            for (Status tem : infoList_status) {
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("prob", tem.problemId);
                listItem.put("result", tem.returnType);
                listItem.put("length", tem.length);
                listItem.put("submitTime", tem.timeString);
                listItem.put("language", tem.language);
                listItem.put("time", tem.timeCost);
                listItem.put("memory", tem.memoryCost);
                addListItem(listItem);
            }
        } else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }
}
