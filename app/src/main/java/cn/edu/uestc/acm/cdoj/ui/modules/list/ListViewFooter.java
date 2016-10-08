package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import cn.edu.uestc.acm.cdoj.ui.modules.Global;


public class ListViewFooter extends LinearLayout {

    public static final int LOADING = 0;
    public static final int LOADCOMPLETE = 1;
    public static final int LOADPROBLEM = 2;
    public static final int BLANK = 3;
    public static final int OTHER = 4;

    @IntDef({LOADING, LOADCOMPLETE, LOADPROBLEM, BLANK})
    @Retention(RetentionPolicy.SOURCE)
    @interface pullUpLoadListViewFooterStatus {}

    @IntDef({OTHER})
    @Retention(RetentionPolicy.SOURCE)
    @interface pullUpLoadListViewFooterOtherStatus {}

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
        inflate(getContext(), R.layout.list_view_footer, this);
        mLoadingLayout = (LinearLayout) findViewById(R.id.pull_up_load_list_view_footer_loading);
        mLoadCompleteLayout = (LinearLayout) findViewById(R.id.pull_up_load_list_view_footer_load_finish);
        mImageView = (ImageView) mLoadCompleteLayout.getChildAt(0);
        mTextView = (TextView) mLoadCompleteLayout.getChildAt(1);
        updateContent(BLANK);
    }

    public void updateContent(@pullUpLoadListViewFooterStatus int state) {
        switch (state) {
            case LOADING:
                mLoadingLayout.setVisibility(View.VISIBLE);
                mLoadCompleteLayout.setVisibility(View.INVISIBLE);
                break;
            case LOADCOMPLETE:
                mLoadingLayout.setVisibility(View.INVISIBLE);
                mLoadCompleteLayout.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(drawIcon(R.drawable.ic_done_white));
                mTextView.setText(getContext().getString(R.string.loadComplete));
                break;
            case BLANK:
                mLoadingLayout.setVisibility(View.INVISIBLE);
                mLoadCompleteLayout.setVisibility(View.VISIBLE);
                break;
            case LOADPROBLEM:
                mLoadingLayout.setVisibility(View.INVISIBLE);
                mLoadCompleteLayout.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(drawIcon(R.drawable.ic_sync_problem_white));
                mTextView.setText(getContext().getString(R.string.loadProblem));
        }
    }

    public void updateContent(@pullUpLoadListViewFooterOtherStatus int state, @DrawableRes int imageResource, String text) {
        mLoadCompleteLayout.setVisibility(View.VISIBLE);
        mTextView.setText(text);
        mImageView.setImageBitmap(drawIcon(imageResource));
    }

    public void updateContent(@pullUpLoadListViewFooterOtherStatus int state, Bitmap imageBitmap, String text) {
        mLoadCompleteLayout.setVisibility(View.VISIBLE);
        mTextView.setText(text);
        mImageView.setImageBitmap(imageBitmap);
    }

    private Bitmap drawIcon(@DrawableRes int icon) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(Global.mainColorMatrix));
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return afterBitmap;
    }
}
