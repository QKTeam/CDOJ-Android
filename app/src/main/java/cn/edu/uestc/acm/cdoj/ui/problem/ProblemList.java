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
import java.util.Collection;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;

/**
 * Created by great on 2016/8/17.
 */
public class ProblemList extends ListViewWithGestureLoad implements ConvertNetData {
    private List<ProblemData> problemDataList = new ArrayList<>();
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;

    public ProblemList(Context context) {
        this(context, null);
    }

    public ProblemList(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mListAdapter = new ProblemAdapter(context, problemDataList);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onListRefresh() {
        resetPullUpLoad();
        problemDataList.clear();
        mListAdapter.notifyDataSetChanged();
        NetDataPlus.getProblemList(getContext(), 1, ProblemList.this);
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getProblemList(getContext(), mPageInfo.currentPage + 1, ProblemList.this);
        }
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Global.isTwoPane()) {
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
}
