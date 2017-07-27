package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.article.ArticleListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.ArticleAdapter;

/**
 * Created by 14779 on 2017-7-23.
 * 获得从网络获取的数据并传给GenaralFragment
 * 传递Adapter给GenaralFragment中的recyclerView
 * 给GenaralFragment中的recyclerView设置点击监听
 */

public class ArticleListData extends AbsDataList<ArticleListItem> {
    private static final String TAG = "ArticleListData";
    private static List<ArticleListItem> data = new ArrayList<>();

    public ArticleListData(Context context){
        super(context);
        Connection.instance.searchArticle(mPageInfo.currentPage , "time", this);
        ArticleListData.setData(super.data);
    }

    @Override
    protected void createAdapter() {
        ArticleAdapter articleAdapter = new ArticleAdapter(context, super.data);
        articleAdapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                transItemDataListener.onTranItemData(position, "articleFragment");
            }
        });
        super.adapter = articleAdapter;
    }

    public static void setData(List<ArticleListItem> data){
        ArticleListData.data = data;
    }

    public static List<ArticleListItem> getData(){
        return data;
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.currentPage < mPageInfo.getTotalPages()){
            Connection.instance.searchArticle(mPageInfo.currentPage+1, "time", this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.searchArticle(1, "time", this);
        isfreshing = true;
    }
}
