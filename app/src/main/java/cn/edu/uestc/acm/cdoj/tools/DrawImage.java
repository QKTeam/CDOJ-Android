package cn.edu.uestc.acm.cdoj.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Great on 2016/10/8.
 */

public class DrawImage {

    private int imageResource = -1;
    private float[] colorMatrix;
    private Context context;

    public DrawImage(Context context) {
        this.context = context;
    }

    public DrawImage(Context context, @DrawableRes int imageResource) {
        this.context = context;
        this.imageResource = imageResource;
    }

    public DrawImage(Context context, @DrawableRes int imageResource, float[] colorMatrix) {
        this.context = context;
        this.colorMatrix = colorMatrix;
        this.imageResource = imageResource;
    }

    public Bitmap draw(boolean themeRender) {
        if (imageResource == -1) return null;
        return draw(context, imageResource, null, themeRender);
    }

    public Bitmap draw(@DrawableRes int imageResource, boolean themeRender) {
        return draw(context, imageResource, null, themeRender);
    }

    public Bitmap draw(@DrawableRes int imageResource, float[] colorMatrix) {
        return draw(context, imageResource, colorMatrix, true);
    }

    public static Bitmap draw(Context context, @DrawableRes int imageResource, boolean themeRender) {
        return draw(context, imageResource, null, themeRender);
    }

    public static Bitmap draw(Context context, @DrawableRes int imageResource, float[] colorMatrix) {
        return draw(context, imageResource, colorMatrix, true);
    }

    public static Bitmap draw(Context context, @DrawableRes int imageResource, RGBColor color) {
        float[] colorMatrix = new float[]{
                0, 0, 0, 0, color.R,
                0, 0, 0, 0, color.G,
                0, 0, 0, 0, color.B,
                0, 0, 0, 1, 0
        };
        return draw(context, imageResource, colorMatrix, true);
    }

    public static Bitmap draw(Context context, @DrawableRes int imageResource, RGBAColor color) {
        float[] colorMatrix = new float[]{
                0, 0, 0, 0, color.R,
                0, 0, 0, 0, color.G,
                0, 0, 0, 0, color.B,
                0, 0, 0, 0, color.A
        };
        return draw(context, imageResource, colorMatrix, true);
    }

    public static Bitmap draw(Context context, @DrawableRes int imageResource, float[] colorMatrix, boolean themeRender) {
        if (colorMatrix == null) colorMatrix = Global.mainColorMatrix;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
        if (themeRender) {
            return render(bitmap, colorMatrix);
        }
        return bitmap;
    }

    public static Bitmap render(Bitmap bitmap, RGBColor color) {
        float[] colorMatrix = new float[]{
                0, 0, 0, 0, color.R,
                0, 0, 0, 0, color.G,
                0, 0, 0, 0, color.B,
                0, 0, 0, 1, 0
        };
        return render(bitmap, colorMatrix);
    }

    public static Bitmap render(Bitmap bitmap, RGBAColor color) {
        float[] colorMatrix = new float[]{
                0, 0, 0, 0, color.R,
                0, 0, 0, 0, color.G,
                0, 0, 0, 0, color.B,
                0, 0, 0, 0, color.A
        };
        return render(bitmap, colorMatrix);
    }

    public static Bitmap render(Bitmap bitmap, float[] colorMatrix) {
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return afterBitmap;
    }
}
