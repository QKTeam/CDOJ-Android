package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.MainList;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;

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
    private int progressContainerVisibility = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        if (progressContainerVisibility != -1) {
            mListView.setProgressContainerVisibility(progressContainerVisibility);
        }
        configOnListItemClick();
        if (refreshed) refresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        intent.putExtra("id", (int) listItems.get(position).get("id"));
        context.startActivity(intent);
    }

    private void createAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.article_item_list,
                new String[]{"title", "content", "date", "author"},
                new int[]{R.id.article_title, R.id.article_content,
                        R.id.article_date, R.id.article_author});
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void show(int which, Object data, long time) {
        Log.d("我在这里", "show: ");
        if (((InfoList) data).result) {
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<ArticleInfo> infoList_A = ((InfoList) data).getInfoList();
            Log.d("我在这里aaaaa", "show: "+infoList_A.size());
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
            mListView.setPullUpLoadFailure();
        }
        if (mPageInfo.currentPage == 1 && progressListener != null) {
            progressListener.end();
        }
        notifyDataSetChanged();
    }

    public NoticeListFragment refresh() {
        refreshed = true;
        if (mListView != null){
            if (progressListener != null) progressListener.start();
            mListView.resetPullUpLoad();
            listItems.clear();
            NetData.getArticleList(1, this);
        }
        return this;
    }

    @Override
    public void notifyDataSetChanged() {
        if (mListAdapter == null) createAdapter();
        mListAdapter.notifyDataSetChanged();
        if (mListView.isPullUpLoading()) {
            mListView.setPullUpLoading(false);
        }
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    @Override
    public void setRefreshProgressListener(OnRefreshProgressListener listener) {
        progressListener = listener;
    }

    @Override
    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    @Override
    public void setPageInfo(PageInfo pageInfo) {
        mPageInfo = pageInfo;
    }

    @Override
    public PageInfo getPageInfo() {
        return mPageInfo;
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
}
