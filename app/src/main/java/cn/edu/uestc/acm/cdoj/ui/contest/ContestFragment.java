package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.Contest;
import cn.edu.uestc.acm.cdoj.net.data.ContestReceived;
import cn.edu.uestc.acm.cdoj.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ContestFragment extends Fragment implements ConvertNetData {

    public static final int OVERVIEW = 0;
    public static final int PROBLEMS = 1;
    public static final int CLARIFICATION = 2;
    public static final int STATUS = 3;
    public static final int RANK = 4;


    @IntDef({ContestFragment.OVERVIEW, ContestFragment.PROBLEMS, ContestFragment.CLARIFICATION,
            ContestFragment.STATUS, ContestFragment.RANK})
    @Retention(RetentionPolicy.SOURCE)
    @interface contestPart {
    }

    private View rootView;
    private ArrayList<Fragment> moduleList;
    private ContestOverview overviewFragment;
    private ContestProblems problemsFragment;
    private ContestClarification clarificationFragment;
    private ContestStatus statusFragment;
    private ContestRank rankFragment;

    private Button[] buttons;
    private Contest contestOverview;
    private ArrayList<Problem> problemList;
    private int[] problemIDs;
    private FragmentManager fragmentManager;
    private int currentPage;
    private int lastPage;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = ContestFragment.OVERVIEW;
        lastPage = ContestFragment.OVERVIEW;
        fragmentManager = getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contest, container, false);
        setupModules();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtons();
        if (problemIDs != null) addDataToModules();
    }

    private void setupButtons() {
        buttons = new Button[5];
        buttons[0] = (Button) rootView.findViewById(R.id.contest_button_overview);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(OVERVIEW);
            }
        });
        buttons[1] = (Button) rootView.findViewById(R.id.contest_button_problem);
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(PROBLEMS);
            }
        });
        buttons[2] = (Button) rootView.findViewById(R.id.contest_button_clarification);
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(CLARIFICATION);
            }
        });
        buttons[3] = (Button) rootView.findViewById(R.id.contest_button_status);
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(STATUS);
            }
        });
        buttons[4] = (Button) rootView.findViewById(R.id.contest_button_rank);
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(RANK);
            }
        });
    }

    private void synButtonsColor() {
        buttons[lastPage].setTextColor(0x38BDFF);
        buttons[currentPage].setTextColor(0x000000);
    }

    private ContestFragment showPage(@contestPart int page) {
        lastPage = currentPage;
        currentPage = page;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(moduleList.get(lastPage));
        transaction.show(moduleList.get(currentPage));
        transaction.commit();
        synButtonsColor();
        return this;
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ContestReceived contestReceived = JSON.parseObject(jsonString, ContestReceived.class);
        contestOverview = contestReceived.getContest();
        contestOverview.addJsonString(JSON.toJSONString(contestOverview));

        problemList = contestReceived.getProblemList();
        problemIDs = new int[problemList.size()];
        for (int i = 0; i != problemList.size(); ++i) {
            Problem problem = problemList.get(i);
            problem.addJsonString(JSON.toJSONString(problem));
            problemIDs[i] = problem.getProblemId();
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getType()) {
            case NetHandler.Status.SUCCESS:
                if (rankFragment != null) addDataToModules();
                break;
            case NetHandler.Status.DATAISNULL:
                break;
            case NetHandler.Status.FINISH:
                break;
            case NetHandler.Status.NETNOTCONECT:
                break;
            case NetHandler.Status.CONECTOVERTIME:
                break;
            case NetHandler.Status.FALSE:
                break;
            case NetHandler.Status.DEFAULT:
                break;
        }
    }

    public ContestFragment refresh(int contestID) {
        if (contestID < 1 || context == null) return this;
        return refresh(context, contestID);
    }

    public ContestFragment refresh(Context context, int contestID) {
        if (contestID < 1 || context == null) return this;
        this.context = context;
        if (rootView != null) refreshModules(contestID);
        NetDataPlus.getContestDetail(context, contestID, this);
        return this;
    }

    private void setupModules() {
        overviewFragment = new ContestOverview();
        problemsFragment = new ContestProblems();
        clarificationFragment = new ContestClarification();
        statusFragment = new ContestStatus();
        rankFragment = new ContestRank();

        moduleList = new ArrayList<>(5);
        moduleList.add(overviewFragment);
        moduleList.add(problemsFragment);
        moduleList.add(clarificationFragment);
        moduleList.add(statusFragment);
        moduleList.add(rankFragment);

        addFragments();
    }

    private void addFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment page : moduleList) {
            transaction.add(R.id.contest_details_container, page);
            transaction.hide(page);
        }
        transaction.commit();
    }

    private void refreshModules(int contestId) {
        clarificationFragment.refresh(contestId);
        statusFragment.refresh(contestId);
        rankFragment.refresh(contestId);
    }
    private void addDataToModules() {
        overviewFragment.addJSData(contestOverview.obtainJsonString());
        problemsFragment.addProblems(problemList);
        statusFragment.setProblemIDs(problemIDs);
    }
}
