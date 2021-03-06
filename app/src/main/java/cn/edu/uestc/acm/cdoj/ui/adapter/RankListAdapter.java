package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListItem;

/**
 * Created by 14779 on 2017-8-5.
 */

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.RankListViewHolder> {
    private static final String TAG = "RankListAdapter";
    private Context context;
    private List<RankListItem> listItems;
    private List<Bitmap> avatarList;
    private RecyclerViewItemClickListener clickListener;

    public RankListAdapter(Context context, List<RankListItem> listItems, List<Bitmap> avatarList){
        this.context = context;
        this.listItems = listItems;
        this.avatarList = avatarList;
    }

    @Override
    public RankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankListViewHolder(LayoutInflater.from(context).inflate(R.layout.contest_rank_list_item, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(RankListViewHolder holder, int position) {
        RankListItem positionItem = listItems.get(position);
        holder.avatar.setImageBitmap(avatarList.get(position));
        holder.user_name.setText(positionItem.getName());
        holder.user_nickname.setText(positionItem.getNickName());
        holder.rank.setText("Rank"+positionItem.getRank());
        holder.star_layout.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void setClickListener(RecyclerViewItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static class RankListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView avatar;
        private TextView user_name;
        private TextView user_nickname;
        private TextView rank;
        private LinearLayout star_layout;
        private RecyclerViewItemClickListener clickListener;

        public RankListViewHolder(View itemView, RecyclerViewItemClickListener clickListener) {
            super(itemView);
            avatar = itemView.findViewById(R.id.contest_rank_list_user_avatar);
            user_name = itemView.findViewById(R.id.contest_rank_list_user_name);
            user_nickname = itemView.findViewById(R.id.contest_rank_list_user_nickname);
            rank = itemView.findViewById(R.id.contest_rank_list_rank);
            this.star_layout = itemView.findViewById(R.id.contest_rank_list_star_layout);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}
