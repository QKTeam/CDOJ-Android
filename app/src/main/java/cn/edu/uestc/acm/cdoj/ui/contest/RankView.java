package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorData;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblemData;
import cn.edu.uestc.acm.cdoj.net.data.RankProblemData;
import cn.edu.uestc.acm.cdoj.net.data.RankReceive;
import cn.edu.uestc.acm.cdoj.net.data.RankTeamUserData;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/25.
 */
public class RankView extends ListViewWithGestureLoad implements ConvertNetData {
    public static final int DIDNOTHING = 0;
    public static final int TRIED = 1;
    public static final int SOLVED = 2;
    public static final int THEFIRSTSOLVED = 3;

    private static final String TAG = "排名";

    private List<RankCompactorData> compactorList;
    private List<String> solvedPercentList;
    private int compactorCount = 0;
    private RankAdapter mListAdapter;
    private RankAlert detailAlert;
    private Context context;
    private boolean isInvitedContest;
    private int contestId;

    public RankView(Context context) {
        this(context, -1, null);
    }

    public RankView(Context context, AttributeSet attrs) {
        this(context, -1, attrs);
    }

    public RankView(Context context, int contestId) {
        this(context, contestId, null);
    }

    public RankView(Context context, int contestId, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.contestId = contestId;
        init();
    }

    public void init() {
        compactorList = new ArrayList<>();
        solvedPercentList = new ArrayList<>();
        mListAdapter = new RankAdapter(context, compactorList);
        setListAdapter(mListAdapter);
        setPullUpLoadEnable(false);
    }

    @Override
    public void onListRefresh() {
        refresh();
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (detailAlert == null) {
            detailAlert = new RankAlert(context, isInvitedContest);
        }
        detailAlert.show(compactorList.get(position), compactorList.get(position).getItemList());
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        RankReceive rankReceived = JSON.parseObject(jsonString, RankReceive.class);
        if (!rankReceived.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }

        if (rankReceived.getRankList().getRankList().get(0).getTeamUsers() != null) {
            isInvitedContest = true;
            mListAdapter.setInvitedContest(true);
        }
        List<RankProblemData> problemList = rankReceived.getRankList().getProblemList();
        List<RankCompactorData> compactorListTem = rankReceived.getRankList().getRankList();
        compactorCount = compactorListTem.size();
        convertProblemsInfo(problemList);
        result.setContent(convertCompactorsInfo(compactorListTem));

        if (compactorCount == 0) {
            result.setStatus(Result.DATAISNULL);
        } else {
            result.setStatus(Result.FINISH);
        }
        return result;
    }

    private void convertProblemsInfo(List<RankProblemData> problemList) {
        for (RankProblemData rankProblemData : problemList) {
            double solved = (double) (int) rankProblemData.getSolved();
            double tried = (double) (int) rankProblemData.getTried();
            double percent = 0;
            if (tried != 0) {
                percent = (solved / tried) * 100.0;
            }
            solvedPercentList.add(String.format(Locale.CHINA,
                    "%.2f%%(%.0f/%.0f/%d)", percent, solved, tried, compactorCount));
        }
    }

    private List<RankCompactorData> convertCompactorsInfo(List<RankCompactorData> compactorListItem) {
        for (RankCompactorData compactor : compactorListItem) {
            if (isInvitedContest) {
                compactor.temUsersName = "队员:";
                for (RankTeamUserData teamUserData : compactor.getTeamUsers())
                    compactor.temUsersName = compactor.temUsersName + teamUserData.getName() + "; ";
            } else {
                compactor.avatar = Global.getDefaultLogo();
            }
            compactor.solvedString = "solved:  " + compactor.getSolved();
            compactor.triedString = "tried:  " + compactor.getTried();
            convertCompactorProblemsInfo((compactor.getItemList()));
        }
        return compactorListItem;
    }

    private void convertCompactorProblemsInfo(List<RankCompactorProblemData> problemList) {
        for (int j = 0; j < problemList.size(); ++j) {
            RankCompactorProblemData problem = problemList.get(j);
            problem.order = String.valueOf((char) ('A' + j));
            problem.solvedPercent = solvedPercentList.get(j);
            problem.solvedTimeString = TimeFormat.getFormatTime(problem.getSolvedTime());
            problem.triedString = String.valueOf(problem.getTried());

            if (problem.getFirstBlood()) {
                problem.solvedStatus = THEFIRSTSOLVED;
            } else if (problem.getSolved()) {
                problem.solvedStatus = SOLVED;
            } else if (problem.getTried() > 0) {
                problem.solvedStatus = TRIED;
            } else {
                problem.solvedStatus = DIDNOTHING;
            }
        }
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getDataType()) {
            case NetData.CONTEST_RANK:
                if (result.getContent() != null) {
                    compactorList.clear();
                    compactorList.addAll((List<RankCompactorData>) result.getContent());
                    mListAdapter.notifyDataSetChanged();
                    getAvatar();
                }
                noticeLoadOrRefreshComplete();
                switch (result.getStatus()) {
                    case Result.SUCCESS:
                        break;
                    case Result.DATAISNULL:
                        noticeDataIsNull();
                        break;
                    case Result.FINISH:
                        getAvatar();
                        noticeLoadFinish();
                        break;
                    case Result.NETNOTCONECT:
                        noticeNetNotConnect();
                        break;
                    case Result.CONECTOVERTIME:
                        noticeConnectOvertime();
                        break;
                    case Result.FALSE:
                        noticeLoadFailure();
                        break;
                    case Result.DEFAULT:
                        break;
                }
                break;

            case NetData.AVATAR:
                int position = (int) result.getExtra();
                if (position < compactorList.size()) {
                    compactorList.get(position).avatar = (BitmapDrawable) result.getContent();
                }
                View view = findViewWithTag(position);
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.contestRank_header))
                            .setImageDrawable((BitmapDrawable) result.getContent());
                }
        }
    }

    private void getAvatar() {
        for (int i = 0; i < compactorList.size(); ++i) {
            RankCompactorData compactor = compactorList.get(i);
            NetDataPlus.getAvatar(context, compactor.getEmail(), i, this);
        }
    }

    public void clear() {
        compactorList.clear();
        mListAdapter.notifyDataSetChanged();
    }

    public RankView refresh() {
        return refresh(contestId);
    }

    public RankView refresh(int contestId) {
        if (contestId > 0) {
            clear();
            NetDataPlus.getContestRank(context, contestId, this);
        } else {
            noticeRefreshComplete();
        }
        return this;
    }
}
