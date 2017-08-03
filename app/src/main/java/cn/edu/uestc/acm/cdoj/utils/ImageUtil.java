package cn.edu.uestc.acm.cdoj.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by lagranmoon on 2017/7/26.
 */

public class ImageUtil {


    public static Bitmap downloadImage(String url) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public static void saveImage(Context context, Bitmap bitmap, String Url) {
        File image_dir = new File(context.getFilesDir() + "/Images");
        File image = new File(image_dir, DigestUtil.md5(Url) + ".jpg");
        FileOutputStream fileOutputStream = null;
        if (!image_dir.exists()) {
            image_dir.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readImage(String Uri) {
        if (Uri != null) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(Uri);
                return BitmapFactory.decodeStream(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
