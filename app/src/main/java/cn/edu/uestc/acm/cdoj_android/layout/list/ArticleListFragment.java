package cn.edu.uestc.acm.cdoj_android.layout.list;

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

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.ItemContentActivity;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.GetInformation;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleFragment;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;

/**
 * Created by great on 2016/8/17.
 */
public class ArticleListFragment extends ListFragmentWithGestureLoad implements ViewHandler {
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private PullUpLoadListView listView;
    boolean isTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
            refresh();
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage < getPageInfo().totalPages) {
                        NetData.getArticleList(getPageInfo().currentPage + 1, ArticleListFragment.this);
                    }else {
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!isTwoPane) {
            showDetailOnActivity(position);
            return;
        }
        ((ArticleFragment) Global.detailsContainer.getDetail(ViewHandler.ARTICLE_DETAIL))
                .refresh((int) listItems.get(position).get("id"));
    }

    private void showDetailOnActivity(int position) {
        Context context = rootView.getContext();
        Intent intent = new Intent(context, ItemContentActivity.class);
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("type", ViewHandler.ARTICLE_DETAIL);
        intent.putExtra("id", (int[]) listItems.get(position).get("id"));
        context.startActivity(intent);
    }

    private void createAdapter() {
        adapter = new SimpleAdapter(
                Global.currentMainActivity, listItems, R.layout.article_item_list,
                new String[]{"title", "content", "date", "author"},
                new int[]{R.id.article_title, R.id.article_content,
                        R.id.article_date, R.id.article_author});
        setListAdapter(adapter);
    }

    public ArticleListFragment refresh(int id) {
        NetData.getArticleList(id, this);
        return this;
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<ArticleInfo> infoList_A = ((InfoList) data).getInfoList();
            for (ArticleInfo tem : infoList_A) {
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("title", tem.title);
                listItem.put("content", tem.content);
                listItem.put("date", tem.timeString);
                listItem.put("author", tem.ownerName);
                listItem.put("id", tem.articleId);
                addListItem(listItem);
            }
        }else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }

    public ArticleListFragment refresh() {
        continuePullUpLoad();
        listItems.clear();
        NetData.getArticleList(1, this);
        return this;
    }
}
