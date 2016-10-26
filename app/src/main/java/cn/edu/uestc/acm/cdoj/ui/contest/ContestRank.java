package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Locale;
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

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends Fragment implements ConvertNetData {
    public static final int DIDNOTHING = 0;
    public static final int TRIED = 1;
    public static final int SOLVED = 2;
    public static final int THEFIRSTSOLVED = 3;

    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ArrayList<String> problemsSolvedPercent = new ArrayList<>();
    private int problemsCount = 0;
    private ListViewWithGestureLoad mListView;
    private SimpleAdapter mListAdapter;
    private Context context;
    private Integer contestID;

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
        if (mListAdapter != null) mListView.setAdapter(mListAdapter);
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

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        problemsSolvedPercent.clear();
        clearItems();
        Map<String, Object> rankReceived = JSON.parseObject(jsonString);
        Map<String, Object> rankMap = (Map<String, Object>) rankReceived.get("rankList");
        convertNetData(rankMap);

        if (listItems.size() == 0) {
            result.setStatus(NetHandler.Status.DATAISNULL);
        } else if (! rankReceived.get("result").equals("success")) {
            result.setStatus(NetHandler.Status.FALSE);
        } else {
            result.setStatus(NetHandler.Status.FINISH);
        }
        return result;
    }

    private void convertNetData(Map<String, Object> rankMap) {
        convertCompactorsInfo((ArrayList<Map<String, Object>>) rankMap.get("rankList"));
        convertProblemsInfo((ArrayList<Map<String, Object>>) rankMap.get("problemList"));
    }

    private void convertCompactorsInfo(ArrayList<Map<String, Object>> compactorList) {
        for (Map<String, Object> compactor : compactorList) {
            compactor.put("header", R.drawable.logo);
            compactor.put("rank", String.valueOf((int) compactor.get("rank")));
            convertCompactorProblemsInfo((ArrayList<Map<String, Object>>) compactor.get("itemList"));

            listItems.add(compactor);
            NetDataPlus.getAvatar(context, String.valueOf(compactor.get("email")), listItems.size() - 1, this);
        }
    }

    private void convertCompactorProblemsInfo(ArrayList<Map<String, Object>> problemList) {
        for (int j = 0; j < problemList.size(); ++j) {
            Map<String, Object> problem = problemList.get(j);
            problem.put("problemOrder", String.valueOf((char) ('A' + j)));
            problem.put("problemSolvedPercent", problemsSolvedPercent.get(j));
            problem.put("solvedTime", TimeFormat.getFormatTime((int) problem.get("solvedTime")));
            problem.put("tried", String.valueOf(problem.get("tried")));

            if ((boolean) problem.get("firstBlood")) {
                problem.put("solvedStatus", THEFIRSTSOLVED);
            } else if ((boolean) problem.get("solved")) {
                problem.put("solvedStatus", SOLVED);
            } else if ((int) problem.get("tried") > 0) {
                problem.put("solvedStatus", TRIED);
            } else {
                problem.put("solvedStatus", DIDNOTHING);
            }
        }
    }

    private void convertProblemsInfo(ArrayList<Map<String, Object>> problemList) {
        int compactorCount = listItems.size();
        problemsCount = problemList.size();

        for (Map<String, Object> temMap : problemList) {
            double solved = (double) temMap.get("solved");
            double tried = (double) temMap.get("tried");
            double percent = 0;
            if (tried != 0) {
                percent = (solved / tried) * 100.0;
            }
            problemsSolvedPercent.add(String.format(Locale.CHINA, "%.2f%%(%.0f/%.0f/%d)", percent, solved, tried, compactorCount));
        }
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getType()) {
            case NetData.CONTEST_RANK:
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
                if (position < listItems.size()) {
                    listItems.get(position).put("header", result.getContent());
                }
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.contestRank_header))
                            .setImageDrawable((BitmapDrawable) result.getContent());
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
            if (mListView.isRefreshing()) {
                mListView.setRefreshing(false);
            }
        }
    }

    private SimpleAdapter setupAdapter() {
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

    private void setupProblemsStatusView(View view, Object data) {
        ArrayList<Map<String, Object>> problemsStatusList = (ArrayList<Map<String, Object>>) data;
        for (int i = 0; i < problemsCount && i < 9; ++i) {
            Map<String, Object> problemStatus = problemsStatusList.get(i);
            FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
            markContainer.setVisibility(View.VISIBLE);
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            switch ((int) problemStatus.get("solvedStatus")) {
                case THEFIRSTSOLVED:
                    bg.setImageBitmap(Global.rankIcon_theFirstSolved);
                    break;
                case SOLVED:
                    bg.setImageBitmap(Global.rankIcon_solved);
                    break;
                case TRIED:
                    bg.setImageBitmap(Global.rankIcon_tried);
                    break;
                default:
                    bg.setImageBitmap(Global.rankIcon_didNothing);
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
        NetDataPlus.getContestRank(context, contestID, this);
        return this;
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }
}
