package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.article.ArticleListItem;
import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.utils.TimeFormat;

/**
 * Created by 14779 on 2017-7-21.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private List<ArticleListItem> articleList;
    private RecyclerViewItemClickListener clickListener;

    public ArticleAdapter(Context context, List<ArticleListItem> articleList){
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.article_item, parent, false), clickListener);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        ArticleListItem positionArticle = articleList.get(position);
        holder.article_title.setText(positionArticle.getTitle());
        holder.article_content.setText(positionArticle.getContent());
        holder.article_time.setText(TimeFormat.changeDataFormat(positionArticle.getTime(), "yyyy-MM-dd HH:mm:ss"));
        holder.article_authour.setText(positionArticle.getOwnerName());

    }

    public void setClickListener(RecyclerViewItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView article_title;
        private TextView article_content;
        private TextView article_time;
        private TextView article_authour;
        private View view;
        private RecyclerViewItemClickListener clickListener;
        public ArticleViewHolder(View itemView, RecyclerViewItemClickListener clickListener) {
            super(itemView);
            article_title = itemView.findViewById(R.id.article_title);
            article_content = itemView.findViewById(R.id.article_content);
            article_time = itemView.findViewById(R.id.article_time);
            article_authour = itemView.findViewById(R.id.article_authour);
            view = itemView;
            this.clickListener = clickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}
