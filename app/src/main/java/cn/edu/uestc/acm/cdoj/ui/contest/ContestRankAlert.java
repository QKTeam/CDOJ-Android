package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Great on 2016/10/19.
 */

public class ContestRankAlert extends LinearLayout {

    public ContestRankAlert(Context context) {
        super(context);
    }

    public ContestRankAlert(Context context, Map<String, Object> rankListItem, List<Map<String, Object>> problemsStatusList) {
        super(context);
        initViews(rankListItem, problemsStatusList);
    }

    public void initViews(Map<String, Object> rankListItem, List<Map<String, Object>> problemsStatusList) {
        setOrientation(VERTICAL);
        addView(createItemListViewHeader(rankListItem));
        ListView listView = new ListView(getContext());
        listView.setAdapter(createItemAdapter(problemsStatusList));
        addView(listView);
    }


    private View createItemListViewHeader(Map<String, Object> rankListItem) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_list_item_detail_header, null);
        ImageView headerImageView = (ImageView) headerView.findViewById(R.id.contestRankItemHeader_header);
        if (rankListItem.get("header") instanceof Bitmap) {
            headerImageView.setImageBitmap((Bitmap) rankListItem.get("header"));
        } else {
            headerImageView.setImageResource((int) rankListItem.get("header"));
        }
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_nickName))
                .setText((String) rankListItem.get("nickName"));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_rank))
                .setText(String.valueOf(rankListItem.get("rank")));
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solvedNum))
                .setText(String.valueOf(rankListItem.get("solved")));
        return headerView;
    }

    private ListAdapter createItemAdapter(final List<Map<String, Object>> problemsStatusList) {
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
                        case ContestRank.THEFIRSTSOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_theFirstSolved));
                            break;
                        case ContestRank.SOLVED:
                            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.rank_solved));
                            break;
                        case ContestRank.TRIED:
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

    public void show() {
        new AlertDialog.Builder(getContext())
                .setView(this)
                .setNegativeButton(getContext().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
