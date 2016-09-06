package cn.edu.uestc.acm.cdoj_android.layout.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.ItemContentActivity;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.GetInformation;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/17.
 */
public class ProblemListFragment extends ListFragmentWithGestureLoad {
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    boolean isTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    continuePullUpLoad();
                    Global.netContent.getContent(ViewHandler.PROBLEM_LIST, 1);
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
                @Override
                public void onPullUpLoading() {
                    Global.netContent.getContent(ViewHandler.PROBLEM_LIST, getPageInfo().currentPage+1);
                }
            });
            Global.netContent.getContent(ViewHandler.PROBLEM_LIST, 1);
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
                new String[]{"title", "source", "id", "number"},
                new int[]{R.id.problem_title, R.id.problem_source, R.id.problem_id, R.id.problem_number});
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!isTwoPane) {
            Context context = l.getContext();
            Intent intent = new Intent(context, ItemContentActivity.class);
            intent.putExtra("type", ViewHandler.PROBLEM_DETAIL);
            intent.putExtra("id", Integer.parseInt((String) listItems.get(position).get("id")));
            context.startActivity(intent);
            return;
        }
        Global.netContent.getContent(ViewHandler.PROBLEM_DETAIL,
                Integer.parseInt((String) listItems.get(position).get("id")));
    }
}
