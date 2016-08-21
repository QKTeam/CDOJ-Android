package cn.edu.uestc.acm.cdoj_android.layout;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.GetInformation;
import cn.edu.uestc.acm.cdoj_android.Information;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.NetData;

/**
 * Created by great on 2016/8/17.
 */
public class ArticleListFragment extends ListFragmentWithGestureLoad {
    SimpleAdapter adapter;
    ArrayList<Map<String,String>> listItems = new ArrayList<>();
    Information information;
    SwipeRefreshLayout swipeRefreshLayout;
    PullUpLoadListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        information = ((GetInformation)getActivity()).getInformation();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeRefreshLayout = (SwipeRefreshLayout)(getView().findViewById(R.id.listSwipeRefresh));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                NetData.getArticleList(1,((GetInformation)getActivity()).getInformation());
            }
        });
        listView = getListView();
        listView.setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
            @Override
            public void onPullUpLoading() {
                Log.d("下拉加载", "Article");
                NetData.getArticleList(listItems.size() / 20 + 1, information);
            }
        });
        NetData.getArticleList(1,information);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addListItem(Map<String ,String> listItem) {
        listItems.add(listItem);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter == null) {
            adapter = new SimpleAdapter(
                    getActivity(), listItems, R.layout.article_list_item,
                    new String[]{"title", "content", "releaseTime", "author"},
                    new int[]{R.id.article_title, R.id.article_content, R.id.article_releaseTime, R.id.article_author});
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
        super.onListItemClick(l, v, position, id);
        NetData.getArticleDetail(Integer.parseInt(listItems.get(position).get("id")),information);
    }
}
