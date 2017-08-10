package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.homePage.RecentContestListItem;
import cn.edu.uestc.acm.cdoj.utils.TimeFormat;

/**
 * Created by 14779 on 2017-8-10.
 */

public class RecentContestAdapter extends RecyclerView.Adapter<RecentContestAdapter.RecentContestViewHolder> {

    private Context context;
    private List<RecentContestListItem> itemList;

    public RecentContestAdapter(Context context, List<RecentContestListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecentContestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentContestViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_recent_contest_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecentContestViewHolder holder, int position) {
        RecentContestListItem positionItem = itemList.get(position);
        holder.title.setText(positionItem.getName());
        holder.OJ.setText(positionItem.getOj());
        holder.access.setText(positionItem.getAccess());
        holder.start_time.setText(TimeFormat.changeDataFormat(positionItem.getStart_time(), "yyyy-mm-dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class RecentContestViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView OJ;
        private TextView access;
        private TextView start_time;

        public RecentContestViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recent_contest_title);
            OJ = itemView.findViewById(R.id.recent_contest_OJ);
            access = itemView.findViewById(R.id.recent_contest_access);
            start_time = itemView.findViewById(R.id.recent_contest_start_time);
        }
    }
}
