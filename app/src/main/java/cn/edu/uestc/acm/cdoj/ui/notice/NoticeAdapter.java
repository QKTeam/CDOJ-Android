package cn.edu.uestc.acm.cdoj.ui.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.ArticleData;

/**
 * Created by Grea on 2016/10/27.
 */

class NoticeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ArticleData> articleDataList;

    NoticeAdapter(Context context, List<ArticleData> articleDataList) {
        this.articleDataList = articleDataList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articleDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleData articleData = articleDataList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.notice_list_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.notice_list_item_author))
                .setText(articleData.getOwnerName());
        ((TextView) v.findViewById(R.id.notice_list_item_content))
                .setText(articleData.summary);
        ((TextView) v.findViewById(R.id.notice_list_item_date))
                .setText(articleData.timeString);
        ((TextView) v.findViewById(R.id.notice_list_item_title))
                .setText(articleData.getTitle());
        return v;
    }
}
