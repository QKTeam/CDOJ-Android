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
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
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
    public static final int TRIED = 0;
    public static final int SOLVED = 1;
    public static final int THEFIRSTSOLVED = 2;
    public static final int DIDNOTHING = 2;

    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ListViewWithGestureLoad mListView;
    private Context context;
    private int contestID;
    private int problemsCount;
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

    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> listItem = listItems.get(position);
                ListView itemDetailListView = new ListView(context);
                ArrayList<Map<String, Object>> itemProblemsStatusList
                        = (ArrayList<Map<String, Object>>) listItems.get(position).get("problemsStatus");
                itemDetailListView.addHeaderView(setupItemListHeader(listItem));
                itemDetailListView.setAdapter(setupItemListAdapter(itemProblemsStatusList));
                showItemList(itemDetailListView);
            }
        });
    }

    private View setupItemListHeader(Map<String, Object> listItem) {
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

    private ListAdapter setupItemListAdapter(final ArrayList<Map<String, Object>> problemsStatusMapList) {
        SimpleAdapter adapter = new SimpleAdapter(
                Global.currentMainUIActivity, problemsStatusMapList, R.layout.contest_rank_list_item_detail,
                new String[]{"solvedStatus", "problemOrder", "solvedTime", "failureCount", "isTheFirstSolved"},
                new int[]{R.id.contestRankItem_container, R.id.contestRankItem_ProblemOrder, R.id.contestRankItem_solvedTime,
                        R.id.contestRankItem_failureCount, R.id.contestRankItem_isTheFirstSuccess});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof TextView) {
                    ((TextView) view).setText(String.valueOf(data));
                    return true;
                } else if (view instanceof LinearLayout) {
                    switch ((int) data) {
                        case THEFIRSTSOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_green));
                            break;
                        case SOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_halfGreen));
                            break;
                        case TRIED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_yellow));
                            break;
                        default:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_gray));
                    }
                    return true;
                }
                return false;
            }
        });
        return adapter;
    }

    private void showItemList(View itemDetailView) {
        new AlertDialog.Builder(context)
                .setView(itemDetailView)
                .setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_RANK:
                Rank rankInfo = (Rank) data;
                if (rankInfo.result) {
                    ArrayList<Rank.Performance> infoList_rank = rankInfo.getPerformanceList();
                    if (infoList_rank.size() == 0) {
                        mListView.setDataIsNull();
                        notifyDataSetChanged();
                        return;
                    }
                    for (int i = 0; i != infoList_rank.size(); ++i) {
                        Rank.Performance competitorInfo = infoList_rank.get(i);
                        this.listItems.add(getListItem(competitorInfo));
                        userManager.getAvatar(competitorInfo.email, i, this);
                    }
                }else {
                    mListView.setPullUpLoadFailure();
                }
                notifyDataSetChanged();
            case ViewHandler.AVATAR:
                Object[] dataReceive = (Object[]) data;
                int position = (int) dataReceive[0];
                if (position < listItems.size())
                    listItems.get(position).put("header", dataReceive[1]);
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.contestClarification_header))
                            .setImageBitmap((Bitmap) dataReceive[1]);
                }
        }
    }

    private Map<String, Object> getListItem(Rank.Performance competitorInfo) {
        Map<String, Object> listItem = new HashMap<>();
        listItem.put("header", R.drawable.logo);
        listItem.put("rank", competitorInfo.rank);
        listItem.put("account", competitorInfo.name);
        listItem.put("nickName", competitorInfo.nickName);
        listItem.put("solvedNum", competitorInfo.solved);
        listItem.put("problemsStatus", getProblemsStatusMapList(competitorInfo.getProblemStatusList()));
        return listItem;
    }

    private ArrayList<Map<String, Object>> getProblemsStatusMapList(ArrayList<Rank.Performance.ProblemStatus> problemStatusList) {
        ArrayList<Map<String, Object>> problemsStatusMapList = new ArrayList<>();
        for (int j = 0; j != problemsCount; ++j) {
            Map<String, Object> problemStatusMap = new HashMap<>();
            Rank.Performance.ProblemStatus temProbStatus = problemStatusList.get(j);
            problemStatusMap.put("problemOrder", String.valueOf((char) ('A' + j)));
            problemStatusMap.put("failureCount", temProbStatus.tried);
            problemStatusMap.put("solvedTime", TimeFormat.getFormatTime(temProbStatus.solvedTime));
            if (temProbStatus.firstBlood) {
                problemStatusMap.put("solvedStatus", THEFIRSTSOLVED);
            } else if (temProbStatus.solved) {
                problemStatusMap.put("solvedStatus", SOLVED);
            } else if (temProbStatus.tried > 0) {
                problemStatusMap.put("solvedStatus", TRIED);
            } else {
                problemStatusMap.put("solvedStatus", DIDNOTHING);
            }
            problemsStatusMapList.add(problemStatusMap);
        }
        return problemsStatusMapList;
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) setupRankListAdapter();
        mListAdapter.notifyDataSetChanged();
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private void setupRankListAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"header", "rank", "nickName", "account", "problemsStatus"},
                new int[]{R.id.contestRank_header, R.id.contestRank_rank, R.id.contestRank_nickName,
                        R.id.contestRank_account, R.id.contestRank_problemsStatus}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView.setTag(position);
                return super.getView(position, convertView, parent);
            }
        };
        setupRankAdapterBinder();
        mListView.setAdapter(mListAdapter);
    }

    private void setupRankAdapterBinder() {
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
                    setupRankProblemsStatusView(view, data);
                    return true;
                }
                return false;
            }
        });
    }

    public void setupRankProblemsStatusView(View view, Object data) {
        Context context = view.getContext();
        ArrayList<Map<String, Object>> problemsStatusList = (ArrayList<Map<String, Object>>) data;
        for (int i = 0; i < 9 && i < problemsCount; ++i) {
            Map<String, Object> problemStatus = problemsStatusList.get(i);
            FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            ((TextView) markContainer.getChildAt(1)).setText((String) problemStatus.get("problemOrder"));
            switch ((int) problemStatus.get("solvedStatus")) {
                case THEFIRSTSOLVED:
                    bg.setImageBitmap(DrawImage.draw(context, R.drawable.contest_rank_mark_bg_white,
                            RGBAColor.getColorMatrix(context, R.color.rank_green)));
                    break;
                case SOLVED:
                    bg.setImageBitmap(DrawImage.draw(context, R.drawable.contest_rank_mark_bg_white,
                            RGBAColor.getColorMatrix(context, R.color.rank_halfGreen)));
                    break;
                case TRIED:
                    bg.setImageBitmap(DrawImage.draw(context, R.drawable.contest_rank_mark_bg_white,
                            RGBAColor.getColorMatrix(context, R.color.rank_yellow)));
                    break;
                default:
                    bg.setImageBitmap(DrawImage.draw(context, R.drawable.contest_rank_mark_bg_white,
                            RGBAColor.getColorMatrix(context, R.color.rank_gray)));
            }
        }
        if (problemsCount >= 9) {
            ((TextView) ((LinearLayout) view).getChildAt(9)).setText("......");
        } else {
            for (int i = problemsCount; i < 9; i++) {
                ((LinearLayout) view).getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void refresh() {
        if (contestID != -1) refresh(contestID, problemsCount);
    }

    public void refresh(int contestID, int probCount) {
        refreshed = true;
        this.contestID = contestID;
        this.problemsCount = probCount;
        listItems.clear();
        NetData.getContestRank(contestID, this);
    }
}
