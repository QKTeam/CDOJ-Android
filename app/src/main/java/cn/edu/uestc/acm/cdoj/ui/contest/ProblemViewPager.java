package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemView;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemViewPager extends ViewPager{
    private TabLayout mTabLayout;
    private List<ProblemView> problemViewList;
    private PagerAdapter mAdapter;
    private List<ProblemData> problemDataList;
    private ViewPager contestPager;
    private int contestId = -1;

    public ProblemViewPager(Context context) {
        super(context);
        init();
    }

    public ProblemViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProblemViewPager(Context context, ArrayList<ProblemData> problemDataList) {
        super(context);
        this.problemDataList = problemDataList;
        init();
    }

    public void init() {
        problemViewList = new ArrayList<>();
        mTabLayout = new TabLayout(getContext());
        setProblemDataList(problemDataList);
        setupAdapter();
        setAdapter(mAdapter);
        setupTabLayout();
        addView(mTabLayout);
        setOffscreenPageLimit(3);
    }

    private void setupTabLayout() {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.isDecor = true;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        mTabLayout.setLayoutParams(layoutParams);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(this, true);
    }

    public void setProblemDataList(List<ProblemData> problemDataList) {
        if (problemDataList == null) return;
        for (ProblemData problemData : problemDataList) {
            ProblemView problemView = new ProblemView(getContext());
            problemView.setProblemData(problemData);
            problemView.setTitleVisibility(View.GONE);
            problemView.setContestId(contestId);
            problemView.setContestPager(contestPager);
            problemViewList.add(problemView);
        }
        mAdapter.notifyDataSetChanged();
        setupTab();
    }

    private void setupTab() {
        for (int i = 0; i < mTabLayout.getTabCount(); ++i) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(String.valueOf((char) ('A' + i)));
            }
        }
    }

    private void setupAdapter() {
        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return problemViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(problemViewList.get(position));
                return problemViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(problemViewList.get(position));
            }
        };
    }

    public void setContestPager(ViewPager contestPager) {
        this.contestPager = contestPager;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
        for (ProblemView problemView : problemViewList) {
            problemView.setContestId(contestId);
        }
    }
}
