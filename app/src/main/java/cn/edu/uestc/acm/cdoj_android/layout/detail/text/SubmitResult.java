package cn.edu.uestc.acm.cdoj_android.layout.detail.text;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj_android.R;

/**
 * Created by Great on 2016/9/22.
 */

public class SubmitResult extends TextView {
    public SubmitResult(Context context) {
        super(context);
    }

    public SubmitResult(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubmitResult(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text.equals("Accepted")){
            setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            return;
        }
        setTextColor(ContextCompat.getColor(getContext(), R.color.red));
    }
}
