package cn.edu.uestc.acm.cdoj_android.layout.detail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by QK on 2016/9/18.
 */
public class ContestRankAdapter extends SimpleAdapter implements ViewHandler {

    private List<? extends Map<String, ?>> data;
    private Context context;
    private View[] items;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ContestRankAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
        items = new View[data.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (items[position] == null) {
            View v = super.getView(position, null, parent);
            LinearLayout solvedContainer = (LinearLayout) v.findViewById(R.id.contestRank_solved);
            ImageView header = (ImageView) v.findViewById(R.id.contestClarification_header);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int probCount = (Integer) data.get(position).get("probCount");
            int solvedDetail = (Integer) data.get(position).get("solvedDetail");
            char text = 'A';
            for (int i = 0; i != probCount; ++i) {
                TextView textView = (TextView) inflate.inflate(R.layout.contest_rank_problem_mark, solvedContainer, false);
                textView.setText(String.valueOf(text));
                if ((solvedDetail & 1) == 1){
                    textView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                }
                solvedContainer.addView(textView);
                solvedContainer.addView(inflate.inflate(R.layout.contest_rank_problem_mark, solvedContainer, false));
                ++text;
                solvedDetail = solvedDetail >> 1;
            }
            items[position] = v;
//            NetData.getAvatar((String) data.get(position).get("email"), header, this);
        }
        return items[position];
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        items = new View[data.size()];
    }

    @Override
    public void show(int which, Object data, long time) {
        Object[] dataReceive = (Object[]) data;
//        ((ImageView) dataReceive[0]).setImageBitmap((Bitmap) dataReceive[1]);
    }
}
