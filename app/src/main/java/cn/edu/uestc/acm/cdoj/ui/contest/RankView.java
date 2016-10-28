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
import java.util.List;
import java.util.Locale;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactor;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblem;
import cn.edu.uestc.acm.cdoj.net.data.RankProblem;
import cn.edu.uestc.acm.cdoj.net.data.RankReceive;
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

    private List<RankCompactor> compactorList = new ArrayList<>();
    private List<String> solvedPercent = new ArrayList<>();
    private int compactorCount = 0;
    private BaseAdapter mListAdapter;
    private RankAlert detailAlert;
    private Context context;
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
        mListAdapter = new RankAdapter(context, compactorList);
        setListAdapter(mListAdapter);
        setPullUpLoadEnable(false);
        detailAlert = new RankAlert(context);
    }

    @Override
    public void onListRefresh() {
        refresh();
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        List<RankProblem> problemList = rankReceived.getRankList().getProblemList();
        List<RankCompactor> compactorListTem = rankReceived.getRankList().getRankList();
        compactorCount = compactorListTem.size();
        convertProblemsInfo(problemList);
        compactorList.addAll(convertCompactorsInfo(compactorListTem));

        if (compactorList.size() == 0) {
            result.setStatus(Result.DATAISNULL);
        } else {
            result.setStatus(Result.FINISH);
        }
        return result;
    }

    private void convertProblemsInfo(List<RankProblem> problemList) {
        for (RankProblem rankProblem : problemList) {
            double solved = (double) (int) rankProblem.getSolved();
            double tried = (double) (int) rankProblem.getTried();
            double percent = 0;
            if (tried != 0) {
                percent = (solved / tried) * 100.0;
            }
            solvedPercent.add(String.format(Locale.CHINA,
                    "%.2f%%(%.0f/%.0f/%d)", percent, solved, tried, compactorCount));
        }
    }

    private List<RankCompactor> convertCompactorsInfo(List<RankCompactor> compactorListItem) {
        for (RankCompactor compactor : compactorListItem) {
            compactor.avatar = Global.getDefaultLogo();
            convertCompactorProblemsInfo((compactor.getItemList()));
        }
        return compactorListItem;
    }

    private void convertCompactorProblemsInfo(List<RankCompactorProblem> problemList) {
        for (int j = 0; j < problemList.size(); ++j) {
            RankCompactorProblem problem = problemList.get(j);
            problem.order = String.valueOf((char) ('A' + j));
            problem.solvedPercent = solvedPercent.get(j);
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
                mListAdapter.notifyDataSetChanged();
                noticeLoadOrRefreshComplete();
                switch (result.getStatus()) {
                    case Result.SUCCESS:
                        getAvatar();
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
            RankCompactor compactor = compactorList.get(i);
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
        }
        return this;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }
}
