package cn.edu.uestc.acm.cdoj.layout.detail.contest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj.layout.list.PullUpLoadListView;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;
import cn.edu.uestc.acm.cdoj.net.data.Status;

/**
 * Created by great on 2016/8/25.
 */
public class ContestStatus extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID = -1;
    private int[] problemIDs;
    private boolean firstLoad = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (firstLoad && contestID != -1) refresh();
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
                        NetData.getStatusList(-1, null, contestID,
                                getPageInfo().currentPage + 1, ContestStatus.this);
                    }else {
                        stopPullUpLoad();
                    }
                }
            });
            firstLoad = false;
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

    public void setProblemIDs(int[] problemIDs) {
        this.problemIDs = problemIDs;
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<Status> infoList_status = ((InfoList) data).getInfoList();
            for (Status tem : infoList_status) {
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("result", tem.returnType);
                listItem.put("submitDate", tem.timeString);
                listItem.put("language", tem.language+"/"+tem.length+"B");
                listItem.put("cost", "cost:"+tem.timeCost+"ms/"+tem.memoryCost+"KB");
                listItem.put("user", tem.nickName);
                if (problemIDs != null) {
                    int i = 0;
                    char charTem = 'A';
                    while (i != problemIDs.length && tem.problemId != problemIDs[i]) ++i;
                    if (i == problemIDs.length) {
                        listItem.put("probOrder", "?");
                    } else {
                        listItem.put("probOrder", String.valueOf((char)(charTem + i)));
                    }
                }
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
        NetData.getStatusList(-1, null, contestID, 1, this);
    }
}
