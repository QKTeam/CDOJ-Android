package cn.edu.uestc.acm.cdoj.ui.problem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;

/**
 * Created by great on 2016/8/17.
 */
public class ProblemList extends ListViewWithGestureLoad implements ConvertNetData {
    private List<ProblemData> problemDataList;
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;
    private String key = "";
    private int startId = 1;

    public ProblemList(Context context) {
        this(context, null);
    }

    public ProblemList(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        problemDataList = new ArrayList<>();
        mListAdapter = new ProblemAdapter(context, problemDataList);
    }

    @Override
    public void onListRefresh() {
        refresh();
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getProblemList(getContext(), mPageInfo.currentPage + 1, key, startId, ProblemList.this);
        }
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Resource.isTwoPane()) {
            showDetailOnActivity(position);
        }
    }

    private void showDetailOnActivity(int position) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra("title", problemDataList.get(position).getTitle());
        intent.putExtra("type", NetData.PROBLEM_DETAIL);
        intent.putExtra("id", problemDataList.get(position).getProblemId());
        getContext().startActivity(intent);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ListReceive<ProblemData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ProblemData>>() {});
        if (!listReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }
        mPageInfo = listReceive.getPageInfo();
        result.setContent(listReceive.getList());

        if (mPageInfo.totalItems == 0) {
            result.setStatus(Result.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(Result.FINISH);
        } else {
            result.setStatus(Result.SUCCESS);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        if (!hasAdapter()) {
            setListAdapter(mListAdapter);
        }
        if (result.getContent() != null) {
            problemDataList.addAll((List<ProblemData>) result.getContent());
            mListAdapter.notifyDataSetChanged();
        }
        noticeLoadOrRefreshComplete();
        switch (result.getStatus()) {
            case Result.SUCCESS:
                break;
            case Result.DATAISNULL:
                noticeDataIsNull();
                break;
            case Result.FINISH:
                noticeLoadFinish();
                break;
            case Result.NETNOTCONECT:
                noticeNetNotConnect();
                break;
            case Result.CONECTOVERTIME:
                noticeConnectOvertime();
                break;
            case Result.FALSE:
                noticeLoadFailure();
                break;
            case Result.DEFAULT:
                break;
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public void clear() {
        resetPullUpLoad();
        problemDataList.clear();
        mListAdapter.notifyDataSetChanged();
    }

    public void refresh() {
        clear();
        setRefreshing(true);
        NetDataPlus.getProblemList(getContext(), 1, key, startId, ProblemList.this);
    }
}
