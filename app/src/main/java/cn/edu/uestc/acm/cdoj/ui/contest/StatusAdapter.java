package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.StatusData;

/**
 * Created by Grea on 2016/10/28.
 */

public class StatusAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<StatusData> statusDataList;

    StatusAdapter(Context context, List<StatusData> statusDataList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.statusDataList = statusDataList;
    }

    @Override
    public int getCount() {
        return statusDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return statusDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatusData statusData = statusDataList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.status_list_item, parent, false);
        }
        ((TextView) v.findViewById(R.id.status_list_item_codeLength))
                .setText(statusData.lengthString);
        ((TextView) v.findViewById(R.id.status_list_item_language))
                .setText(statusData.getLanguage());
        ((TextView) v.findViewById(R.id.status_list_item_memoryCost))
                .setText(statusData.memoryCostString);
        ((TextView) v.findViewById(R.id.status_list_item_probOrder))
                .setText(statusData.problemIdString);
        ((TextView) v.findViewById(R.id.status_list_item_result))
                .setText(statusData.getReturnType());
        ((TextView) v.findViewById(R.id.status_list_item_submitDate))
                .setText(statusData.timeString);
        ((TextView) v.findViewById(R.id.status_list_item_timeCost))
                .setText(statusData.timeCostString);
        ((TextView) v.findViewById(R.id.status_list_item_user))
                .setText(statusData.getUserName());
        return v;
    }
}
