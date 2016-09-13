package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.list.PullUpLoadListView;
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
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    continuePullUpLoad();
                    Global.netContent.getContestPart(ViewHandler.CONTEST_COMMENT, contestID, 1);
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage != getPageInfo().totalPages) {
                        Global.netContent.getContestPart(ViewHandler.CONTEST_COMMENT,contestID, getPageInfo().currentPage+1);
                    } else {
                        stopPullUpLoad();
                    }
                }
            });
            if (contestID != -1) {
                Global.netContent.getContestPart(ViewHandler.CONTEST_COMMENT, contestID, 1);
            }
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
                Global.currentMainActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{/*"header",*/ "content", "date", "author"},
                new int[]{/*R.id.contestClarification_header,*/ R.id.contestClarification_content,
                        R.id.contestClarification_date, R.id.contestClarification_author});
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
        if (getListView() != null) {
            Global.netContent.getContestPart(ViewHandler.CONTEST_COMMENT, contestID, 1);
        }
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
                listItem.put("date", tem.timeString);
                listItem.put("author", tem.ownerName);
                addListItem(listItem);
            }
        }else {
            getDataFailure();
        }
        notifyDataSetChanged();
    }
}
