package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;


public class ListViewFooter extends LinearLayout {

    public static final int LOADING = 0x100;
    public static final int LOADCOMPLETE = 0x101;
    public static final int LOADPROBLEM = 0x102;
    public static final int BLANK = 0x103;
    public static final int DATAISNULL = 0x104;
    public static final int NETNOTCONNECT = 0x105;
    public static final int CONNECTOVERTIME = 0x106;

    @IntDef({LOADING, LOADCOMPLETE, LOADPROBLEM, BLANK,
            DATAISNULL, NETNOTCONNECT, CONNECTOVERTIME})
    @Retention(RetentionPolicy.SOURCE)
    @interface pullUpLoadListViewFooterStatus {}

    private LinearLayout mLoadingLayout;
    private LinearLayout mLoadCompleteLayout;
    private ImageView mImageView;
    private TextView mTextView;

    public ListViewFooter(Context context) {
        this(context, null);
    }

    public ListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.list_view_with_gesture_load_footer, this);
        mLoadingLayout = (LinearLayout) findViewById(R.id.pull_up_load_list_view_footer_loading);
        mLoadCompleteLayout = (LinearLayout) findViewById(R.id.pull_up_load_list_view_footer_load_finish);
        mImageView = (ImageView) mLoadCompleteLayout.getChildAt(0);
        mTextView = (TextView) mLoadCompleteLayout.getChildAt(1);
        updateContent(BLANK);
    }

    public void updateContent(@pullUpLoadListViewFooterStatus int state) {
        switch (state) {
            case BLANK:
                mLoadingLayout.setVisibility(View.GONE);
                mLoadCompleteLayout.setVisibility(View.GONE);
                return;
            case LOADING:
                mLoadingLayout.setVisibility(View.VISIBLE);
                mLoadCompleteLayout.setVisibility(View.GONE);
                return;
            case LOADCOMPLETE:
                mImageView.setImageDrawable(Global.getListFooterIcon_done());
                mTextView.setText(getContext().getString(R.string.loadComplete));
                break;
            case LOADPROBLEM:
                mImageView.setImageDrawable(Global.getListFooterIcon_problem());
                mTextView.setText(getContext().getString(R.string.loadProblem));
                break;
            case DATAISNULL:
                mImageView.setImageDrawable(Global.getListFooterIcon_noData());
                mTextView.setText(getContext().getString(R.string.noData));
                break;
            case NETNOTCONNECT:
                mImageView.setImageDrawable(Global.getListFooterIcon_netProblem());
                mTextView.setText(getContext().getString(R.string.netNotConnect));
                break;
            case CONNECTOVERTIME:
                mImageView.setImageDrawable(Global.getListFooterIcon_netProblem());
                mTextView.setText(getContext().getString(R.string.connectOvertime));
                break;
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadCompleteLayout.setVisibility(View.VISIBLE);
    }

    public void updateContent(@DrawableRes int imageResource, String text, boolean themeRenderImage ) {
        mLoadingLayout.setVisibility(View.GONE);
        mLoadCompleteLayout.setVisibility(View.VISIBLE);
        mTextView.setText(text);
        mImageView.setImageBitmap(DrawImage.draw(getContext(), imageResource, themeRenderImage));
    }

    public void updateContent(Bitmap imageBitmap, String text, boolean themeRenderImage) {
        mLoadingLayout.setVisibility(View.GONE);
        mLoadCompleteLayout.setVisibility(View.VISIBLE);
        mTextView.setText(text);
        if (themeRenderImage) imageBitmap = DrawImage.render(imageBitmap, Global.getMainColorMatrix());
        mImageView.setImageBitmap(imageBitmap);
    }
}
