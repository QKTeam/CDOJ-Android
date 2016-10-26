package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
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
public class ContestClarification extends Fragment implements ConvertNetData {

    private int contestID = -1;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;
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
                if (mPageInfo != null && mPageInfo.currentPage < mPageInfo.totalPages) {
                    NetDataPlus.getContestComment(context, contestID, mPageInfo.currentPage + 1, ContestClarification.this);
                } else {
                    mListView.noticeLoadFinish();
                }
            }
        });
        setupOnListItemClick();
        if (mListAdapter != null) mListView.setAdapter(mListAdapter);
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    private void setupOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProgressDialog = ProgressDialog
                        .show(context, getString(R.string.getting), getString(R.string.linking));
                NetDataPlus.getArticleDetail(context, (int) listItems.get(position).get("articleId"),
                        new ContestClarificationAlert(context, mProgressDialog));
            }
        });
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        Map<String, Object> listMap = JSON.parseObject(jsonString);
        mPageInfo = new PageInfo((Map) listMap.get("pageInfo"));
        convertNetData((List<Map<String, Object>>) listMap.get("list"));

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

    private void convertNetData(List<Map<String, Object>> list) {
        for (Map<String, Object> temMap : list) {
            temMap.put("content", ((String) temMap.get("content")).replaceAll("!\\[title].*\\)", "[图片]"));
            temMap.put("time", TimeFormat.getFormatDate((long) temMap.get("time")));
            temMap.put("header", R.drawable.logo);
            listItems.add(temMap);
            NetDataPlus.getAvatar(context, (String) temMap.get("ownerEmail"), listItems.size() - 1, this);
        }
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getType()) {
            case NetData.CONTEST_COMMENT:
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
                break;

            case NetData.AVATAR:
                int position = (int) result.getExtra();
                if (position < listItems.size())
                    listItems.get(position).put("header", result.getContent());
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.contestClarification_header))
                            .setImageDrawable((BitmapDrawable) result.getContent());
                }
        }
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListAdapter = setupAdapter();
            if (mListView != null) mListView.setAdapter(mListAdapter);
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
                Global.currentMainUIActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{"header", "ownerName", "time", "content"},
                new int[]{R.id.contestClarification_header, R.id.contestClarification_user,
                        R.id.contestClarification_submitDate, R.id.contestClarification_content}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                v.setTag(position);
                return v;
            }
        };
        mListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView) {
                    if (data instanceof Integer) {
                        ((ImageView) view).setImageResource((int) data);
                        return true;
                    } else if (data instanceof Bitmap) {
                        ((ImageView) view).setImageBitmap((Bitmap) data);
                        return true;
                    }
                } else if (view instanceof TextView) {
                    ((TextView) view).setText(data.toString());
                    return true;
                }
                return false;
            }
        });
        return mListAdapter;
    }

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public ContestClarification refresh(int contestID) {
        if (contestID < 1 || context == null) return this;
        return refresh(context, contestID);
    }

    public ContestClarification refresh(Context context, int contestID) {
        if (contestID < 1 || context == null) return this;
        clearItems();
        this.context = context;
        this.contestID = contestID;
        if (mListView != null) mListView.resetPullUpLoad();
        NetDataPlus.getContestComment(context, contestID, 1, this);
        return this;
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }
}
