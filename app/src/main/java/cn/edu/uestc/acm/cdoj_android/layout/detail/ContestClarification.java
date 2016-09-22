package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.ItemContentActivity;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.list.PullUpLoadListView;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;

/**
 * Created by great on 2016/8/25.
 */
public class ContestClarification extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private int contestID = -1;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (contestID != -1) refresh();
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    continuePullUpLoad();
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage != getPageInfo().totalPages) {
                        NetData.getContestComment(contestID, getPageInfo().currentPage, ContestClarification.this);
                    } else {
                        stopPullUpLoad();
                    }
                }
            });
        }
        super.onActivityCreated(savedInstanceState);
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
                Global.currentMainActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{/*"header",*/ "user", "submitDate", "content"},
                new int[]{/*R.id.contestClarification_header,*/ R.id.contestClarification_user,
                        R.id.contestClarification_submitDate, R.id.contestClarification_content});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
        if (getListView() != null) {
            Global.netContent.getContestPart(ViewHandler.CONTEST_COMMENT, contestID, 1);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Context context = l.getContext();
        Intent intent = new Intent(context, ContestClarificationItemActivity.class);
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("id", Integer.parseInt((String) listItems.get(position).get("id")));
        context.startActivity(intent);
    }

    @Override
    public void show(int which, Object data, long time) {
        if (((InfoList) data).result){
            setPageInfo(((InfoList) data).pageInfo);
            ArrayList<ArticleInfo> infoList_clarification = ((InfoList) data).getInfoList();
            for (ArticleInfo tem : infoList_clarification) {
                Map<String, Object> listItem = new HashMap<>();
//                    listItem.put("header", tem.);
                listItem.put("content", tem.content);
                listItem.put("submitDate", tem.timeString);
                listItem.put("user", tem.ownerName);
                listItem.put("title", tem.title);
                listItem.put("id", tem.articleId);
                addListItem(listItem);
            }
        }else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }

    public void refresh() {
        if (contestID == -1) throw new IllegalStateException("Clarification's contestId is null");
        listItems.clear();
        NetData.getContestComment(contestID, 1, this);
    }
}
