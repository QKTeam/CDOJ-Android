package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;
import cn.edu.uestc.acm.cdoj.net.data.Status;

/**
 * Created by great on 2016/8/25.
 */
public class ContestStatus extends Fragment implements ViewHandler{
    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID = -1;
    private int[] problemIDs;
    private ListViewWithGestureLoad mListView;
    private PageInfo mPageInfo;
    private boolean refreshed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListView = new ListViewWithGestureLoad(context);
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mListView.setOnPullUpLoadListener(new ListViewWithGestureLoad.OnPullUpLoadListener() {
            @Override
            public void onPullUpLoading() {
                if (mPageInfo != null && mPageInfo.currentPage != mPageInfo.totalPages) {
                    NetData.getStatusList(-1, null, contestID, mPageInfo.currentPage + 1, ContestStatus.this);
                } else {
                    mListView.setPullUpLoadFinish();
                }
            }
        });
        if (refreshed) refresh();
    }

    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            createAdapter();
        }
        mListAdapter.notifyDataSetChanged();
        if (mListView.isPullUpLoading()) {
            mListView.setPullUpLoading(false);
        }
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private void createAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_status_item_list,
                new String[]{"result", "language", "user", "cost", "probOrder", "submitDate"},
                new int[]{R.id.contestStatus_result, R.id.contestStatus_language,
                        R.id.contestStatus_user, R.id.contestStatus_cost,
                        R.id.contestStatus_probOrder, R.id.contestStatus_submitDate});
        mListView.setAdapter(mListAdapter);
    }


    public void setProblemIDs(int[] problemIDs) {
        this.problemIDs = problemIDs;
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            mPageInfo = ((InfoList) data).pageInfo;
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
            mListView.setPullUpLoadFailure();
        }
        notifyDataSetChanged();
    }

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public void refresh(int contestID)  {
        refreshed = true;
        this.contestID = contestID;
        mListView.resetPullUpLoad();
        listItems.clear();
        NetData.getStatusList(-1, null, contestID, 1, this);
    }
}
