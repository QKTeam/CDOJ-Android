package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactor;
import cn.edu.uestc.acm.cdoj.net.data.RankCompactorProblem;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/27.
 */

public class RankAdapter extends BaseAdapter {

    private static final String TAG = "Rank适配器";
    private LayoutInflater mInflater;
    private List<RankCompactor> compactorList;

    RankAdapter(Context context, List<RankCompactor> compactorList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.compactorList = compactorList;
    }

    @Override
    public int getCount() {
        return compactorList.size();
    }

    @Override
    public Object getItem(int position) {
        return compactorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RankCompactor compactor = compactorList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.contest_rank_list_item, parent, false);
            LinearLayout problemsLayout = (LinearLayout) v.findViewById(R.id.contestRank_problemsStatus);
            for (int i = 0; i < 9 && i < compactor.getItemList().size(); i++) {
                problemsLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }
            if (compactor.getItemList().size() > 9) {
                problemsLayout.getChildAt(9).setVisibility(View.VISIBLE);
            }
        }

        ((ImageView) v.findViewById(R.id.contestRank_header))
                .setImageDrawable(compactor.avatar);
        ((TextView) v.findViewById(R.id.contestRank_nickName))
                .setText(compactor.getNickName());
        ((TextView) v.findViewById(R.id.contestRank_account))
                .setText(compactor.getName());
        ((TextView) v.findViewById(R.id.contestRank_rank))
                .setText(compactor.rankString);

        LinearLayout problemsLayout = (LinearLayout) v.findViewById(R.id.contestRank_problemsStatus);
        List<RankCompactorProblem> problemList = compactor.getItemList();
        for (int i = 0; i < problemList.size() && i < 9; ++i) {
            RankCompactorProblem problem = problemList.get(i);
            TextView orderText = (TextView) problemsLayout.getChildAt(i);
            switch (problem.solvedStatus) {
                case RankView.THEFIRSTSOLVED:
                    orderText.setBackground(Global.getRankIcon_theFirstSolved());
                    break;
                case RankView.SOLVED:
                    orderText.setBackground(Global.getRankIcon_solved());
                    break;
                case RankView.TRIED:
                    orderText.setBackground(Global.getRankIcon_tried());
                    break;
                default:
                    orderText.setBackground(Global.getRankIcon_didNothing());
            }
        }
        v.setTag(position);
        return v;
    }
}
