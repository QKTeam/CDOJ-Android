package cn.edu.uestc.acm.cdoj.net.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Grea on 2016/10/23.
 */

public class NetWorkUtils {

    static String TAG = "网络工具";
    public static String cookie = "";

    public static String getJsonString(Context context, String url) {
        HttpURLConnection mUrlConnection = null;
        String resultJsonString = null;
        try {
            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            mUrlConnection.addRequestProperty("Cookie", cookie);

            BufferedReader input = new BufferedReader(new InputStreamReader(mUrlConnection.getInputStream()));
            if (mUrlConnection.getHeaderField("Set-Cookie") != null) {
                cookie = mUrlConnection.getHeaderField("Set-Cookie");
            }
            resultJsonString = input.readLine();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        Log.d(TAG, "getJsonString:cookie: " + cookie + "  "+url+" " + resultJsonString);
        return resultJsonString;
    }

    public static String postJsonString(Context context, String url, String json) {
        HttpURLConnection mUrlConnection = null;
        String resultJsonString = null;
        try {
            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            mUrlConnection.setRequestMethod("POST");
            mUrlConnection.setDoOutput(true);
            mUrlConnection.setChunkedStreamingMode(0);
            mUrlConnection.addRequestProperty("Content-Type", "application/json");
            mUrlConnection.addRequestProperty("Cookie", cookie);

            OutputStream output = new BufferedOutputStream(mUrlConnection.getOutputStream());
            output.write(json.getBytes());
            output.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(mUrlConnection.getInputStream()));
            if (mUrlConnection.getHeaderField("Set-Cookie") != null) {
                cookie = mUrlConnection.getHeaderField("Set-Cookie");
            }
            resultJsonString = input.readLine();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        Log.d(TAG, "postJsonString:cookie: " + cookie + "  "+url+" "+json+" " + resultJsonString);
        return resultJsonString;
    }

    public static byte[] getAvatar(String url) {
        HttpURLConnection mUrlConnection = null;
        byte[] result = null;
        try {
            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();

            InputStream input = mUrlConnection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            input.close();
            result = bos.toByteArray();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        return result;
    }
}
