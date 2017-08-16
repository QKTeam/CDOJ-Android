package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListDetailItem;

/**
 * Created by 14779 on 2017-8-8.
 */

public class RankListDetailAdapter extends BaseAdapter {
    private Context context;
    private List<RankListDetailItem> list;
    public RankListDetailAdapter(Context context, List<RankListDetailItem> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.contest_rank_list_detail_list_item, viewGroup, false);
        }
        LinearLayout container = convertView.findViewById(R.id.contest_rank_list_detail_list_item_container);
        
        return convertView;
    }
}
