package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.edu.uestc.acm.cdoj_android.R;

/**
 * Created by QK on 2016/9/18.
 */
public class ContestRankAdapter extends SimpleAdapter {

    private List<? extends Map<String, ?>> data;
    private Context context;
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        LinearLayout solvedContainer = (LinearLayout) v.findViewById(R.id.contestRank_solved);
        if (convertView == null) {
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int probCount = (Integer) data.get(position).get("probCount");
            int solvedDetail = (Integer) data.get(position).get("solvedDetail");
            char text = 'A';
            for (int i = 0; i != probCount; ++i) {
                TextView textView = (TextView) inflate.inflate(R.layout.contest_rank_problem_mark, solvedContainer, false);
                textView.setText(String.valueOf(text));
                if ((solvedDetail & (Integer.MAX_VALUE - 1)) != solvedDetail){
                    textView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                }
                solvedContainer.addView(textView);
                solvedContainer.addView(inflate.inflate(R.layout.contest_rank_problem_mark, solvedContainer, false));
                ++text;
                solvedDetail = solvedDetail >> 1;
            }
        }

        return v;
    }
}
