package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactor;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblem;

/**
 * Created by Great on 2016/10/19.
 */

public class RankAlert extends LinearLayout {

    private AlertDialog alertDialog;
    private RankCompactor compactor;
    private LinearLayout headerView;
    private ListView mListView;
    private RankAlertAdapter mListAdapter;
    private List<RankCompactorProblem> problemList;

    public RankAlert(Context context) {
        this(context, null, null);
    }

    public RankAlert(Context context, RankCompactor compactor, List<RankCompactorProblem> problemList) {
        super(context);
        this.compactor = compactor;
        this.problemList = problemList;
        initViews();
    }

    public void initViews() {
        setOrientation(VERTICAL);
        setupHeader();
        setupListView();
        addView(headerView);
        addView(mListView);
        setupAlertDialog();
    }

    private void setupListView() {
        mListView = new ListView(getContext());
        mListAdapter = new RankAlertAdapter(getContext(), problemList);
        mListView.setAdapter(mListAdapter);
    }


    private void setupHeader() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_list_item_alert_header, this, false);
        if (compactor != null) {
            updateHeaderData(compactor);
        }
    }

    private void setupAlertDialog() {
        alertDialog = new AlertDialog.Builder(getContext())
                .setView(this)
                .setNegativeButton(getContext().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    private void updateHeaderData(RankCompactor compactor) {
        ((ImageView) headerView.findViewById(R.id.contestRankItemHeader_header))
                .setImageDrawable(compactor.avatar);
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_nickName))
                .setText(compactor.getNickName());
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_rank))
                .setText(compactor.rankString);
        ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solvedNum))
                .setText(compactor.solvedString);
    }

    public void show() {
        if (problemList != null) {
            alertDialog.show();
        }
    }

    public void show(RankCompactor compactor, List<RankCompactorProblem> problemList) {
        updateHeaderData(compactor);
        mListAdapter.setProblemList(problemList);
        mListAdapter.notifyDataSetChanged();
        alertDialog.show();
    }
}
