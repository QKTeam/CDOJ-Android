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
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestUI;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends ListFragmentWithGestureLoad {
    SimpleAdapter adapter;
    ArrayList<Map<String,String>> listItems = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    PullUpLoadListView listView;
    ContestUI contestDetails;
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
            swipeRefreshLayout = (SwipeRefreshLayout)(getView().findViewById(R.id.listSwipeRefresh));
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    Global.netContent.getContent(ViewHandler.CONTEST_LIST, 1);
                }
            });
            listView = getListView();
            listView.setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
                @Override
                public void onPullUpLoading() {
                    Global.netContent.getContent(ViewHandler.CONTEST_LIST, listItems.size() / 20 + 1);
                }
            });
            Global.netContent.getContent(ViewHandler.CONTEST_LIST, 1);
            if (isTwoPane) {
                contestDetails = (ContestUI)((GetInformation) (Global.currentMainActivity))
                        .getDetailsContainer()
                        .getDetail(ViewHandler.CONTEST_DETAIL);
            }
        }
    }

    @Override
    public void addListItem(Map<String ,String> listItem) {
        listItems.add(listItem);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter == null) {
            adapter = new SimpleAdapter(
                    Global.currentMainActivity, listItems, R.layout.contest_list_item,
                    new String[]{"title", "releaseTime", "timeLimit", "id", "status", "permissions"},
                    new int[]{R.id.contest_title, R.id.contest_releaseTime, R.id.contest_timeLimit,
                            R.id.contest_id, R.id.contest_status, R.id.contest_permissions});
            setListAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (listView.isPullUpLoading()) {
            listView.pullUpLoadingComplete();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!isTwoPane) {
            Context context = l.getContext();
            Intent intent = new Intent(context, ItemContentActivity.class);
            intent.putExtra("type", ViewHandler.CONTEST_DETAIL);
            intent.putExtra("id", Integer.parseInt(listItems.get(position).get("id")));
            context.startActivity(intent);
            return;
        }
        Global.netContent.getContent(ViewHandler.CONTEST_DETAIL, Integer.parseInt(listItems.get(position).get("id")));
    }
}
