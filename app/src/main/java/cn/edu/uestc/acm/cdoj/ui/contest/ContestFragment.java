package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebViewFragment;
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
        setRetainInstance(true);
        fragmentManager = getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.contest, container, false);
        if (refreshed) refresh();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configButtons();
    }

    private void configButtons() {
        buttons[0] = (Button) rootView.findViewById(R.id.contest_button0);
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
        setButtonSelect(ContestFragment.OVERVIEW);
    }

    private void setButtonSelect(@contestPart int which) {
        int lastPage = currentPage;
        currentPage = which;
        if (buttons[lastPage] != null)
            buttons[lastPage].setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.main_blue));
        if (buttons[currentPage] != null)
            buttons[currentPage].setTextColor(ContextCompat.getColor(rootView.getContext(), R.color.black));
    }

    public ContestFragment showPart(@contestPart int part) {
        hideAllPart();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (part) {
            case 0:
                setButtonSelect(ContestFragment.OVERVIEW);
                if (overview != null) {
                    transaction.show(overview);
                }
                break;
            case 1:
                setButtonSelect(ContestFragment.PROBLEMS);
                if (problems != null){
                    transaction.show(problems);
                }
                break;
            case 2:
                setButtonSelect(ContestFragment.CLARIFICATION);
                if (clarification != null) {
                    transaction.show(clarification);
                }
                break;
            case 3:
                setButtonSelect(ContestFragment.STATUS);
                if (status != null){
                    transaction.show(status);
                }
                break;
            case 4:
                setButtonSelect(ContestFragment.RANK);
                if (rank != null) {
                    transaction.show(rank);
                }
        }
        transaction.commit();
        return this;
    }

    private void hideAllPart() {
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
        if (clarification != null) clarification.refresh(contestID);
        if (status != null) status.refresh(contestID);
        if (rank != null) rank.refresh(contestID);
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
    public void show(int which, Result result, long time) {
        Contest contest_data = (Contest) result.getContent();
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

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public ContestFragment refresh(int contestID) {
        if (contestID < 1) return this;
        this.contestID = contestID;
        refreshed = true;
        if (rootView != null) {
            createFragmentParts();
            showPart(ContestFragment.OVERVIEW);
            NetData.getContestDetail(contestID, this);
            rootView.findViewById(R.id.contest_button_container).setVisibility(View.VISIBLE);
        }
        return this;
    }

    private void createFragmentParts() {
        overview = new DetailWebViewFragment().switchHTMLData(ViewHandler.CONTEST_DETAIL);
        problems = new ContestProblems();
        clarification = new ContestClarification().refresh(contestID);
        status  = new ContestStatus().refresh(contestID);
        rank = new ContestRank().refresh(contestID);
        addFragmentParts();
    }

    private void addFragmentParts() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contest_details_container, overview);
        transaction.add(R.id.contest_details_container, problems);
        transaction.add(R.id.contest_details_container, clarification);
        transaction.add(R.id.contest_details_container, status);
        transaction.add(R.id.contest_details_container, rank);
        transaction.commit();
    }
}
