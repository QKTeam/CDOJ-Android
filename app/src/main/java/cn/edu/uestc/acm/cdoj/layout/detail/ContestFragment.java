package cn.edu.uestc.acm.cdoj.layout.detail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Contest;
import cn.edu.uestc.acm.cdoj.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ContestFragment extends Fragment implements ViewHandler{

    public static final int OVERVIEW = 0;
    public static final int PROBLEMS = 1;
    public static final int CLARIFICATION = 2;
    public static final int STATUS = 3;
    public static final int RANK = 4;
    @IntDef({ContestFragment.OVERVIEW, ContestFragment.PROBLEMS, ContestFragment.CLARIFICATION,
            ContestFragment.STATUS, ContestFragment.RANK})
    @Retention(RetentionPolicy.SOURCE)
    @interface contestPart {}

    private View rootView;
    private View buttonContainer;
    private DetailWebViewFragment overview;
    private ContestProblems problems;
    private ContestClarification clarification;
    private ContestStatus status;
    private ContestRank rank;
    private int contestID = -1;//命名为ID会与Fragment的ID冲突
    private FragmentManager fragmentManager;
    private Button[] buttons = new Button[5];
    private int currentPage = ContestFragment.OVERVIEW;
    private boolean refreshed = false;
    private int[] problemIDs;

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
            buttonContainer =  rootView.findViewById(R.id.contest_button_container);
            buttonContainer.setVisibility(View.INVISIBLE);
            if (refreshed) refresh();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setTopButton();
        }
    }

    private void setTopButton() {
        buttons[0] = (Button) rootView.findViewById(R.id.contest_button0);
        changeButtonColor(ContestFragment.OVERVIEW);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(ContestFragment.OVERVIEW);
            }
        });
        buttons[1] = (Button) rootView.findViewById(R.id.contest_button1);
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(ContestFragment.PROBLEMS);
            }
        });
        buttons[2] = (Button) rootView.findViewById(R.id.contest_button2);
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(ContestFragment.CLARIFICATION);
            }
        });
        buttons[3] = (Button) rootView.findViewById(R.id.contest_button3);
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(ContestFragment.STATUS);
            }
        });
        buttons[4] = (Button) rootView.findViewById(R.id.contest_button4);
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPart(ContestFragment.RANK);
            }
        });
    }

    private void changeButtonColor(@contestPart int which) {
        int lastPage = currentPage;
        currentPage = which;
        if (buttons[lastPage] != null)
            buttons[lastPage].setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.main_blue));
        if (buttons[currentPage] != null)
            buttons[currentPage].setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.black));
    }



    public ContestFragment showPart(@contestPart int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (i) {
            case 0:
                changeButtonColor(ContestFragment.OVERVIEW);
                transaction.replace(R.id.contest_details, overview);
                break;
            case 1:
                changeButtonColor(ContestFragment.PROBLEMS);
                transaction.replace(R.id.contest_details, problems);
                break;
            case 2:
                changeButtonColor(ContestFragment.CLARIFICATION);
                transaction.replace(R.id.contest_details, clarification);
                break;
            case 3:
                changeButtonColor(ContestFragment.STATUS);
                transaction.replace(R.id.contest_details, status);
                break;
            case 4:
                changeButtonColor(ContestFragment.RANK);
                transaction.replace(R.id.contest_details, rank);
        }
        transaction.commit();
        return this;
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
        if (clarification != null) clarification.setContestID(contestID);
        if (status != null) status.setContestID(contestID);
        if (rank != null) rank.setContestID(contestID);
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
        problemIDs = new int[problemList.size()];
        for (int i = 0; i != problemList.size(); ++i) {
            Problem tem = problemList.get(i);
            problemIDs[i] = tem.problemId;
        }
        if (status != null) status.setProblemIDs(problemIDs);
    }

    public void refresh() {
        refreshed = true;
        if (rootView == null) return;
        if (contestID == -1) throw new IllegalStateException("contest's contestID is null");
        buttonContainer.setVisibility(View.VISIBLE);
        createFragmentParts();
        NetData.getContestDetail(contestID, this);
        showPart(ContestFragment.OVERVIEW);
    }

    private void createFragmentParts() {
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
    }
}
