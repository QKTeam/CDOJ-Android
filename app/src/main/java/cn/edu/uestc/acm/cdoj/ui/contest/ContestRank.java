package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends Fragment implements ViewHandler {
    public static final int DIDNOTHING = 0;
    public static final int TRIED = 1;
    public static final int SOLVED = 2;
    public static final int THEFIRSTSOLVED = 3;

    private SimpleAdapter mListAdapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ArrayList<String> problemsSolvedPercent = new ArrayList<>();
    private ListViewWithGestureLoad mListView;
    private Context context;
    private Integer contestID;
    private int problemsCount = 0;
    private boolean refreshed;

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
        setupOnListItemClick();
        if (refreshed) refresh();
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    private void setupOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = listItems.get(position);
                ArrayList<Map<String, Object>> problemsStatusList = (ArrayList<Map<String, Object>>) item.get("itemList");
                new ContestRankAlert(context, item, problemsStatusList).show();
            }
        });
    }

    @Override
    public void show(int which, Result result, long time) {
        switch (which) {
            case ViewHandler.CONTEST_RANK:
                problemsSolvedPercent.clear();
                listItems.clear();
                notifyDataSetChanged();
                if (result.result) {
                    Map<String, Object> rankMap = (Map<String, Object>) ((Map<String, Object>) result.getContent()).get("rankList");
                    ArrayList<Map<String, Object>> listItemsReceived = (ArrayList<Map<String, Object>>) rankMap.get("rankList");
                    setupReceivedData(rankMap, listItemsReceived);
                    if (listItems.size() == 0) {
                        mListView.setDataIsNull();
                        notifyDataSetChanged();
                        return;
                    }
                    mListView.setPullUpLoadFinish();
                }else {
                    mListView.setPullUpLoadFailure();
                }
                notifyDataSetChanged();
                return;

            case ViewHandler.AVATAR:
                int position = (int) result.getExtra();
                if (position < listItems.size()){
                    listItems.get(position).put("header", result.getContent());
                }
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.contestRank_header);
                    if (imageView != null) {
                        imageView.setImageBitmap((Bitmap) result.getContent());
                    }
                }
        }
    }

    private void setupReceivedData(Map<String, Object> rankMap, ArrayList<Map<String, Object>> listItemsReceived) {
        int compactorCount = listItemsReceived.size();
        for (Map<String, Object> temMap : (ArrayList<Map<String, Object>>) rankMap.get("problemList")) {
            int solved = (int) temMap.get("solved");
            int tried = (int) temMap.get("tried");
            float percent = 0;
            if (tried != 0) {
                percent = ((float) solved / (float) tried) * 100.0f;
            }
            problemsSolvedPercent.add(String.format(Locale.CHINA, "%.2f%%(%d/%d/%d)", percent, solved, tried, compactorCount));
        }
        problemsCount = problemsSolvedPercent.size();
        setupCompactorInfoDetail(compactorCount, listItemsReceived);
    }

    private void setupCompactorInfoDetail(int compactorCount, ArrayList<Map<String, Object>> listItemsReceived) {
        for (int i = 0; i < compactorCount; i++) {
            Map<String, Object> compactorInfoDetail = listItemsReceived.get(i);
            compactorInfoDetail.put("header", R.drawable.logo);
            compactorInfoDetail.put("rank", String.valueOf((int) compactorInfoDetail.get("rank")));

            for (int j = 0; j < problemsCount; ++j) {
                Map<String, Object> compactorProblemSolvedDetail = ((ArrayList<Map<String, Object>>) compactorInfoDetail.get("itemList")).get(j);
                compactorProblemSolvedDetail.put("problemOrder", String.valueOf((char) ('A' + j)));
                compactorProblemSolvedDetail.put("problemSolvedPercent", problemsSolvedPercent.get(j));
                if ((boolean) compactorProblemSolvedDetail.get("firstBlood")) {
                    compactorProblemSolvedDetail.put("solvedStatus", THEFIRSTSOLVED);
                } else if ((boolean) compactorProblemSolvedDetail.get("solved")) {
                    compactorProblemSolvedDetail.put("solvedStatus", SOLVED);
                } else if ((int) compactorProblemSolvedDetail.get("tried") > 0) {
                    compactorProblemSolvedDetail.put("solvedStatus", TRIED);
                } else {
                    compactorProblemSolvedDetail.put("solvedStatus", DIDNOTHING);
                }
                compactorProblemSolvedDetail.put("solvedTime", TimeFormat.getFormatTime((int) compactorProblemSolvedDetail.get("solvedTime")));
                compactorProblemSolvedDetail.put("tried", String.valueOf(compactorProblemSolvedDetail.get("tried")));
            }

            listItems.add(compactorInfoDetail);
            Global.userManager.getAvatar(String.valueOf(compactorInfoDetail.get("email")), listItems.size() - 1, this);
        }
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListView.setAdapter(setupAdapter());
        }else {
            mListAdapter.notifyDataSetChanged();
        }
        mListAdapter.notifyDataSetChanged();
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private ListAdapter setupAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"header", "rank", "nickName", "name", "itemList"},
                new int[]{R.id.contestRank_header, R.id.contestRank_rank, R.id.contestRank_nickName,
                        R.id.contestRank_account, R.id.contestRank_problemsStatus}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                v.setTag(position);
                return v;
            }
        };
        setupAdapterBinder();
        return mListAdapter;
    }

    private void setupAdapterBinder() {
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
                    setupProblemsStatusView(view, data);
                    return true;
                }
                return false;
            }
        });
    }

    public void setupProblemsStatusView(View view, Object data) {
        ArrayList<Map<String, Object>> problemsStatusList = (ArrayList<Map<String, Object>>) data;
        for (int i = 0; i < 9 && i < problemsCount; ++i) {
            Map<String, Object> problemStatus = problemsStatusList.get(i);
            FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
            markContainer.setVisibility(View.VISIBLE);
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            switch ((int) problemStatus.get("solvedStatus")) {
                case THEFIRSTSOLVED:
                    bg.setImageBitmap(Global.theFirstSolvedIcon);
                    break;
                case SOLVED:
                    bg.setImageBitmap(Global.solvedIcon);
                    break;
                case TRIED:
                    bg.setImageBitmap(Global.triedIcon);
                    break;
                default:
                    bg.setImageBitmap(Global.didNothingIcon);
            }
        }
        if (problemsCount >= 9) {
            ((LinearLayout) view).getChildAt(9).setVisibility(View.VISIBLE);
        }
    }

    private void refresh() {
        if (contestID != null) refresh(contestID);
    }

    public ContestRank refresh(Integer contestID) {
        if (contestID < 1) return this;
        this.contestID = contestID;
        refreshed = true;
        if (mListView != null) {
            NetData.getContestRank(contestID, this);
        }
        return this;
    }
}
