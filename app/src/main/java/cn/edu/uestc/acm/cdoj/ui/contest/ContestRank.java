package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
    private ArrayList<Map<String, Object>> rankListItems = new ArrayList<>();
    ArrayList<String> problemsSolvedPercent = new ArrayList<>();
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
        configOnListItemClick();
        if (refreshed) refresh();
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> RankListItem = rankListItems.get(position);
                ListView itemDetailListView = new ListView(context);
                itemDetailListView.addHeaderView(createItemListViewHeader(RankListItem));
                itemDetailListView.setAdapter(createItemAdapter((ArrayList<Map<String, Object>>) RankListItem.get("itemList")));
                showItemList(itemDetailListView);
            }
        });
    }

    private View createItemListViewHeader(Map<String, Object> RankListItem) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_list_item_detail_header, null);
        ImageView headerImageView = (ImageView) headerView.findViewById(R.id.contestRankItemHeader_header);
        if (RankListItem.get("header") instanceof Bitmap) {
            headerImageView.setImageBitmap((Bitmap) RankListItem.get("header"));
        } else {
            headerImageView.setImageResource((int) RankListItem.get("header"));
        }
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_nickName))
                .setText((String) RankListItem.get("nickName"));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_rank))
                .setText(String.valueOf(RankListItem.get("rank")));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solvedNum))
                .setText(String.valueOf(RankListItem.get("solved")));
        return headerView;
    }

    private ListAdapter createItemAdapter(final ArrayList<Map<String, Object>> problemsStatusList) {
        SimpleAdapter adapter = new SimpleAdapter(
                Global.currentMainUIActivity, problemsStatusList, R.layout.contest_rank_list_item_detail,
                new String[]{"solvedStatus", "problemOrder", "solvedTime", "tried", "problemSolvedPercent"},
                new int[]{R.id.contestRankItem_container, R.id.contestRankItem_ProblemOrder, R.id.contestRankItem_solvedTime,
                        R.id.contestRankItem_failureCount, R.id.contestRankItem_problemSolvedPercent});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof TextView) {
                    ((TextView) view).setText(String.valueOf(data));
                    return true;
                } else if (view instanceof LinearLayout) {
                    switch ((int) data) {
                        case THEFIRSTSOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_theFirstSolved));
                            break;
                        case SOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_solved));
                            break;
                        case TRIED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_tried));
                            break;
                        default:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_didNothing));
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
    public void show(int which, Result result, long time) {
        switch (which) {
            case ViewHandler.CONTEST_RANK:
                problemsSolvedPercent.clear();
                rankListItems.clear();
                notifyDataSetChanged();
                if (result.result) {
                    Map<String, Object> rankMap = (Map<String, Object>) ((Map<String, Object>) result.getContent()).get("rankList");
                    ArrayList<Map<String, Object>> rankListItemsReceive = (ArrayList<Map<String, Object>>) rankMap.get("rankList");
                    int compactorCount = rankListItemsReceive.size();
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
                    for (int i = 0; i < rankListItemsReceive.size(); i++) {
                        Map<String, Object> temRankMap = rankListItemsReceive.get(i);
                        temRankMap.put("header", R.drawable.logo);
                        temRankMap.put("rank", String.valueOf((int) temRankMap.get("rank")));
                        for (int j = 0; j < problemsCount; ++j) {
                            Map<String, Object> itemListMap = ((ArrayList<Map<String, Object>>) temRankMap.get("itemList")).get(j);
                            itemListMap.put("problemOrder", String.valueOf((char) ('A' + j)));
                            itemListMap.put("problemSolvedPercent", problemsSolvedPercent.get(j));
                            if ((boolean) itemListMap.get("firstBlood")) {
                                itemListMap.put("solvedStatus", THEFIRSTSOLVED);
                            } else if ((boolean) itemListMap.get("solved")) {
                                itemListMap.put("solvedStatus", SOLVED);
                            } else if ((int) itemListMap.get("tried") > 0) {
                                itemListMap.put("solvedStatus", TRIED);
                            }else {
                                itemListMap.put("solvedStatus", DIDNOTHING);
                            }
                            itemListMap.put("solvedTime", TimeFormat.getFormatTime((int) itemListMap.get("solvedTime")));
                            itemListMap.put("tried", String.valueOf(itemListMap.get("tried")));
                        }
                        rankListItems.add(temRankMap);
                        Global.userManager.getAvatar(String.valueOf(temRankMap.get("email")), rankListItems.size() - 1, this);
                    }
                    if (rankListItems.size() == 0) {
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
                if (position < rankListItems.size()){
                    rankListItems.get(position).put("header", result.getContent());
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

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListView.setAdapter(createRankListAdapter());
        }else {
            mListAdapter.notifyDataSetChanged();
        }
        mListAdapter.notifyDataSetChanged();
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private ListAdapter createRankListAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, rankListItems, R.layout.contest_rank_list_item,
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
        setupRankAdapterBinder();
        return mListAdapter;
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
        ArrayList<Map<String, Object>> problemsStatusList = (ArrayList<Map<String, Object>>) data;
        for (int i = 0; i < 9 && i < problemsCount; ++i) {
            Map<String, Object> problemStatus = problemsStatusList.get(i);
            FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
            markContainer.setVisibility(View.VISIBLE);
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            /*if ((boolean) problemStatus.get("firstBlood")) {
            } else if ((boolean) problemStatus.get("solved")) {
            } else if ((int) problemStatus.get("tried") > 0) {
            }else {
            }*/
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
