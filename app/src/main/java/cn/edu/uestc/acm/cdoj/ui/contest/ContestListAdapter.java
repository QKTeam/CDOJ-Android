package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.ContestListItemData;

/**
 * Created by Grea on 2016/10/27.
 */

public class ContestListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ContestListItemData> contestListItemDataList;

    ContestListAdapter(Context context, List<ContestListItemData> contestListItemDataList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contestListItemDataList = contestListItemDataList;
    }

    @Override
    public int getCount() {
        return contestListItemDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return contestListItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContestListItemData contestListItemData = contestListItemDataList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.contest_list_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.contest_list_item_id))
                .setText(contestListItemData.contestIdString);
        ((TextView) v.findViewById(R.id.contest_list_item_date))
                .setText(contestListItemData.timeString);
        ((TextView) v.findViewById(R.id.contest_list_item_permission))
                .setText(contestListItemData.getTypeName());
        ((TextView) v.findViewById(R.id.contest_list_item_status))
                .setText(contestListItemData.getStatus());
        ((TextView) v.findViewById(R.id.contest_list_item_timeLimit))
                .setText(contestListItemData.lengthString);
        ((TextView) v.findViewById(R.id.contest_list_item_title))
                .setText(contestListItemData.getTitle());
        return v;
    }
}
