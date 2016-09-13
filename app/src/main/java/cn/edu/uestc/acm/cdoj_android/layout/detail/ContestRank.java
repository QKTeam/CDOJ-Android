package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends ListFragmentWithGestureLoad {
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    continuePullUpLoad();
                    Global.netContent.getContestPart(ViewHandler.CONTEST_RANK, contestID, 1);
                }
            });
            if (contestID != -1) {
                Global.netContent.getContestPart(ViewHandler.CONTEST_RANK, contestID, 1);
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
                Global.currentMainActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"rank", "name", "solved"},
                new int[]{R.id.contestRank_rank, R.id.contestRank_name, R.id.contestRank_solved});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
        if (getListView() != null) {
            Global.netContent.getContestPart(ViewHandler.CONTEST_RANK, contestID, 1);
        }
    }
}
