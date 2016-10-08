package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Rank;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

import static cn.edu.uestc.acm.cdoj.ui.modules.Global.userManager;

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends Fragment implements ViewHandler {
    public static final int NOTHING = 0;
    public static final int TRIED = 1;
    public static final int SOLVED = 2;
    public static final int THEFIRSTSOLVED = 3;

    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ListViewWithGestureLoad mListView;
    private Context context;
    private int contestID;
    private int probCount;
    private boolean refreshed;

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
        configOnListItemClick();
        if (refreshed) refresh();
    }

    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) createAdapter();
        mListAdapter.notifyDataSetChanged();
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> listItem = listItems.get(position);
                ListView itemDetailListView = new ListView(context);
                ArrayList<Map<String, Object>> itemProblemsStatusList
                        = (ArrayList<Map<String, Object>>) listItems.get(position).get("problemsStatus");
                itemDetailListView.setAdapter(generateItemDetailListViewAdapter(itemProblemsStatusList));
                itemDetailListView.addHeaderView(generateItemDetailListHeader(listItem));
                showItemDetail(itemDetailListView);
            }
        });
    }

    private View generateItemDetailListHeader(Map<String, Object> listItem) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_list_item_detail_header, null);
        ImageView headerImageView = (ImageView) headerView.findViewById(R.id.contestRankItemHeader_header);
        if (listItem.get("header") instanceof Bitmap) {
            headerImageView.setImageBitmap((Bitmap) listItem.get("header"));
        } else {
            headerImageView.setImageResource((int) listItem.get("header"));
        }
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_nickName))
                .setText((String) listItem.get("nickName"));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_rank))
                .setText((String) listItem.get("rank"));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solvedNum))
                .setText(String.valueOf((int) listItem.get("solvedNum")));
        return headerView;
    }

    private ListAdapter generateItemDetailListViewAdapter(final ArrayList<Map<String, Object>> problemsStatusList) {
        return new SimpleAdapter(
                Global.currentMainUIActivity, problemsStatusList, R.layout.contest_rank_list_item_detail,
                new String[]{"probOrder", "solvedTime", "failureNum", "isFirstSuccess"},
                new int[]{R.id.contestRankItem_Prob, R.id.contestRankItem_solvedTime,
                        R.id.contestRankItem_failureCount, R.id.contestRankItem_isFirstSuccess}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                switch ((int) (problemsStatusList.get(position).get("solvedStatus"))) {
                    case ContestRank.TRIED:
                        v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.rank_yellow));
                        return v;
                    case ContestRank.SOLVED:
                        v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.rank_halfGreen));
                        return v;
                    case ContestRank.THEFIRSTSOLVED:
                        v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.rank_green));
                        return v;
                }
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.rank_gray));
                return v;
            }
        };
    }

    private void showItemDetail(View itemDetailView) {
        new AlertDialog.Builder(context)
                .setView(itemDetailView)
                .setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void createAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"header", "rank", "nickName", "account", "solvedDetail"},
                new int[]{R.id.contestRank_header, R.id.contestRank_rank, R.id.contestRank_nickName,
                        R.id.contestRank_account, R.id.contestRank_solved}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView.setTag(position);
                return super.getView(position, convertView, parent);
            }
        };
        generateAdapterBinder();
        mListView.setAdapter(mListAdapter);
    }

    private void generateAdapterBinder() {
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
                } else if (view instanceof LinearLayout) {
                    configSolveProblemsDetailView(view, data);
                }
                return false;
            }
        });
    }

    public void configSolveProblemsDetailView(View view, Object data) {
        int[] solvedDetail = (int[]) data;
        char text = 'A';
        for (int i = 0; i < 9 && i < probCount; ++i) {
            FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            TextView tx = (TextView) markContainer.getChildAt(1);
            tx.setText(String.valueOf((char) (text + i)));
            if (((solvedDetail[2] >> i) & 1) == 1) {
                bg.setImageResource(R.drawable.contest_rank_mark_bg_green);
            } else if (((solvedDetail[1] >> i) & 1) == 1) {
                bg.setImageResource(R.drawable.contest_rank_mark_bg_green_half);
            } else if (((solvedDetail[0] >> i) & 1) == 1) {
                bg.setImageResource(R.drawable.contest_rank_mark_bg_yellow);
            } else
                bg.setImageResource(R.drawable.contest_rank_mark_bg_gray);
        }
        if (probCount >= 9) {
            TextView tx = (TextView) ((LinearLayout) view).getChildAt(9);
            tx.setText("......");
        } else {
            for (int i = probCount; i < 9; i++) {
                FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
                markContainer.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setContestID(int id) {
        contestID = id;
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_RANK:
                Rank rankInfo = (Rank) data;
                if (rankInfo.result) {
                    ArrayList<Rank.Performance> infoList_rank = rankInfo.getPerformanceList();
                    for (int i = 0; i != infoList_rank.size(); ++i) {
                        Rank.Performance tem = infoList_rank.get(i);
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("header", R.drawable.logo);
                        listItem.put("rank", "Rank  " + tem.rank);
                        listItem.put("account", tem.name);
                        listItem.put("nickName", tem.nickName);
                        listItem.put("solvedNum", tem.solved);
                        ArrayList<Rank.Performance.ProblemStatus> problemStatusList = tem.getProblemStatusList();
                        probCount = problemStatusList.size();
                        Collections.reverse(problemStatusList);
                        int[] solvedDetail = {0, 0, 0};
                        ArrayList<Map<String, Object>> problemStatus = new ArrayList<>();
                        for (int j = 0; j != probCount; ++j) {
                            Map<String, Object> temStatus = new HashMap<>();
                            Rank.Performance.ProblemStatus temProbStatus = problemStatusList.get(j);
                            temStatus.put("probOrder", String.valueOf((char) ('A' + (probCount - j -1))));
                            temStatus.put("solvedTime", TimeFormat.getFormatTime(temProbStatus.solvedTime));
                            temStatus.put("failureNum", temProbStatus.tried);
                            if (temProbStatus.firstBlood) {
                                temStatus.put("isFirstSuccess", "是");
                            }else temStatus.put("isFirstSuccess", "否");
                            problemStatus.add(temStatus);
                            solvedDetail[0] = solvedDetail[0] << 1;
                            solvedDetail[1] = solvedDetail[1] << 1;
                            solvedDetail[2] = solvedDetail[2] << 1;
                            if (temProbStatus.firstBlood){
                                ++solvedDetail[2];
                                temStatus.put("solvedStatus", ContestRank.THEFIRSTSOLVED);
                            }else if (temProbStatus.solved){
                                ++solvedDetail[1];
                                temStatus.put("solvedStatus", ContestRank.SOLVED);
                            }else if (temProbStatus.tried > 0){
                                ++solvedDetail[0];
                                temStatus.put("solvedStatus", ContestRank.TRIED);
                            }else temStatus.put("solvedStatus", ContestRank.NOTHING);
                        }
                        Collections.reverse(problemStatus);
                        listItem.put("problemsStatus", problemStatus);
                        listItem.put("solvedDetail", solvedDetail);
                        listItem.put("detail", problemStatusList);
                        addListItem(listItem);
                        userManager.getAvatar(tem.email, i, this);
                    }
                }
                notifyDataSetChanged();
                return;
            case ViewHandler.AVATAR:
                Object[] dataReceive = (Object[]) data;
                int position = (int) dataReceive[0];
                if (position < listItems.size())
                    listItems.get(position).put("header", dataReceive[1]);
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ImageView headerImage = (ImageView) view.findViewById(R.id.contestClarification_header);
                    if (headerImage != null) headerImage.setImageBitmap((Bitmap) dataReceive[1]);
                }
        }
    }

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public void refresh(int contestID) {
        refreshed = true;
        this.contestID = contestID;
        listItems.clear();
        NetData.getContestRank(contestID, this);
    }
}
