package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.PageInfo;

/**
 * Created by great on 2016/8/25.
 */
public class ContestStatus extends Fragment implements ConvertNetData {
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID = -1;
    private int[] problemIDs;
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
                if (mPageInfo != null && mPageInfo.currentPage != mPageInfo.totalPages) {
                    NetDataPlus.getStatusList(context, contestID, mPageInfo.currentPage + 1, ContestStatus.this);
                } else {
                    mListView.noticeLoadFinish();
                }
            }
        });
        if (mListAdapter != null) mListView.setAdapter(mListAdapter);
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListAdapter = setupAdapter();
            if (mListView != null)
                mListView.setAdapter(mListAdapter);
        } else {
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

    private SimpleAdapter setupAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_status_item_list,
                new String[]{"returnType", "language", "length", "nickName", "timeCost", "memoryCost", "probOrder", "time"},
                new int[]{R.id.contestStatus_result, R.id.contestStatus_language, R.id.contestStatus_codeLength,
                        R.id.contestStatus_user, R.id.contestStatus_timeCost, R.id.contestStatus_memoryCost,
                        R.id.contestStatus_probOrder, R.id.contestStatus_submitDate});
        return mListAdapter;
    }

    public void setProblemIDs(int[] problemIDs) {
        this.problemIDs = problemIDs;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        Map<String, Object> statusMap = JSON.parseObject(jsonString);
        mPageInfo = new PageInfo((Map) statusMap.get("pageInfo"));
        convertNetData((ArrayList<Map<String, Object>>) statusMap.get("list"));

        if (mPageInfo.totalItems == 0) {
            result.setStatus(NetHandler.Status.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(NetHandler.Status.FINISH);
        } else {
            result.setStatus(NetHandler.Status.SUCCESS);
        }
        return result;
    }

    private void convertNetData(ArrayList<Map<String, Object>> list) {
        for (Map<String, Object> temMap : list) {
            temMap.put("length", temMap.get("length") + "B");
            temMap.put("timeCost", temMap.get("timeCost") + " ms");
            temMap.put("memoryCost", temMap.get("memoryCost") + " B");
            temMap.put("time", TimeFormat.getFormatDate((long) temMap.get("time")));
            if (problemIDs != null) {
                int i = 0;
                while (i < problemIDs.length && (int) temMap.get("problemId") != problemIDs[i]) ++i;
                temMap.put("probOrder", String.valueOf((char) ('A' + i)));
            }
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

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public ContestStatus refresh(int contestID) {
        if (contestID < 1) return this;
        clearItems();
        this.contestID = contestID;
        if (mListView != null) mListView.resetPullUpLoad();
        NetDataPlus.getStatusList(context, contestID, 1, this);
        return this;
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }
}
