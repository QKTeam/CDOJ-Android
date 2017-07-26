package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemListItem;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder> {
    private Context context;
    private List<ProblemListItem> problemListItemList;
    private RecyclerViewItemClickListener itemClickListener;

    public ProblemAdapter(Context context, List<ProblemListItem> problemListItemList){
        this.problemListItemList = problemListItemList;
        this.context = context;
    }

    @Override
    public ProblemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProblemViewHolder(LayoutInflater.from(context).inflate(R.layout.problem_item, parent, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(ProblemViewHolder holder, int position) {
        ProblemListItem positionItem = problemListItemList.get(position);
        holder.title.setText(positionItem.getTitle());
        holder.content.setText(positionItem.getSource());
        holder.id.setText(String.valueOf(positionItem.getProblemId()));
        holder.solved.setText("Solved:"+positionItem.getTried());
        holder.tried.setText("Tried:"+positionItem.getTried());

    }

    @Override
    public int getItemCount() {
        return problemListItemList.size();
    }

    public void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addItems(List<ProblemListItem> newItems){
        problemListItemList.addAll(newItems);
        notifyDataSetChanged();
    }

    public static class ProblemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewItemClickListener itemClickListener;
        private TextView title;
        private TextView content;
        private TextView id;
        private TextView solved;
        private TextView tried;
        private View view;

        public ProblemViewHolder(View itemView, RecyclerViewItemClickListener itemClickListener) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.problem_title);
            content = itemView.findViewById(R.id.problem_content);
            id = itemView.findViewById(R.id.problem_id);
            solved = itemView.findViewById(R.id.problem_solved);
            tried = itemView.findViewById(R.id.problem_tried);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
