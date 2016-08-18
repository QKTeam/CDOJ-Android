package cn.edu.uestc.acm.cdoj_android.net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lenovo on 2016/8/7.
 */
public class NetWorkTool {
    public static InputStream _post(String url, String params){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept" , "*/*");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes("utf-8"));
            outputStream.flush();
            Log.d("_post", ""+ connection.getResponseCode());
            return connection.getInputStream();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public static InputStream _get(String url){
        try {
            URLConnection connection = new URL(url).openConnection();
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String post(String url, String params){
        return getString(_post(url, params));
    }
    public static String get(String url){
        return getString(_get(url));
    }
    public static String getString(InputStream is){
        String string = null;
        if (is == null) {
            return null;
        }
        byte[] buffer = new byte[1024*320];
        int len = 0, tlen = 0;
        try {
            while (tlen != -1){
                tlen = is.read(buffer, len, 1024);
                if (tlen != -1){
                    len += tlen;
                }
            }
            string = new String(buffer, 0 , len, "utf-8");
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return string;
    }
}
