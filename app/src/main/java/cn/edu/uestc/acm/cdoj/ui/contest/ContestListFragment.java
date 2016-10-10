package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.LoginActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.ui.modules.list.MainList;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends Fragment implements ViewHandler, MainList{
    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String,Object>> listItems = new ArrayList<>();
    private String key;
    private int clickPosition = -1;
    private int clickContestID = -1;
    private ProgressDialog progressDialog;
    private FragmentManager mFragmentManager;
    private ListViewWithGestureLoad mListView;
    private MainList.OnRefreshProgressListener progressListener;
    private PageInfo mPageInfo;
    private Context context;
    private boolean refreshed;
    private boolean hasSetProgressListener;
    private int progressContainerVisibility = View.VISIBLE;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mListView = new ListViewWithGestureLoad(context);
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mListView.setOnPullUpLoadListener(new ListViewWithGestureLoad.OnPullUpLoadListener(){
            @Override
            public void onPullUpLoading() {
                if (mPageInfo != null && mPageInfo.currentPage < mPageInfo.totalPages) {
                    NetData.getContestList(mPageInfo.currentPage + 1, key, ContestListFragment.this);
                }else {
                    mListView.setPullUpLoadFinish();
                }
            }
        });
        mListView.setProgressContainerVisibility(progressContainerVisibility);
        configOnListItemClick();
        if (refreshed) refresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int contestId = (int) listItems.get(position).get("id");
                addInfoOfCurrentClick(position, contestId);
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
                .setTitle("未登录")
                .setMessage("您还未登录，部分功能将无法使用")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        final EditText passwordET = new EditText(context);
        new AlertDialog.Builder(context)
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
                    mPageInfo = ((InfoList) data).pageInfo;
                    ArrayList<ContestInfo> infoList_C = ((InfoList) data).getInfoList();
                    if (infoList_C.size() == 0){
                        mListView.setDataIsNull();
                        notifyDataSetChanged();
                        return;
                    }
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
                    if (hasSetProgressListener && mPageInfo.currentPage == 1) {
                        progressListener.end();
                    }
                } else {
                    mListView.setPullUpLoadFailure();
                }
                notifyDataSetChanged();
                break;
            case ViewHandler.LOGIN_CONTEST:
                if (mPageInfo.currentPage == 1 && progressDialog != null){
                    progressDialog.cancel();
                }
                if ((boolean) data) {
                    showDetail();
                    return;
                }
                reminderLoginError();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (mListAdapter == null) mListView.setAdapter(createAdapter());
        else mListAdapter.notifyDataSetChanged();
        if (mListView.isPullUpLoading()) {
            mListView.setPullUpLoading(false);
        }
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private ListAdapter createAdapter() {
        mListAdapter =  new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_item_list,
                new String[]{"title", "date", "timeLimit", "id", "status", "permission"},
                new int[]{R.id.contest_title, R.id.contest_date, R.id.contest_timeLimit,
                        R.id.contest_id, R.id.contest_status, R.id.contest_permission});
        return mListAdapter;
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

    private void showDetailOnActivity() {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("title", (String) listItems.get(clickPosition).get("title"));
        intent.putExtra("type", ViewHandler.CONTEST_DETAIL);
        intent.putExtra("id",clickContestID);
        context.startActivity(intent);
    }

    private void reminderLoginError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
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

    @Override
    public void setRefreshProgressListener(OnRefreshProgressListener listener) {
        hasSetProgressListener = true;
        progressListener = listener;
    }

    @Override
    public void addListItem(Map<String ,Object> listItem) {
        listItems.add(listItem);
    }

    @Override
    public ListViewWithGestureLoad getListView() {
        return mListView;
    }

    @Override
    public void setProgressContainerVisibility(int visibility) {
        progressContainerVisibility = visibility;
        if (mListView != null) {
            mListView.setProgressContainerVisibility(visibility);
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContestListFragment refresh() {
        refreshed = true;
        if (mListView != null) {
            if (progressListener != null) progressListener.start();
            mListView.resetPullUpLoad();
            listItems.clear();
            NetData.getContestList(1, key, this);
        }
        return this;
    }
}
