package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.MainList;
import cn.edu.uestc.acm.cdoj.ui.modules.list.PageInfo;

/**
 * Created by great on 2016/8/17.
 */
public class NoticeListFragment extends Fragment implements ViewHandler, MainList {
    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private MainList.OnRefreshProgressListener progressListener;
    private ListViewWithGestureLoad mListView;
    private PageInfo mPageInfo;
    private Context context;
    private boolean refreshed;
    private int progressContainerVisibility = View.VISIBLE;
    private boolean hasSetProgressListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                if (mPageInfo != null && mPageInfo.currentPage < mPageInfo.totalPages) {
                    NetData.getArticleList(mPageInfo.currentPage + 1, NoticeListFragment.this);
                } else {
                    mListView.setPullUpLoadFinish();
                }
            }
        });
        mListView.setProgressContainerVisibility(progressContainerVisibility);
        configOnListItemClick();
        if (refreshed) refresh();
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Global.isTwoPane) {
                    showDetailOnActivity(position);
                    return;
                }
                FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
                ArticleFragment article = new ArticleFragment().refresh((int) listItems.get(position).get("id"));
                article.setTitle((String) listItems.get(position).get("title"));
                mTransaction.replace(R.id.ui_main_detail, article);
                mTransaction.commit();
            }
        });
    }

    private void showDetailOnActivity(int position) {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("type", ViewHandler.ARTICLE_DETAIL);
        intent.putExtra("id", (int) listItems.get(position).get("articleId"));
        context.startActivity(intent);
    }

    @Override
    public void show(int which, Result result, long time) {
        if (refreshed) {
            if (hasSetProgressListener) progressListener.end();
            listItems.clear();
            notifyDataSetChanged();
            refreshed = false;
        }
        if (result.result) {
            Map<String, Object> listMap = (Map<String, Object>) result.getContent();
            mPageInfo = new PageInfo((Map<String, Object>) listMap.get("pageInfo"));
            ArrayList<Map<String, Object>> temArrayList = (ArrayList<Map<String, Object>>) listMap.get("list");
            for (Map<String, Object> temMap : temArrayList) {
                temMap.put("time", TimeFormat.getFormatDate((long) temMap.get("time")));
            }
            listItems.addAll(temArrayList);
            if (mPageInfo.totalItems == 0) {
                mListView.setDataIsNull();
                notifyDataSetChanged();
                return;
            }
            if (listItems.size() >= mPageInfo.totalItems) {
                mListView.setPullUpLoadFinish();
            }
        }else {
            mListView.setPullUpLoadFailure();
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListView.setAdapter(createAdapter());
        }else {
            mListAdapter.notifyDataSetChanged();
        }
        if (mListView.isPullUpLoading()) {
            mListView.setPullUpLoading(false);
        }
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private ListAdapter createAdapter() {
        mListAdapter =  new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.article_item_list,
                new String[]{"title", "content", "time", "ownerName"},
                new int[]{R.id.article_title, R.id.article_content,
                        R.id.article_date, R.id.article_author});
        return mListAdapter;
    }

    @Override
    public void setRefreshProgressListener(OnRefreshProgressListener listener) {
        hasSetProgressListener = true;
        progressListener = listener;
    }

    @Override
    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    @Override
    public ListViewWithGestureLoad getListView() {
        return mListView;
    }

    @Override
    public void setProgressContainerVisibility(int visibility) {
        progressContainerVisibility = visibility;
        if (mListView != null) {
            mListView.setProgressContainerVisibility(visibility);
        }
    }

    public NoticeListFragment refresh() {
        refreshed = true;
        if (mListView != null){
            if (hasSetProgressListener) progressListener.start();
            mListView.resetPullUpLoad();
            NetData.getArticleList(1, this);
        }
        return this;
    }

}
