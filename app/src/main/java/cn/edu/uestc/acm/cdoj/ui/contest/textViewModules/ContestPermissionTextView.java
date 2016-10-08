package cn.edu.uestc.acm.cdoj.ui.contest.textViewModules;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by QK on 2016/9/18.
 */
public class ContestPermissionTextView extends TextView {

    public ContestPermissionTextView(Context context) {
        super(context);
    }

    public ContestPermissionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContestPermissionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text.equals("Public")){
            setTextColor(ContextCompat.getColor(getContext(), R.color.contestPermission_Public));
            return;
        }
        if (text.equals("Private")) {
            setTextColor(ContextCompat.getColor(getContext(), R.color.contestPermission_Private));
            return;
        }
        if (text.equals("Invited")) {
            setTextColor(ContextCompat.getColor(getContext(), R.color.contestPermission_Invited));
            return;
        }
        if (text.equals("Diy")) {
            setTextColor(ContextCompat.getColor(getContext(), R.color.contestPermission_Diy));
            return;
        }
        if (text.equals("Onsite"))
            setTextColor(ContextCompat.getColor(getContext(), R.color.contestPermission_Onsite));
    }

}
