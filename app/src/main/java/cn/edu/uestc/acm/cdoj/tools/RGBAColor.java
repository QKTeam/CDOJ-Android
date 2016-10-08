package cn.edu.uestc.acm.cdoj.tools;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by Great on 2016/10/8.
 */

public class RGBAColor {
    public int R = 0;
    public int G = 0;
    public int B = 0;
    private Integer A;

    public RGBAColor(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public RGBAColor(int R, int G, int B, int A) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = A;
    }

    public Integer getA() {
        return A;
    }

    public float[] getColorMatrix(boolean skew) {
        if (skew) {
            if (A == null) {
                return new float[]{
                        1, 0, 0, 0, R,
                        0, 1, 0, 0, G,
                        0, 0, 1, 0, B,
                        0, 0, 0, 1, 0};
            }
            return new float[]{
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, 1, A};
        }
        if (A == null) {
            return new float[]{
                    0, 0, 0, 0, R,
                    0, 0, 0, 0, G,
                    0, 0, 0, 0, B,
                    0, 0, 0, 1, 0};
        }
        return new float[]{
                0, 0, 0, 0, R,
                0, 0, 0, 0, G,
                0, 0, 0, 0, B,
                0, 0, 0, 0, A};
    }

    public static float[] getColorMatrix(int R, int G, int B, boolean skew) {
        if (skew) {
            return new float[]{
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, 1, 0};
        }
        return new float[]{
                0, 0, 0, 0, R,
                0, 0, 0, 0, G,
                0, 0, 0, 0, B,
                0, 0, 0, 1, 0};
    }

    public static float[] getColorMatrix(int R, int G, int B, int A, boolean skew) {
        if (skew) {
            return new float[]{
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, 1, A};
        }
        return new float[]{
                0, 0, 0, 0, R,
                0, 0, 0, 0, G,
                0, 0, 0, 0, B,
                0, 0, 0, 0, A};
    }

    public static float[] getColorMatrix(Context context, @ColorRes int colorResource) {
        return getColorMatrix(context, colorResource, false);
    }

    public static float[] getColorMatrix(Context context, @ColorRes int colorResource, boolean skew) {
        int color = ContextCompat.getColor(context, colorResource);
        int A = (color & 0xff000000) >>> 24;
        int R = (color & 0x00ff0000) >>> 16;
        int G = (color & 0x0000ff00) >>> 8;
        int B = (color & 0x000000ff);
        if (skew) {
            return new float[]{
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, 1, A};
        }
        return new float[]{
                0, 0, 0, 0, R,
                0, 0, 0, 0, G,
                0, 0, 0, 0, B,
                0, 0, 0, 0, A};
    }

    public static float[] getColorMatrixWithPercentAlpha(int R, int G, int B, double A, boolean skew) {
        if (skew) {
            return new float[]{
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, (float)A, 0};
        }
        return new float[]{
                0, 0, 0, 0, R,
                0, 0, 0, 0, G,
                0, 0, 0, 0, B,
                0, 0, 0, (float)A, 0};
    }
}
