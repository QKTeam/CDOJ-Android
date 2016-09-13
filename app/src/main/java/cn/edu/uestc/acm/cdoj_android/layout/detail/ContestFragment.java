package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.NetContent;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.Status;

/**
 * Created by great on 2016/8/25.
 */
public class ContestFragment extends Fragment implements ViewHandler{

    private View rootView;
    private DetailWebViewFragment overview;
    private ContestProblems problems;
    private ContestClarification clarification;
    private ContestStatus status;
    private ContestRank rank;
    private int contestID = -1;//命名为ID会与Fragment的ID冲突
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager = getFragmentManager();
            rootView =  inflater.inflate(R.layout.contest, container, false);
            overview = new DetailWebViewFragment().switchHTMLData(ViewHandler.CONTEST_DETAIL);
            problems = new ContestProblems();
            clarification = new ContestClarification();
            status  = new ContestStatus();
            rank = new ContestRank();
            addPartFragment();
            show(0);
            if (contestID != -1) {
                clarification.setContestID(contestID);
                status.setContestID(contestID);
                rank.setContestID(contestID);
            }
        }
        return rootView;
    }

    private void addPartFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contest_details, overview);
        transaction.add(R.id.contest_details, problems);
        transaction.add(R.id.contest_details, clarification);
        transaction.add(R.id.contest_details, status);
        transaction.add(R.id.contest_details, rank);
        transaction.commit();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            Button[] buttons = new Button[5];
            buttons[0] = (Button) rootView.findViewById(R.id.contest_button0);
            buttons[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(0);
                }
            });
            buttons[1] = (Button) rootView.findViewById(R.id.contest_button1);
            buttons[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(1);
                }
            });
            buttons[2] = (Button) rootView.findViewById(R.id.contest_button2);
            buttons[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(2);
                }
            });
            buttons[3] = (Button) rootView.findViewById(R.id.contest_button3);
            buttons[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(3);
                }
            });
            buttons[4] = (Button) rootView.findViewById(R.id.contest_button4);
            buttons[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(4);
                }
            });
        }
        /*if (savedInstanceState == null) {
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
        }*/
    }

    private void show(int i) {
        hideAll();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (i) {
            case 0:
                transaction.show(overview);
                break;
            case 1:
                transaction.show(problems);
                break;
            case 2:
                transaction.show(clarification);
                break;
            case 3:
                transaction.show(status);
                break;
            case 4:
                transaction.show(rank);
                break;
        }
        transaction.commit();
    }

    private void hideAll() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(overview);
        transaction.hide(problems);
        transaction.hide(clarification);
        transaction.hide(status);
        transaction.hide(rank);
        transaction.commit();
    }

    public void addProblems(ArrayList<Problem> problemList) {
        problems.addProblems(problemList);
    }

    public void addOverView(String jsData) {
        overview.addJSData(jsData);
    }

    public int getContestID() {
        return contestID;
    }

    public void setContestID(int contestID) {
        this.contestID = contestID;
        if (clarification != null && status != null && rank != null) {
            clarification.setContestID(contestID);
            status.setContestID(contestID);
            rank.setContestID(contestID);
        }
    }

    public ContestClarification getClarification() {
        return clarification;
    }

    public ContestStatus getStatus() {
        return status;
    }

    public ContestRank getRank() {
        return rank;
    }

    @Override
    public void show(int which, Object data, long time) {
        Contest contest_data = (Contest) data;
        addOverView(contest_data.getContentString());
        addProblems(contest_data.getProblemList());
    }

    public void refresh() {
        NetData.getContestDetail(contestID, this);
        NetData.getContestComment(contestID, 1, clarification);
        NetData.getContestRank(contestID, rank);
    }
}
