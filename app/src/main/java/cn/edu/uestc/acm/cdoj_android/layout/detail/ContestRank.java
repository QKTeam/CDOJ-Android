package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Rank;

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (contestID != -1) refresh();
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
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
        adapter = new ContestRankAdapter(
                Global.currentMainActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"rank", "nickName", "account"},
                new int[]{R.id.contestRank_rank, R.id.contestRank_nickName, R.id.contestRank_account});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
        if (getListView() != null) {
            Global.netContent.getContestPart(ViewHandler.CONTEST_RANK, contestID, 1);
        }
    }

    @Override
    public void show(int which, Object data, long time) {
        Rank rankInfo = (Rank) data;
        if (rankInfo.result) {
            ArrayList<Rank.Performance> infoList_rank = rankInfo.getPerformanceList();
            for (Rank.Performance tem : infoList_rank) {
                int solved = 0;
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("rank", "Rank  "+tem.rank);
                listItem.put("account", tem.name);
                listItem.put("nickName", tem.nickName);
                listItem.put("solvedNum", tem.solved);
                ArrayList<Rank.Performance.ProblemStatus> problemStatusList = tem.getProblemStatusList();
                listItem.put("probCount", problemStatusList.size());
                Collections.reverse(problemStatusList);
                for (Rank.Performance.ProblemStatus temP : problemStatusList) {
                    if (temP.solved) ++solved;
                    solved = solved << 1;
                }
                listItem.put("solvedDetail", solved);
                addListItem(listItem);
            }
        }
        notifyDataSetChanged();
    }

    public void refresh() {
        if (contestID == -1) throw new IllegalStateException("Rank's contestId is null");
        listItems.clear();
        NetData.getContestRank(contestID, this);
    }
}
