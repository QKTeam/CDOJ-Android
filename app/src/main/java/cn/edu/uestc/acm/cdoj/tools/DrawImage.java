package cn.edu.uestc.acm.cdoj.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;

import cn.edu.uestc.acm.cdoj.Resource;

/**
 * Created by Great on 2016/10/8.
 */

public class DrawImage {

    public static BitmapDrawable draw(Context context, @DrawableRes int imageResource) {
        return draw(context, imageResource, null);
    }

    public static BitmapDrawable draw(Context context, @DrawableRes int imageResource, float[] colorMatrix) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
        return draw(context, bitmap, colorMatrix);
    }

    public static BitmapDrawable draw(Context context, Bitmap bitmap) {
        return draw(context, bitmap, null);
    }

    public static BitmapDrawable draw(Context context, Bitmap bitmap, float[] colorMatrix) {
        if (colorMatrix == null) colorMatrix = Resource.getMainColorMatrix();
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return new BitmapDrawable(context.getResources(), afterBitmap);
    }
}
