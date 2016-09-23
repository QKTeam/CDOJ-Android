package cn.edu.uestc.acm.cdoj_android.layout.detail.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;


/**
 * Created by QK on 2016/9/23.
 */

public class HeaderImage implements ViewHandler {

    private ImageView header;

    public HeaderImage(ImageView view) {
        header = view;
    }

    @Override
    public void show(int which, Object data, long time) {
        header.setImageBitmap((Bitmap) ((ArrayList<Object>) data).get(1));
    }
}
