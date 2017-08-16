package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.GeneralStatusListItem;
import cn.edu.uestc.acm.cdoj.net.contest.Contest;
import cn.edu.uestc.acm.cdoj.net.contest.status.ContestStatusListItem;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemStatusListItem;
import cn.edu.uestc.acm.cdoj.ui.custom_view.ProblemSubmitResultTextView;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemStatusListData;
import cn.edu.uestc.acm.cdoj.ui.data.contestData.StatusListData;
import cn.edu.uestc.acm.cdoj.utils.TimeFormat;

/**
 * Created by 14779 on 2017-8-4.
 */

public class StatusListAdapter<T> extends RecyclerView.Adapter<StatusListAdapter.StatusListViewHolder>{
    private static final String TAG = "StatusListAdapter";
    private Context context;
    private List<T> statusListItems;


    public StatusListAdapter(Context context, List<T> statusListItems){
        this.context = context;
        this.statusListItems = statusListItems;
    }

    @Override
    public StatusListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatusListViewHolder(LayoutInflater.from(context).inflate(R.layout.status_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(StatusListViewHolder holder, int position) {
        GeneralStatusListItem positionItem = (GeneralStatusListItem) statusListItems.get(position);
        holder.returnType.setText(positionItem.getReturnType());
        holder.language_length.setText(positionItem.getLanguage()+"/"+positionItem.getLength());
        holder.userName.setText(positionItem.getUserName());
        if (positionItem.getReturnType().equals("Accepted")){
            holder.memory_time_cost.setText(positionItem.getTimeCost()+"ms"+"/"+positionItem.getMemoryCost()+"KB");
        }
        holder.problemName.setText("");
        holder.submitTime.setText(TimeFormat.changeDataFormat(positionItem.getTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return statusListItems.size();
    }

    public static class StatusListViewHolder extends RecyclerView.ViewHolder {
        private ProblemSubmitResultTextView returnType;
        private TextView language_length;
        private TextView userName;
        private TextView memory_time_cost;
        private TextView problemName;
        private TextView submitTime;

        public StatusListViewHolder(View itemView) {
            super(itemView);
            returnType = itemView.findViewById(R.id.status_return_type);
            language_length = itemView.findViewById(R.id.status_language_length);
            userName = itemView.findViewById(R.id.status_user_name);
            memory_time_cost = itemView.findViewById(R.id.status_memory_time_cost);
            problemName = itemView.findViewById(R.id.status_contest_problem_name);
            submitTime = itemView.findViewById(R.id.status_submit_time);
        }
    }
}
