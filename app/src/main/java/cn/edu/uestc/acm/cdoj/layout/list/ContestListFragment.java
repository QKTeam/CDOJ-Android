package cn.edu.uestc.acm.cdoj.layout.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.ItemContentActivity;
import cn.edu.uestc.acm.cdoj.LoginActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.GetInformation;
import cn.edu.uestc.acm.cdoj.layout.detail.ContestFragment;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends ListFragmentWithGestureLoad implements ViewHandler {
    private SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> listItems = new ArrayList<>();
    boolean isTwoPane;
    private String key;
    private int clickPosition = -1;
    private int clickContestID = -1;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
        refresh();
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage < getPageInfo().totalPages) {
                        NetData.getContestList(getPageInfo().currentPage + 1, key, ContestListFragment.this);
                    }else {
                        stopPullUpLoad();
                    }
                }
            });
        }
    }

    @Override
    public void addListItem(Map<String ,Object> listItem) {
        listItems.add(listItem);
    }

    public void setKey(String key) {
        this.key = key;
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
                Global.currentMainActivity, listItems, R.layout.contest_item_list,
                new String[]{"title", "date", "timeLimit", "id", "status", "permission"},
                new int[]{R.id.contest_title, R.id.contest_date, R.id.contest_timeLimit,
                        R.id.contest_id, R.id.contest_status, R.id.contest_permission});
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(final ListView l, View v, int position, long id) {
        int contestId = (int) listItems.get(position).get("id");
        addClickInfo(position, contestId);
        if (!Global.userManager.isLogin()){
            reminderUnLogin();
            return;
        }
        if (listItems.get(position).get("permission").equals("Private")){
            reminderEnterPassword();
            return;
        }
        showAlreadyClick();
        NetData.loginContest(contestId, "123456789", ContestListFragment.this);
    }

    private void addClickInfo(int position, int id) {
        clickPosition = position;
        clickContestID = id;
    }

    private void showAlreadyClick() {
        progressDialog = ProgressDialog
                .show(rootView.getContext(), getString(R.string.getingContest),
                        getString(R.string.linking));
    }

    private void reminderUnLogin() {
        final boolean[] enter = {false};
        AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext())
                .setTitle("未登录")
                .setMessage("您还未登录，部分功能将无法使用")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Context context = rootView.getContext();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        if (listItems.get(clickPosition).get("permission").equals("Public")) {
            alert.setNeutralButton("直接进入", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDetail();
                }
            });
        }
        alert.show();
    }

    private void reminderEnterPassword() {
        final EditText passwordET = new EditText(rootView.getContext());
        new AlertDialog.Builder(rootView.getContext())
                .setTitle("请输入进入该比赛的密码：")
                .setView(passwordET)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlreadyClick();
                        NetData.loginContest(clickContestID,
                                passwordET.getText().toString(), ContestListFragment.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_LIST:
                if (((InfoList) data).result) {
                    setPageInfo(((InfoList) data).pageInfo);
                    addDataToList(((InfoList) data).getInfoList());
                } else {
                    getDataFailure();
                }
                notifyDataSetChanged();
                break;
            case ViewHandler.LOGIN_CONTEST:
                if (progressDialog != null) progressDialog.cancel();
                if ((boolean) data) {
                    showDetail();
                    return;
                }
                reminderLoginError();
        }

    }

    private void addDataToList(ArrayList<ContestInfo> infoList_C) {
        for (ContestInfo tem : infoList_C) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("title", tem.title);
            listItem.put("date", tem.timeString);
            listItem.put("timeLimit", tem.lengthString);
            listItem.put("id", tem.contestId);
            listItem.put("status", tem.status);
            listItem.put("permission", tem.typeName);
            addListItem(listItem);
        }
    }

    private void showDetail() {
        if (!isTwoPane) {
            showDetailOnActivity();
            return;
        }
        ((ContestFragment) Global.detailsContainer.getDetail(ViewHandler.CONTEST_DETAIL))
                .setContestID(clickContestID)
                .refresh();
    }

    private void showDetailOnActivity() {
        Context context = rootView.getContext();
        Intent intent = new Intent(context, ItemContentActivity.class);
//        if (intent.resolveActivity(Global.currentMainActivity.getPackageManager()) != null) return;
        intent.putExtra("title", (String) listItems.get(clickPosition).get("title"));
        intent.putExtra("type", ViewHandler.CONTEST_DETAIL);
        intent.putExtra("id",clickContestID);
        context.startActivity(intent);
    }

    private void reminderLoginError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext())
                .setMessage("密码错误或者您无权查看该比赛！")
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        if (listItems.get(clickPosition).get("permission").equals("Private")) {
            alert.setMessage("密码错误，请重新输入！");
        } else {
            alert.setMessage("您无查看该比赛的相关权限！");
        }
        alert.show();
    }


    public ContestListFragment refresh() {
        continuePullUpLoad();
        listItems.clear();
        NetData.getContestList(1, key, this);
        return this;
    }
}
