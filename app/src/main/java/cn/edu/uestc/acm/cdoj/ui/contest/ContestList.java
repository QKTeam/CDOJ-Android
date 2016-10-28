package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ContestListItemData;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.LoginActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/17.
 */
public class ContestList extends ListViewWithGestureLoad implements ConvertNetData {
    private List<ContestListItemData> contestListItemDataList = new ArrayList<>();
    private int clickPosition = -1;
    private int clickContestID = -1;
    private ProgressDialog progressDialog;
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;
    private Context context;

    public ContestList(Context context) {
        this(context, null);
    }

    public ContestList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mListAdapter = new ContestListAdapter(context, contestListItemDataList);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onListRefresh() {
        resetPullUpLoad();
        contestListItemDataList.clear();
        mListAdapter.notifyDataSetChanged();
        NetDataPlus.getContestList(context, 1, ContestList.this);
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getContestList(context, mPageInfo.currentPage + 1, ContestList.this);
        }
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        int contestId = contestListItemDataList.get(position).getContestId();
        clickPosition = position;
        clickContestID = contestId;
        if (Global.getUser() == null) {
            reminderUnLogin();
            return;
        }
        if (contestListItemDataList.get(position).getTypeName().equals("Private")) {
            reminderEnterPassword();
            return;
        }
        showAlreadyClick();
        showDetail();
    }

    private void showAlreadyClick() {
        progressDialog = ProgressDialog
                .show(context, context.getString(R.string.gettingContest),
                        context.getString(R.string.linking));
    }

    private void reminderUnLogin() {
        final boolean[] enter = {false};
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.notLogin))
                .setMessage(context.getString(R.string.reminderNotLogin))
                .setPositiveButton(context.getString(R.string.login), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        if (contestListItemDataList.get(clickPosition).getTypeName().equals("Public")) {
            alert.setNeutralButton(context.getString(R.string.enterDirectly), new DialogInterface.OnClickListener() {
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
                .setTitle(context.getString(R.string.pleaseEnterPassword))
                .setView(passwordET)
                .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlreadyClick();
                        NetDataPlus.loginContest(context, clickContestID,
                                passwordET.getText().toString(), ContestList.this);
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        switch (result.getDataType()) {
            case NetData.CONTEST_LIST:
                ListReceive<ContestListItemData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ContestListItemData>>() {
                });
                if (!listReceive.getResult().equals("success")) {
                    result.setStatus(Result.FALSE);
                    return result;
                }

                mPageInfo = listReceive.getPageInfo();
                contestListItemDataList.addAll(convertNetData(listReceive.getList()));

                if (mPageInfo.totalItems == 0) {
                    result.setStatus(Result.DATAISNULL);
                } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
                    result.setStatus(Result.FINISH);
                } else {
                    result.setStatus(Result.SUCCESS);
                }
                break;

            case NetData.LOGIN_CONTEST:
                boolean isSuccess = JSON.parseObject(jsonString)
                        .get("result")
                        .equals("success");
                if (isSuccess) {
                    result.setStatus(Result.SUCCESS);
                } else {
                    result.setStatus(Result.FALSE);
                }
        }
        return result;
    }

    private List<ContestListItemData> convertNetData(List<ContestListItemData> contestListItemDataListTem) {
        for (ContestListItemData contestListItemData : contestListItemDataListTem) {
            contestListItemData.timeString = TimeFormat.getFormatDate(contestListItemData.getTime());
            contestListItemData.lengthString = TimeFormat.getFormatTime(contestListItemData.getLength());
        }
        return contestListItemDataListTem;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getDataType()) {
            case NetData.CONTEST_LIST:
                noticeLoadOrRefreshComplete();
                mListAdapter.notifyDataSetChanged();
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

            case NetData.LOGIN_CONTEST:
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                if (result.getStatus() == Result.SUCCESS) {
                    showDetail();
                } else {
                    reminderLoginError();
                }
        }
    }

    private void showDetailOnActivity() {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("title", (contestListItemDataList.get(clickPosition).getTitle()));
        intent.putExtra("type", NetData.CONTEST_DETAIL);
        intent.putExtra("id", clickContestID);
        context.startActivity(intent);
    }

    private void showDetail() {
        if (!Global.isTwoPane()) {
            showDetailOnActivity();
        }
    }

    private void reminderLoginError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.reminderLoginError))
                .setNeutralButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        if (contestListItemDataList.get(clickPosition).getTypeName().equals("Private")) {
            alert.setMessage(context.getString(R.string.passwordIsWrong));
        } else {
            alert.setMessage(context.getString(R.string.noPermission));
        }
        alert.show();
    }
}
