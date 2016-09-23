package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;

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
    private boolean askRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager = getChildFragmentManager();
            rootView =  inflater.inflate(R.layout.contest, container, false);
            overview = new DetailWebViewFragment().switchHTMLData(ViewHandler.CONTEST_DETAIL);
            problems = new ContestProblems();
            clarification = new ContestClarification();
            status  = new ContestStatus();
            rank = new ContestRank();
            if (contestID != -1) {
                clarification.setContestID(contestID);
                status.setContestID(contestID);
                rank.setContestID(contestID);
            }
            addPartFragment();
            showPart(0);
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
        /*if (askRefresh) {
            clarification.refresh();
            status.refresh();
            rank.refresh();
        }*/
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setTopButton();
        }
    }

    private void setTopButton() {
        Button[] buttons = new Button[5];
        buttons[0] = (Button) rootView.findViewById(R.id.contest_button0);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(0);
            }
        });
        buttons[1] = (Button) rootView.findViewById(R.id.contest_button1);
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(1);
            }
        });
        buttons[2] = (Button) rootView.findViewById(R.id.contest_button2);
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(2);
            }
        });
        buttons[3] = (Button) rootView.findViewById(R.id.contest_button3);
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(3);
            }
        });
        buttons[4] = (Button) rootView.findViewById(R.id.contest_button4);
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(4);
            }
        });
    }

    private void showPart(int i) {
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

    public ContestFragment setContestID(int contestID) {
        this.contestID = contestID;
        if (clarification != null && status != null && rank != null) {
            clarification.setContestID(contestID);
            status.setContestID(contestID);
            rank.setContestID(contestID);
        }
        return this;
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
        ArrayList<Problem> problemList = contest_data.getProblemList();
        addProblems(problemList);
        /*int[] problemsIDs = new int[problemList.size()];
        for (int i = 0; i != problemList.size(); ++i) {
            problemsIDs[i] = problemList.get(i).get
        }*/
    }

    public void refresh() {
        if (contestID == -1) throw new IllegalStateException("contest's contestID is null");
        askRefresh = true;
        NetData.getContestDetail(contestID, this);
        if (clarification != null) clarification.refresh();
        if (rank != null) rank.refresh();
        if (status != null) status.refresh();
        if (clarification != null && rank != null && status != null) askRefresh = false;
    }
}
