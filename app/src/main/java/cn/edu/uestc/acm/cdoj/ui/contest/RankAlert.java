package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorData;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblemData;
import cn.edu.uestc.acm.cdoj.net.data.RankTeamUserData;

/**
 * Created by Great on 2016/10/19.
 */

public class RankAlert extends LinearLayout {

    private AlertDialog alertDialog;
    private RankCompactorData compactor;
    private LinearLayout headerView;
    private ListView mListView;
    private RankAlertAdapter mListAdapter;
    private List<RankCompactorProblemData> problemList;
    private boolean isInvitedContest;
    private LinearLayout userContainer;

    public RankAlert(Context context) {
        this(context, null, null);
    }

    public RankAlert(Context context, boolean isInvitedContest) {
        this(context, null, null, isInvitedContest);
    }

    public RankAlert(Context context, RankCompactorData compactor, List<RankCompactorProblemData> problemList) {
        this(context, compactor, problemList, false);
    }

    public RankAlert(Context context, RankCompactorData compactor, List<RankCompactorProblemData> problemList, boolean isInvitedContest) {
        super(context);
        this.compactor = compactor;
        this.problemList = problemList;
        this.isInvitedContest = isInvitedContest;
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
        if (!isInvitedContest) {
            headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_detail_header, this, false);
        } else {
            headerView = (LinearLayout) inflater.inflate(R.layout.contest_rank_detail_header_team, this, false);
            userContainer = (LinearLayout) headerView.findViewById(R.id.contestRankItemHeader_team_userContainer);
        }
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

    private void updateHeaderData(RankCompactorData compactor) {
        if (!isInvitedContest) {
            ((ImageView) headerView.findViewById(R.id.contestRankItemHeader_avatar))
                    .setImageDrawable(compactor.avatar);
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_nickName))
                    .setText(compactor.getName());
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_rank))
                    .setText(compactor.rankString);
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_solved))
                    .setText(compactor.solvedString);
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_tried))
                    .setText(compactor.triedString);
        }else {
            userContainer.removeAllViews();
            for (RankTeamUserData teamUserData : compactor.getTeamUsers()) {
                TextView textView = new TextView(getContext());
                textView.setTextSize(14);
                textView.setText(String.format(Locale.CHINA, "队员:%s(%s)", teamUserData.getNickName(), teamUserData.getName()));
                userContainer.addView(textView);
            }
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_team_name))
                    .setText(compactor.getName());
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_team_rank))
                    .setText(compactor.rankString);
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_team_solved))
                    .setText(compactor.solvedString);
            ((TextView) headerView.findViewById(R.id.contestRankItemHeader_team_tried))
                    .setText(compactor.triedString);
        }

    }

    public void show() {
        if (problemList != null) {
            alertDialog.show();
        }
    }

    public void show(RankCompactorData compactor, List<RankCompactorProblemData> problemList) {
        updateHeaderData(compactor);
        mListAdapter.setProblemList(problemList);
        mListAdapter.notifyDataSetChanged();
        alertDialog.show();
    }
}
