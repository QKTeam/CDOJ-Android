package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.net.data.StatusData;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/25.
 */
public class StatusView extends ListViewWithGestureLoad implements ConvertNetData {
    private static final String TAG = "状态";

    private List<StatusData> statusDataList = new ArrayList<>();
    private int contestId;
    private int[] contestProblemIds;
    private int problemId;
    private String userName;
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;
    private Context context;

    public StatusView(Context context) {
        this(context, null, -1, null, -1, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, null, -1, null, -1, attrs);
    }

    public StatusView(Context context, String userName) {
        this(context,userName, -1, null, -1, null);
    }

    public StatusView(Context context, int problemId) {
        this(context, null, -1, null, problemId, null);
    }

    public StatusView(Context context, int contestId, int[] contestProblemIds) {
        this(context, null, contestId, contestProblemIds, -1, null);
        init();
    }

    public StatusView(Context context, String userName, int contestId, int[] contestProblemIds, int problemId, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.userName = userName;
        this.contestId = contestId;
        this.contestProblemIds = contestProblemIds;
        this.problemId = problemId;
    }

    private void init() {
        mListAdapter = new StatusAdapter(context, statusDataList);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onListRefresh() {
        refresh();
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getStatusList(context, problemId, userName, contestId, mPageInfo.currentPage + 1, this);
        }
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ListReceive<StatusData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<StatusData>>() {
        });
        if (!listReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }

        mPageInfo = listReceive.getPageInfo();
        statusDataList.addAll(convertNetData(listReceive.getList()));

        if (mPageInfo.totalItems == 0) {
            result.setStatus(Result.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(Result.FINISH);
        } else {
            result.setStatus(Result.SUCCESS);
        }
        return result;
    }

    private List<StatusData> convertNetData(List<StatusData> statusDataListTem) {
        for (StatusData statusData : statusDataListTem) {
            if (statusData.getLength() > 1024) {
                statusData.lengthString = String.format(Locale.CHINA, "%.2fKB", ((double) statusData.getLength()) / 1024.0);
            } else {
                statusData.lengthString = statusData.getLength() + "B";
            }
            if (statusData.getMemoryCost() > 1024) {
                statusData.memoryCostString = String.format(Locale.CHINA, "%.2fMB", ((double) statusData.getMemoryCost()) / 1024.0);
            } else {
                statusData.memoryCostString = statusData.getMemoryCost() + "KB";
            }
            if (statusData.getTimeCost() > 1000) {
                statusData.timeCostString = String.format(Locale.CHINA, "%.2fs", ((double) statusData.getTimeCost()) / 1000.0);
            } else {
                statusData.timeCostString = statusData.getTimeCost() + "ms";
            }
            statusData.timeString = TimeFormat.getFormatDate(statusData.getTime());

            if (contestProblemIds != null) {
                int i = 0;
                while (i < contestProblemIds.length && statusData.getProblemId() != contestProblemIds[i]) {
                    ++i;
                }
                if (i < contestProblemIds.length) {
                    statusData.problemIdString = String.valueOf((char) ('A' + i));
                } else {
                    statusData.problemIdString = "?";
                }
            } else {
                statusData.problemIdString = "ID:" + statusData.getProblemId();
            }
        }
        return statusDataListTem;
    }

    private void updateProblemIdString() {
        if (contestProblemIds != null && statusDataList.size() > 0) {
            int i = 0;
            while (i < contestProblemIds.length && statusDataList.get(i).getProblemId() != contestProblemIds[i]) {
                ++i;
            }
            if (i < contestProblemIds.length) {
                statusDataList.get(i).problemIdString = String.valueOf((char) ('A' + i));
            } else {
                statusDataList.get(i).problemIdString = "?";
            }
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNetDataConverted(Result result) {
        mListAdapter.notifyDataSetChanged();
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

    public void setContestInfo(int contestId, int[] contestProblemIds) {
        this.contestId = contestId;
        this.contestProblemIds = contestProblemIds;
    }

    public void setContestProblemIds(int[] contestProblemIds) {
        if (contestProblemIds == null) return;
        this.contestProblemIds = contestProblemIds;
        updateProblemIdString();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public void reset() {
        contestId = -1;
        contestProblemIds = null;
        problemId = -1;
        userName = null;
    }

    public StatusView refresh() {
        clear();
        NetDataPlus.getStatusList(context, problemId, userName, contestId, 1, this);
        return this;
    }

    public void clear() {
        statusDataList.clear();
        mListAdapter.notifyDataSetChanged();
    }
}
