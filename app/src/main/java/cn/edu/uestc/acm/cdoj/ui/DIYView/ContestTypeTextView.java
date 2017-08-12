package cn.edu.uestc.acm.cdoj.ui.DIYView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 14779 on 2017-8-10.
 */

public class ContestTypeTextView extends TextView {

    public ContestTypeTextView(Context context) {
        super(context);
    }

    public ContestTypeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContestTypeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ContestTypeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
