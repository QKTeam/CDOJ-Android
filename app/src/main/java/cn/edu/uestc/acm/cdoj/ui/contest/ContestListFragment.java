package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

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
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.LoginActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.PageInfo;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends Fragment implements ConvertNetData {
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private int clickPosition = -1;
    private int clickContestID = -1;
    private ProgressDialog progressDialog;
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
                NetDataPlus.getContestList(context, mPageInfo.currentPage + 1, ContestListFragment.this);
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
                int contestId = (int) listItems.get(position).get("contestId");
                addInfoOfCurrentClick(position, contestId);
                if (Global.user == null) {
                    reminderUnLogin();
                    return;
                }
                if (listItems.get(position).get("typeName").equals("Private")) {
                    reminderEnterPassword();
                    return;
                }
                showAlreadyClick();
                NetDataPlus.loginContest(context, contestId, "123456789", ContestListFragment.this);
            }
        });
    }

    private void addInfoOfCurrentClick(int position, int id) {
        clickPosition = position;
        clickContestID = id;
    }

    private void showAlreadyClick() {
        progressDialog = ProgressDialog
                .show(context, getString(R.string.gettingContest),
                        getString(R.string.linking));
    }

    private void reminderUnLogin() {
        final boolean[] enter = {false};
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(getString(R.string.notLogin))
                .setMessage(getString(R.string.reminderNotLogin))
                .setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        if (listItems.get(clickPosition).get("typeName").equals("Public")) {
            alert.setNeutralButton(getString(R.string.enterDirectly), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDetail();
                }
            });
        }
        alert.show();
    }

    private void reminderEnterPassword() {
        final EditText passwordET = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.pleaseEnterPassword))
                .setView(passwordET)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlreadyClick();
                        NetDataPlus.loginContest(context, clickContestID,
                                passwordET.getText().toString(), ContestListFragment.this);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        switch (result.getType()) {
            case NetData.CONTEST_LIST:
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
                break;

            case NetData.LOGIN_CONTEST:
                boolean isSuccess = JSON.parseObject(jsonString)
                        .get("result")
                        .equals("success");
                if (isSuccess) {
                    result.setStatus(NetHandler.Status.SUCCESS);
                } else {
                    result.setStatus(NetHandler.Status.FALSE);
                }
        }
        return result;
    }

    private void convertNetData(List<Map<String, Object>> list) {
        for (Map<String, Object> temMap : list) {
            temMap.put("contestIdString", "ID:" + temMap.get("contestId"));
            temMap.put("time", TimeFormat.getFormatDate((long) temMap.get("time")));
            temMap.put("length", TimeFormat.getFormatTime((int) temMap.get("length")));
        }
        listItems.addAll(list);
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getType()) {
            case NetData.CONTEST_LIST:
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
                break;

            case NetData.LOGIN_CONTEST:
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                if (result.getStatus() == NetHandler.Status.SUCCESS) {
                    showDetail();
                } else {
                    reminderLoginError();
                }
        }

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
                Global.currentMainUIActivity, listItems, R.layout.contest_item_list,
                new String[]{"title", "time", "length", "contestIdString", "status", "typeName"},
                new int[]{R.id.contest_title, R.id.contest_date, R.id.contest_timeLimit,
                        R.id.contest_id, R.id.contest_status, R.id.contest_permission});
        return mListAdapter;
    }

    private void showDetailOnActivity() {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("title", (String) listItems.get(clickPosition).get("title"));
        intent.putExtra("type", NetData.CONTEST_DETAIL);
        intent.putExtra("id", clickContestID);
        context.startActivity(intent);
    }

    private void showDetail() {
        if (!Global.isTwoPane) {
            showDetailOnActivity();
            return;
        }
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        ContestFragment contest = new ContestFragment()
                .refresh(clickContestID);
        mTransaction.replace(R.id.ui_main_detail, contest);
        mTransaction.commit();
    }

    private void reminderLoginError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setMessage(getString(R.string.reminderLoginError))
                .setNeutralButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        if (listItems.get(clickPosition).get("typeName").equals("Private")) {
            alert.setMessage(getString(R.string.passwordIsWrong));
        } else {
            alert.setMessage(getString(R.string.noPermission));
        }
        alert.show();
    }

    public ContestListFragment refresh() {
        if (context == null) return this;
        return refresh(context);
    }

    public ContestListFragment refresh(Context context) {
        clearItems();
        if (mListView != null) mListView.resetPullUpLoad();
        NetDataPlus.getContestList(context, 1, this);
        return this;
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }
}
