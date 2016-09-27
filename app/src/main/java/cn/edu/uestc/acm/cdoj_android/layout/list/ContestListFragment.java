package cn.edu.uestc.acm.cdoj_android.layout.list;

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

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.ItemContentActivity;
import cn.edu.uestc.acm.cdoj_android.LoginActivity;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.GetInformation;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleFragment;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestFragment;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends ListFragmentWithGestureLoad implements ViewHandler {
    private SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> listItems = new ArrayList<>();
    boolean isTwoPane;
    private String key;
    private int clickPosition = -1;
    private int clickID = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = ((GetInformation) Global.currentMainActivity).isTwoPane();
        if (savedInstanceState == null) {
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    continuePullUpLoad();
                    Global.netContent.getContent(ViewHandler.CONTEST_LIST, 1);
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener(){
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage != getPageInfo().totalPages) {
                        NetData.getContestList(getPageInfo().currentPage + 1, key, ContestListFragment.this);
                    }else {
                        stopPullUpLoad();
                    }
                }
            });
            Global.netContent.getContent(ViewHandler.CONTEST_LIST, 1);
        }
    }

    @Override
    public void addListItem(Map<String ,Object> listItem) {
        listItems.add(listItem);
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
    public void onListItemClick(final ListView l, View v, final int position, long id) {
        final int contestId = Integer.parseInt((String) listItems.get(position).get("id"));
        if (!Global.userManager.isLogin()){
            unLogin(position, contestId);
            return;
        }
        if (listItems.get(position).get("permission").equals("Private")){
            privateContest(position, contestId);
            return;
        }
        setClickInfo(position, contestId);
        NetData.loginContest(contestId, "123456789", ContestListFragment.this);
    }

    private void unLogin(final int position, final int contestId) {
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
        if (listItems.get(position).get("permission").equals("Public")) {
            alert.setNeutralButton("直接进入", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDetail(position, contestId);
                }
            });
        }
        alert.show();
    }

    private void privateContest(final int position, final int contestId) {
        final EditText passwordET = new EditText(rootView.getContext());
        new AlertDialog.Builder(rootView.getContext())
                .setTitle("请输入进入该比赛的密码：")
                .setView(passwordET)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String password = passwordET.getText().toString();
                        setClickInfo(position, contestId);
                        NetData.loginContest(contestId, password, ContestListFragment.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void showDetail(int position, int id) {
        if (isTwoPane) {
            showDetailOnActivity(position, id);
        }
        ((ContestFragment) Global.detailsContainer.getDetail(ViewHandler.CONTEST_DETAIL)).refresh();
    }

    private void showDetailOnActivity(int position, int id) {
        Context context = rootView.getContext();
        Intent intent = new Intent(context, ItemContentActivity.class);
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("type", ViewHandler.CONTEST_DETAIL);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    private void showDetail() {
        if (clickPosition != -1 && clickID != -1) {
            showDetail(clickPosition, clickID);
        }
    }

    private void setClickInfo(int position, int id) {
        clickPosition = position;
        clickID = id;
    }

    public void clearList() {
        listItems.clear();
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_LIST:
                if (((InfoList) data).result) {
                    setPageInfo(((InfoList) data).pageInfo);
                    ArrayList<ContestInfo> infoList_C = ((InfoList) data).getInfoList();
                    for (ContestInfo tem : infoList_C) {
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("title", tem.title);
                        listItem.put("date", tem.timeString);
                        listItem.put("timeLimit", tem.lengthString);
                        listItem.put("id", "" + tem.contestId);
                        listItem.put("status", tem.status);
                        listItem.put("permission", tem.typeName);
                        addListItem(listItem);
                    }
                } else {
                    getDataFailure();
                }
                notifyDataSetChanged();
                break;
            case ViewHandler.LOGIN_CONTEST:
                if ((boolean) data) {
                    showDetail();
                    return;
                }
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

    }

    public void setKey(String key) {
        this.key = key;
    }
}
