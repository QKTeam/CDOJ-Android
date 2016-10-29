package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ContestListItemData;
import cn.edu.uestc.acm.cdoj.net.data.ContestLoginReceive;
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
    private ProgressDialog contestLoginProgress;
    private AlertDialog passwordDialog;
    private EditText passwordET;
    private AlertDialog unLoginDialog;
    private AlertDialog errorDialog;
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
        if (contestLoginProgress == null) {
            contestLoginProgress = new ProgressDialog(context);
            contestLoginProgress.setTitle(context.getString(R.string.gettingContest));
            contestLoginProgress.setMessage(context.getString(R.string.linking));
        }
        int contestId = contestListItemDataList.get(position).getContestId();
        clickPosition = position;
        clickContestID = contestId;
        if (contestListItemDataList.get(position).getTypeName().equals("Public")) {
            showDetail();
            return;
        }
        if (Global.getUser() == null) {
            reminderUnLogin();
        } else {
            if (contestListItemDataList.get(position).getTypeName().equals("Private")) {
                reminderEnterContestPassword();
            }else {
                contestLoginProgress.show();
                NetDataPlus.loginContest(context, contestId, NetData.sha1("123456"), this);
            }
        }
    }

    private void reminderUnLogin() {
        if (unLoginDialog == null) {
            unLoginDialog = new AlertDialog.Builder(context)
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
                    })
                    .create();
        }
        unLoginDialog.show();
    }

    private void reminderEnterContestPassword() {
        if (passwordDialog == null) {
            passwordET = new EditText(context);
            passwordET.setHint("比赛密码");
            passwordDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.pleaseEnterPassword))
                    .setView(passwordET)
                    .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contestLoginProgress.show();
                            NetDataPlus.loginContest(context, clickContestID, NetData.sha1(passwordET.getText().toString()), "private", ContestList.this);
                            passwordET.setText("");
                        }
                    })
                    .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            passwordET.setText("");
                        }
                    })
                    .create();
        }
        passwordDialog.show();
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        switch (result.getDataType()) {
            case NetData.CONTEST_LIST:
                ListReceive<ContestListItemData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ContestListItemData>>() {});
                if (!listReceive.getResult().equals("success")) {
                    result.setStatus(Result.FALSE);
                    return result;
                }

                mPageInfo = listReceive.getPageInfo();
                result.setContent(convertNetData(listReceive.getList()));

                if (mPageInfo.totalItems == 0) {
                    result.setStatus(Result.DATAISNULL);
                } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
                    result.setStatus(Result.FINISH);
                } else {
                    result.setStatus(Result.SUCCESS);
                }
                break;

            case NetData.LOGIN_CONTEST:
                ContestLoginReceive contestLoginReceive = JSON.parseObject(jsonString, ContestLoginReceive.class);
                if (contestLoginReceive.getResult().equals("success")) {
                    result.setStatus(Result.SUCCESS);
                }else {
                    result.setStatus(Result.FALSE);
                    if (contestLoginReceive.getError_msg() != null &&
                            contestLoginReceive.getError_msg().equals("Please login first.")) {
                        result.setContent(contestLoginReceive.getError_msg());
                    }
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
                if (result.getContent() != null) {
                    contestListItemDataList.addAll((List<ContestListItemData>) result.getContent());
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
                break;

            case NetData.LOGIN_CONTEST:
                contestLoginProgress.cancel();
                switch (result.getStatus()) {
                    case Result.SUCCESS:
                        showDetail();
                        break;
                    case Result.FALSE:
                        reminderLoginError(result);
                        break;
                    case Result.DATAISNULL:
                        break;
                    case Result.FINISH:
                        break;
                    case Result.NETNOTCONECT:
                        break;
                    case Result.CONECTOVERTIME:
                        break;
                    case Result.DEFAULT:
                        break;
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

    private void reminderLoginError(Result result) {
        if (result.getContent() != null && result.getContent().equals("Please login first.")) {
            reminderUnLogin();
            return;
        }
        if (result.getExtra() != null && result.getExtra().equals("private")) {
            new AlertDialog.Builder(context)
                    .setMessage("密码错误！")
                    .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            passwordDialog.show();
                        }
                    })
                    .show();
            return;
        }
        if (errorDialog == null) {
            errorDialog = new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.noPermission))
                    .setNeutralButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
        }
        errorDialog.show();
    }
}
