package cn.edu.uestc.acm.cdoj.ui.problem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;

/**
 * Created by Grea on 2016/10/27.
 */

public class ProblemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ProblemData> problemDataList;

    ProblemAdapter(Context context, List<ProblemData> problemDataList) {
        this.problemDataList = problemDataList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return problemDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return problemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProblemData problemData = problemDataList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.problem_list_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.problem_list_item_solved))
                .setText(problemData.solvedString);
        ((TextView) v.findViewById(R.id.problem_list_item_id))
                .setText(problemData.problemIdString);
        ((TextView) v.findViewById(R.id.problem_list_item_tried))
                .setText(problemData.triedString);
        ((TextView) v.findViewById(R.id.problem_list_item_source))
                .setText(problemData.getSource());
        ((TextView) v.findViewById(R.id.problem_list_item_title))
                .setText(problemData.getTitle());
        return v;
    }
}
