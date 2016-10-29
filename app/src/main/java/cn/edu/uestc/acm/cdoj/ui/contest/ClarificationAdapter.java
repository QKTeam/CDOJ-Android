package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.ClarificationData;

/**
 * Created by Grea on 2016/10/27.
 */

public class ClarificationAdapter extends BaseAdapter {
    private List<ClarificationData> clarificationDataList;
    private LayoutInflater mInflater;

    ClarificationAdapter(Context context, List<ClarificationData> clarificationDataList) {
        this.clarificationDataList = clarificationDataList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clarificationDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return clarificationDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClarificationData clarificationData = clarificationDataList.get(position);
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.contest_clarification_list_item, parent, false);
        }
        ((ImageView) v.findViewById(R.id.contestClarification_header))
                .setImageDrawable(clarificationData.avatar);
        ((TextView) v.findViewById(R.id.contestClarification_user))
                .setText(clarificationData.getOwnerName());
        ((TextView) v.findViewById(R.id.contestClarification_submitDate))
                .setText(clarificationData.timeString);
        ((TextView) v.findViewById(R.id.contestClarification_content))
                .setText(clarificationData.contentWithoutLink);
        v.setTag(position);
        return v;
    }
}
