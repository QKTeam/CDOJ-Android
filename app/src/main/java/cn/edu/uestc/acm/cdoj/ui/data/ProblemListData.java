package cn.edu.uestc.acm.cdoj.ui.data;

import  android.content.Context;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.edu.uestc.acm.cdoj.genaralData.RecyclerViewItemClickListener;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemListItem;
import cn.edu.uestc.acm.cdoj.ui.adapter.ProblemAdapter;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemListData extends AbsDataList<ProblemListItem> {
    private static final String TAG = "ProblemListData";
    private static List<ProblemListItem> data = new ArrayList<>();
    private ProblemAdapter problemAdapter;

    public ProblemListData(Context context){
        super(context);
        Connection.instance.searchProblem(1, this);
        ProblemListData.setData(super.data);
    }

    @Override
    protected void createAdapter() {
        problemAdapter = new ProblemAdapter(context, getData());

        problemAdapter.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                transItemDataListener.onTranItemData(position, "problemFragment");
            }
        });
        super.adapter = problemAdapter;
    }

    public static List<ProblemListItem> getData(){
        return data;
    }

    public static void setData(List<ProblemListItem> data){
        ProblemListData.data = data;
    }

    @Override
    public void onLoadMore() {
        if (mPageInfo.currentPage < mPageInfo.getTotalPages()){
            Connection.instance.searchProblem(mPageInfo.currentPage+1, "id", this);
            Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "无更多内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        Connection.instance.searchProblem(1, "id", this);
        isRefreshing = true;
    }
}
