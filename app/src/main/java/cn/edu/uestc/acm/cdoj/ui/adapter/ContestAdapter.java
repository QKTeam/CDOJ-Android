package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.contest.ContestListItem;
import cn.edu.uestc.acm.cdoj.utils.TimeFormat;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {
    private Context context;
    private List<ContestListItem> contestListItemList;
    private RecyclerViewItemClickListener itemClickListener;

    public ContestAdapter(Context context, List<ContestListItem> contestListItemList){
        this.context = context;
        this.contestListItemList = contestListItemList;
    }

    @Override
    public ContestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContestViewHolder(LayoutInflater.from(context).inflate(R.layout.contest_item, parent, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(ContestViewHolder holder, int position) {
        ContestListItem positionItem = contestListItemList.get(position);
        String contestType = null;
        switch (positionItem.getType()){
            case 0:
                contestType = "Public";
                break;
            case 1:
                contestType = "Private";
                break;
            case 3:
                contestType = "Register";
                break;
            case 5:
                contestType = "Onsite";
                break;
        }
        holder.title.setText(positionItem.getTitle());
        holder.time.setText(TimeFormat.changeDataFormat(positionItem.getTime(), "yyyy-MM-dd HH-mm-ss"));
        holder.rest_time.setText(TimeFormat.changeDataFormat(positionItem.getLength(),"HH:mm:ss") );
        holder.id.setText(String.valueOf(positionItem.getContestId()));
        holder.status.setText(positionItem.getStatus());
        holder.type.setText(contestType);
    }

    @Override
    public int getItemCount() {
        return contestListItemList.size();
    }

    public void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ContestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView time;
        private TextView rest_time;
        private TextView id;
        private TextView status;
        private TextView type;
        private View view;
        private RecyclerViewItemClickListener itemClickListener;

        public ContestViewHolder(View itemView, RecyclerViewItemClickListener itemClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.contest_title);
            time = itemView.findViewById(R.id.contest_time);
            rest_time = itemView.findViewById(R.id.contest_rest_time);
            id = itemView.findViewById(R.id.contest_id);
            status = itemView.findViewById(R.id.contest_status);
            type = itemView.findViewById(R.id.contest_type);
            view = itemView;
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
