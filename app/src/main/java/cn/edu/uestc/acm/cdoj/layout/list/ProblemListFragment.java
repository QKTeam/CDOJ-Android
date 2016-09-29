package cn.edu.uestc.acm.cdoj.layout.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.ItemContentActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.GetInformation;
import cn.edu.uestc.acm.cdoj.layout.detail.ProblemFragment;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;
import cn.edu.uestc.acm.cdoj.net.data.ProblemInfo;

/**
 * Created by great on 2016/8/17.
 */
public class ProblemListFragment extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    boolean isTwoPane;
    private String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
        refresh();
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage < getPageInfo().totalPages) {
                        NetData.getProblemList(getPageInfo().currentPage + 1, key, ProblemListFragment.this);
                    } else {
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
                Global.currentMainActivity, listItems, R.layout.problem_item_list,
                new String[]{"title", "source", "idString", "number"},
                new int[]{R.id.problem_title, R.id.problem_source, R.id.problem_id, R.id.problem_number});
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!isTwoPane) {
            showDetailOnActivity(position);
            return;
        }
        ((ProblemFragment) Global.detailsContainer.getDetail(ViewHandler.PROBLEM_DETAIL))
                .refresh((int) listItems.get(position).get("id"));
    }

    private void showDetailOnActivity(int position) {
        Context context = rootView.getContext();
        Intent intent = new Intent(context, ItemContentActivity.class);
//        if (intent.resolveActivity(Global.currentMainActivity.getPackageManager()) != null) return;
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("type", ViewHandler.PROBLEM_DETAIL);
        intent.putExtra("id", (int) listItems.get(position).get("id"));
        context.startActivity(intent);
    }

    public void clearList() {
        listItems.clear();
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<ProblemInfo> infoList_P = ((InfoList) data).getInfoList();
            for (ProblemInfo tem : infoList_P) {
                String number = "solved/tried:  " + tem.solved + "/" + tem.tried;
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("title", tem.title);
                listItem.put("source", tem.source);
                listItem.put("id", tem.problemId);
                listItem.put("number", number);
                listItem.put("idString", "ID: " + tem.problemId);
                addListItem(listItem);
            }
        } else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ProblemListFragment refresh() {
        continuePullUpLoad();
        listItems.clear();
        NetData.getProblemList(1, key, this);
        return this;
    }
}
