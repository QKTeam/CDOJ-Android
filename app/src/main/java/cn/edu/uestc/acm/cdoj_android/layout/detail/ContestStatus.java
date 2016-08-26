package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;

/**
 * Created by great on 2016/8/25.
 */
public class ContestStatus extends ListFragmentWithGestureLoad {
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            swipeRefreshLayout = (SwipeRefreshLayout) (getView().findViewById(R.id.listSwipeRefresh));
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                }
                // TODO: 2016/8/26
            });
            getListView().setPullUpLoadEnable(false);
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
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void createAdapter() {
        adapter = new SimpleAdapter(
                Global.currentMainActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{"prob", "result", "length", "submitTime", "language", "time", "memory"},
                new int[]{R.id.contestStatus_prob, R.id.contestStatus_result,
                        R.id.contestStatus_length, R.id.contestStatus_submitTime,
                        R.id.contestStatus_language, R.id.contestStatus_time, R.id.contestStatus_memory});
        setListAdapter(adapter);
    }
}
