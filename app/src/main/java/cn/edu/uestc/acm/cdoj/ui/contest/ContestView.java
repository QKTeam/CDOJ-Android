package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ContestData;
import cn.edu.uestc.acm.cdoj.net.data.ContestReceive;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by great on 2016/8/25.
 */
public class ContestView extends ViewPager implements ConvertNetData {

    private static final String TAG = "比赛";

    private SwipeRefreshLayout refreshLayout;
    private TabLayout mTabLayout;
    private DetailWebView overview;
    private ProblemViewPager problemViewPager;
    private ClarificationView clarificationView;
    private StatusView statusView;
    private RankView rankView;
    private View[] contentViews;

    private ContestData contestData;
    private ArrayList<ProblemData> problemDataList;
    private int[] problemIDs;
    private int contestId;
    private Context context;

    public ContestView(Context context) {
        this(context, -1, null);
    }

    public ContestView(Context context, AttributeSet attrs) {
        this(context, -1, attrs);
    }

    public ContestView(Context context, int contestId) {
        this(context, contestId, null);
    }

    public ContestView(Context context, int contestId, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.contestId = contestId;
        init();
    }

    private void init() {
        overview = new DetailWebView(context, NetData.CONTEST_DETAIL);
        setupRefreshLayout();
        problemViewPager = new ProblemViewPager(context);
        clarificationView = new ClarificationView(context, contestId);
        statusView = new StatusView(context, contestId, null);
        rankView = new RankView(context, contestId);
        contentViews = new View[]{refreshLayout, problemViewPager, clarificationView, statusView, rankView};

        setupViewPagerAdapter();
        setupTabLayout();
        addView(mTabLayout);
    }

    private void setupRefreshLayout() {
        refreshLayout = new SwipeRefreshLayout(context);
        refreshLayout.addView(overview);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void setupViewPagerAdapter() {
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(contentViews[position]);
                return contentViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(contentViews[position]);
            }
        });
    }

    private void setupTabLayout() {
        mTabLayout = new TabLayout(context);
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.isDecor = true;
        layoutParams.height = LayoutParams.WRAP_CONTENT;

        mTabLayout.setLayoutParams(layoutParams);
        mTabLayout.setupWithViewPager(this);
        for (int i = 0; i < 5; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                switch (i) {
                    case 0:
                        tab.setText(context.getString(R.string.overview));
                        break;
                    case 1:
                        tab.setText(context.getString(R.string.problems));
                        break;
                    case 2:
                        tab.setText(context.getString(R.string.clarification));
                        break;
                    case 3:
                        tab.setText(context.getString(R.string.status));
                        break;
                    case 4:
                        tab.setText(context.getString(R.string.rank));
                        break;
                }
            }
        }
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ContestReceive contestReceive = JSON.parseObject(jsonString, ContestReceive.class);
        if (!contestReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }
        contestData = contestReceive.getContest();
        contestData.jsonString = JSON.toJSONString(contestData);

        problemDataList = contestReceive.getProblemList();
        problemIDs = new int[problemDataList.size()];
        for (int i = 0; i < problemDataList.size(); ++i) {
            ProblemData problemData = problemDataList.get(i);
            problemData.jsonString = JSON.toJSONString(problemData);
            problemIDs[i] = problemData.getProblemId();
        }

        result.setStatus(Result.SUCCESS);
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getStatus()) {
            case Result.SUCCESS:
                overview.setJsonString(contestData.jsonString);
                problemViewPager.setProblemDataList(problemDataList);
                statusView.setContestProblemIds(problemIDs);
                break;
            case Result.DATAISNULL:
                break;
            case Result.FINISH:
                break;
            case Result.NETNOTCONECT:
                break;
            case Result.CONECTOVERTIME:
                break;
            case Result.FALSE:
                break;
            case Result.DEFAULT:
                break;
        }
        refreshLayout.setRefreshing(false);
    }

    public ContestView refresh() {
        return refresh(contestId);
    }

    public ContestView refresh(int contestId) {
        if (contestId > 0) {
            NetDataPlus.getContestDetail(context, contestId, this);
            clarificationView.refresh(contestId);
            statusView.setContestInfo(contestId, null);
            statusView.refresh();
            rankView.refresh(contestId);
        } else {
            refreshLayout.setRefreshing(false);
        }
        return this;
    }
}
