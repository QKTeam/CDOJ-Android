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
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
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
    private ArrayList<Map<String, Object>> problemsInfo = new ArrayList<>();
    private ListViewWithGestureLoad mListView;
    private Context context;
    private int contestID;
    private int problemsCount;
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
                Map<String, Object> listItem = rankListItems.get(position);
                ListView itemDetailListView = new ListView(context);
                ArrayList<Map<String, Object>> itemProblemsStatusList = new ArrayList<>();
                itemProblemsStatusList.addAll((ArrayList<Map<String, Object>>) rankListItems.get(position).get("itemList"));
                itemDetailListView.addHeaderView(createItemListViewHeader(listItem));
                itemDetailListView.setAdapter(createItemAdapter(itemProblemsStatusList));
                showItemList(itemDetailListView);
            }
        });
    }

    private View createItemListViewHeader(Map<String, Object> listItem) {
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
                .setText(String.valueOf(listItem.get("rank")));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solvedNum))
                .setText(String.valueOf(listItem.get("solvedNum")));
        return headerView;
    }

    private ListAdapter createItemAdapter(final ArrayList<Map<String, Object>> problemsStatusMapList) {
        SimpleAdapter adapter = new SimpleAdapter(
                Global.currentMainUIActivity, problemsStatusMapList, R.layout.contest_rank_list_item_detail,
                new String[]{"solvedStatus", "problemOrder", "solvedTime", "failureCount", "problemSolvedPercent"},
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
    public void show(int which, Result result, long time) {
        switch (which) {
            case ViewHandler.CONTEST_RANK:
                if (refreshed) {
                    rankListItems.clear();
                    notifyDataSetChanged();
                    refreshed = false;
                }
                if (result.result) {
                    Map<String, Object> rankMap = (Map<String, Object>) result.getContent();
                    rankListItems.addAll((ArrayList<Map<String, Object>>) rankMap.get("rankList"));
                    problemsInfo.addAll((ArrayList<Map<String, Object>>) rankMap.get("problemList"));
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
                if (position < rankListItems.size())
                    rankListItems.get(position).put("header", result.getContent());
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
                new String[]{"header", "rank", "nickName", "account", "problemsStatus"},
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
            ImageView bg = (ImageView) markContainer.getChildAt(0);
            ((TextView) markContainer.getChildAt(1)).setText((String) problemStatus.get("problemOrder"));
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
            ((TextView) ((LinearLayout) view).getChildAt(9)).setText("......");
        } else {
            for (int i = problemsCount; i < 9; i++) {
                ((LinearLayout) view).getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public ContestRank refresh(int contestID) {
        if (contestID < 1) return this;
        this.contestID = contestID;
        refreshed = true;
        if (mListView != null) {
            NetData.getContestRank(contestID, this);
        }
        return this;
    }
}
