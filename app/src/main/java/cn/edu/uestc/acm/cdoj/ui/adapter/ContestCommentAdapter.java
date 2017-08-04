package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.contest.ContestCommentListItem;
import cn.edu.uestc.acm.cdoj.utils.TimeFormat;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentAdapter extends RecyclerView.Adapter<ContestCommentAdapter.ContestCommentViewHolder> {
    private static final String TAG = "ContestCommentAdapter";
    private Context context;
    private List<ContestCommentListItem> contestCommentListItemList;
    private List<Bitmap> listAvatar;

    public ContestCommentAdapter(Context context, List<ContestCommentListItem> contestCommentListItemList, List<Bitmap> listAvatar){
        this.context = context;
        this.contestCommentListItemList = contestCommentListItemList;
        this.listAvatar = listAvatar;
    }

    @Override
    public ContestCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContestCommentViewHolder(LayoutInflater.from(context).inflate(R.layout.contest_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContestCommentViewHolder holder, int position) {
        ContestCommentListItem positionItem = contestCommentListItemList.get(position);
        holder.avatar.setImageBitmap(listAvatar.get(position));
        holder.studentId.setText(positionItem.getOwnerName());
        holder.time.setText(TimeFormat.changeDataFormat(positionItem.getTime(), "yyyy-hh-dd HH-mm-ss"));
        holder.comment.setText(positionItem.getContent());
    }

    @Override
    public int getItemCount() {
        return contestCommentListItemList.size();
    }

    public static class ContestCommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView studentId;
        private TextView time;
        private TextView comment;
        public ContestCommentViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.contest_comment_user_avatar);
            studentId = itemView.findViewById(R.id.contest_comment_user_name);
            time = itemView.findViewById(R.id.contest_comment_user_time);
            comment = itemView.findViewById(R.id.contest_comment_user_comment);
        }
    }
}
