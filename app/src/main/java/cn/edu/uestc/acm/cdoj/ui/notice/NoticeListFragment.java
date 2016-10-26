package cn.edu.uestc.acm.cdoj.ui.notice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.PageInfo;

/**
 * Created by great on 2016/8/17.
 */
public class NoticeListFragment extends Fragment implements ConvertNetData {
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private ListViewWithGestureLoad mListView;
    private SimpleAdapter mListAdapter;
    private PageInfo mPageInfo;
    private Context context;

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
                    NetDataPlus.getArticleList(context, mPageInfo.currentPage + 1, NoticeListFragment.this);
                } else {
                    mListView.noticeLoadFinish();
                }
            }
        });
        configOnListItemClick();
        if (mListAdapter != null) mListView.setAdapter(mListAdapter);
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
        intent.putExtra("type", NetData.ARTICLE_DETAIL);
        intent.putExtra("id", (int) listItems.get(position).get("articleId"));
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        Map<String, Object> listMap = JSON.parseObject(jsonString);
        mPageInfo = new PageInfo((Map) listMap.get("pageInfo"));
        convertNetData((ArrayList<Map<String, Object>>) listMap.get("list"));

        if (mPageInfo.totalItems == 0) {
            result.setStatus(NetHandler.Status.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(NetHandler.Status.FINISH);
        } else if (listMap.get("result").equals("success")) {
            result.setStatus(NetHandler.Status.SUCCESS);
        } else {
            result.setStatus(NetHandler.Status.FALSE);
        }
        return result;
    }

    private void convertNetData(ArrayList<Map<String, Object>> list) {
        for (Map<String, Object> temMap : list) {
            temMap.put("time", TimeFormat.getFormatDate((long) temMap.get("time")));
        }
        listItems.addAll(list);
    }

    @Override
    public void onNetDataConverted(Result result) {
        if (mListView != null) {
            switch (result.getType()) {
                case NetHandler.Status.SUCCESS:
                    break;
                case NetHandler.Status.DATAISNULL:
                    mListView.noticeDataIsNull();
                    break;
                case NetHandler.Status.FINISH:
                    mListView.noticeLoadFinish();
                    break;
                case NetHandler.Status.NETNOTCONECT:
                    mListView.noticeNetNotConnect();
                    break;
                case NetHandler.Status.CONECTOVERTIME:
                    mListView.noticeConnectOvertime();
                    break;
                case NetHandler.Status.FALSE:
                    mListView.noticeLoadFailure();
                    break;
                case NetHandler.Status.DEFAULT:
                    break;
            }
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListAdapter = createAdapter();
            if (mListView != null)
                mListView.setAdapter(mListAdapter);
        }else {
            mListAdapter.notifyDataSetChanged();
        }
        if (mListView != null) {
            if (mListView.isPullUpLoading()) {
                mListView.setPullUpLoading(false);
            }
            if (mListView.isRefreshing()) {
                mListView.setRefreshing(false);
            }
        }
    }

    private SimpleAdapter createAdapter() {
        mListAdapter =  new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.article_item_list,
                new String[]{"title", "content", "time", "ownerName"},
                new int[]{R.id.article_title, R.id.article_content,
                        R.id.article_date, R.id.article_author});
        return mListAdapter;
    }

    public NoticeListFragment refresh() {
        clearItems();
        if (mListView != null) mListView.resetPullUpLoad();
        NetDataPlus.getArticleList(context, 1, this);
        return this;
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }
}
