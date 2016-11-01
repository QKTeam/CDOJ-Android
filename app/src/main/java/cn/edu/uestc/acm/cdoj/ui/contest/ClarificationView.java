package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ClarificationData;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/25.
 */
public class ClarificationView extends ListViewWithGestureLoad implements ConvertNetData {

    private static final String TAG = "讨论";

    private List<ClarificationData> clarificationDataList;
    private BaseAdapter mListAdapter;
    private DetailWebView clarificationDetail;
    private AlertDialog detailAlert;
    private PageInfo mPageInfo;
    private Context context;
    private int contestId;

    public ClarificationView(Context context) {
        this(context, -1, null);
    }

    public ClarificationView(Context context, AttributeSet attrs) {
        this(context, -1, attrs);
    }

    public ClarificationView(Context context, int contestId) {
        this(context, contestId, null);
    }

    public ClarificationView(Context context, int contestId, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.contestId = contestId;
        init();
    }

    private void init() {
        clarificationDataList = new ArrayList<>();
        mListAdapter = new ClarificationAdapter(context, clarificationDataList);
    }

    @Override
    public void onListRefresh() {
        refresh();
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getContestComment(context, contestId, mPageInfo.currentPage + 1, ClarificationView.this);
        }
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (detailAlert == null) {
            setupDetailAlert();
        }
        clarificationDetail.setJsonString(clarificationDataList.get(position).jsonString);
        detailAlert.show();
    }

    public void setupDetailAlert() {
        clarificationDetail = new DetailWebView(context, NetData.ARTICLE_DETAIL);
        detailAlert = new AlertDialog.Builder(getContext())
                .setView(clarificationDetail)
                .setNegativeButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        detailAlert.cancel();
                    }
                })
                .create();
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ListReceive<ClarificationData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ClarificationData>>() {});

        if (!listReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }
        mPageInfo = listReceive.getPageInfo();
        result.setContent(convertNetData(listReceive.getList()));

        if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(Result.FINISH);
        } else if (mPageInfo.totalItems == 0) {
            result.setStatus(Result.DATAISNULL);
        } else {
            result.setStatus(Result.SUCCESS);
        }
        return result;
    }

    private List<ClarificationData> convertNetData(List<ClarificationData> clarificationDataListTem) {
        for (ClarificationData clarificationData : clarificationDataListTem) {
            clarificationData.jsonString = JSON.toJSONString(clarificationData);
            clarificationData.contentWithoutLink = clarificationData.getContent().replaceAll("!\\[title].*\\)", "[图片]");
            clarificationData.timeString = TimeFormat.getFormatDate(clarificationData.getTime());
            clarificationData.avatar = Resource.getDefaultLogo();
        }
        return clarificationDataListTem;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getDataType()) {
            case NetData.CONTEST_COMMENT:
                if (!hasAdapter()) {
                    setListAdapter(mListAdapter);
                }
                if (result.getContent() != null) {
                    int lastCount = clarificationDataList.size();
                    clarificationDataList.addAll((List<ClarificationData>) result.getContent());
                    mListAdapter.notifyDataSetChanged();
                    getAvatar(lastCount);
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
                break;

            case NetData.AVATAR:
                int position = (int) result.getExtra();
                clarificationDataList.get(position).avatar = (BitmapDrawable) result.getContent();
                View view = findViewWithTag(position);
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.contestClarification_header))
                            .setImageDrawable((BitmapDrawable) result.getContent());
                }
        }
    }

    private void getAvatar(int lastCount) {
        for (int i = lastCount; i < clarificationDataList.size(); i++) {
            NetDataPlus.getAvatar(context, clarificationDataList.get(i).getOwnerEmail(), i, this);
        }
    }

    public ClarificationView refresh() {
        return refresh(contestId);
    }

    public ClarificationView refresh(int contestId) {
        if (contestId > 0) {
            setRefreshing(true);
            clear();
            NetDataPlus.getContestComment(context, contestId, 1, ClarificationView.this);
        } else {
            noticeRefreshComplete();
        }
        return this;
    }

    public void clear() {
        clarificationDataList.clear();
        mListAdapter.notifyDataSetChanged();
    }
}
