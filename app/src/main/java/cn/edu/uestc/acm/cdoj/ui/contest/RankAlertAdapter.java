package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblem;

/**
 * Created by Grea on 2016/10/27.
 */

public class RankAlertAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<RankCompactorProblem> problemList;
    private Context context;

    RankAlertAdapter(Context context, List<RankCompactorProblem> problemList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.problemList = problemList;
    }

    @Override
    public int getCount() {
        if (problemList == null) return 0;
        return problemList.size();
    }

    @Override
    public Object getItem(int position) {
        if (problemList == null) return null;
        return problemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (problemList == null) return null;
        RankCompactorProblem problem = problemList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.contest_rank_list_item_alert, parent, false);
        }
        ((TextView) v.findViewById(R.id.contestRankItem_ProblemOrder))
                .setText(problem.order);
        ((TextView) v.findViewById(R.id.contestRankItem_failureCount))
                .setText(problem.triedString);
        ((TextView) v.findViewById(R.id.contestRankItem_problemSolvedPercent))
                .setText(problem.solvedPercent);
        ((TextView) v.findViewById(R.id.contestRankItem_solvedTime))
                .setText(problem.solvedTimeString);

        switch (problem.solvedStatus) {
            case RankView.THEFIRSTSOLVED:
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.rank_theFirstSolved));
                break;
            case RankView.SOLVED:
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.rank_solved));
                break;
            case RankView.TRIED:
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.rank_tried));
                break;
            default:
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.rank_didNothing));
        }
        return v;
    }

    void setProblemList(List<RankCompactorProblem> problemList) {
        this.problemList = problemList;
    }
}
