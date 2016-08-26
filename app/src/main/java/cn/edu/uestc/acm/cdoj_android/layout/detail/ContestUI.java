package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ContestUI extends Fragment {

    private View rootView;
    private DetailWebViewFragment overview;
    private ContestProblems problems;
    private ContestClarification clarification;
    private ContestStatus status;
    private ContestRank rank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView =  inflater.inflate(R.layout.contest, container, false);
            overview = new DetailWebViewFragment().switchHTMLData(ViewHandler.CONTEST_DETAIL);
            problems = new ContestProblems();
            clarification = new ContestClarification();
            status  = new ContestStatus();
            rank = new ContestRank();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.contestViewPager);
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        case 0:
                            return overview;
                        case 1:
                            return problems;
                        case 2:
                            return clarification;
                        case 3:
                            return status;
                        case 4:
                            return rank;
                        default:
                            return null;
                    }
                }
                @Override
                public int getCount() {
                    return 5;
                }
            });
        }
    }

    public void addProblems(ArrayList<Problem> problemList) {
        problems.addProblems(problemList);
    }

    public void addOverView(String jsData) {
        overview.addJSData(jsData);
    }
}
