package cn.edu.uestc.acm.cdoj.layout.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Rank;

import static cn.edu.uestc.acm.cdoj.Global.userManager;

/**
 * Created by great on 2016/8/25.
 */
public class ContestRank extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private int contestID;
    private ListView mListView;
    private int probCount;
    private boolean firstLoad = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mListView = getListView();
            if (firstLoad && contestID != -1) refresh();
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
            firstLoad = false;
        }
    }

    @Override
    public void addListItem(Map<String, Object> listItem) {
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
                Global.currentMainActivity, listItems, R.layout.contest_rank_list_item,
                new String[]{"header", "rank", "nickName", "account","solvedDetail"},
                new int[]{R.id.contestRank_header, R.id.contestRank_rank, R.id.contestRank_nickName,
                        R.id.contestRank_account,R.id.contestRank_solved});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
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
                    int solvedDetail = (Integer) data;
                    char text = 'A';
                    int i = 0;
                    for (; i < 9 && i < probCount; ++i) {
                        FrameLayout markContainer = (FrameLayout) ((LinearLayout) view).getChildAt(i);
                        ImageView bg = (ImageView) markContainer.getChildAt(0);
                        TextView tx = (TextView) markContainer.getChildAt(1);
                        tx.setText(String.valueOf(text));
                        if ((solvedDetail & 1) == 1) {
                            bg.setImageResource(R.drawable.contest_rank_mark_bg_green_half);
                        } else bg.setImageResource(R.drawable.contest_rank_mark_bg_gray);
                        ++text;
                        solvedDetail = solvedDetail >> 1;
                    }
                    if (i == 9) {
                        TextView tx = (TextView) ((LinearLayout) view).getChildAt(i);
                        tx.setText("......");
                    }
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);
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
                        int solved = 0;
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("header", R.drawable.logo);
                        listItem.put("rank", "Rank  "+tem.rank);
                        listItem.put("email", tem.email);
                        listItem.put("account", tem.name);
                        listItem.put("nickName", tem.nickName);
                        listItem.put("solvedNum", tem.solved);
                        ArrayList<Rank.Performance.ProblemStatus> problemStatusList = tem.getProblemStatusList();
                        probCount = problemStatusList.size();
                        Collections.reverse(problemStatusList);
                        for (Rank.Performance.ProblemStatus temP : problemStatusList) {
                            solved = solved << 1;
                            if (temP.solved) ++solved;
                        }
                        listItem.put("solvedDetail", solved);
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
                if (position >= mListView.getFirstVisiblePosition() &&
                        position <= mListView.getLastVisiblePosition()) {
                    ViewGroup viewGroup = (ViewGroup) mListView
                            .getChildAt(position - mListView.getFirstVisiblePosition());
                    ImageView headerImage = null;
                    if (viewGroup != null)
                        headerImage = (ImageView) viewGroup.findViewById(R.id.contestClarification_header);
                    if (headerImage != null) headerImage.setImageBitmap((Bitmap) dataReceive[1]);
                }
        }

    }

    public void refresh() {
        if (contestID == -1) throw new IllegalStateException("Rank's contestId is null");
        listItems.clear();
        NetData.getContestRank(contestID, this);
    }
}
