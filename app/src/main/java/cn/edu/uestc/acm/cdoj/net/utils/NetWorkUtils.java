package cn.edu.uestc.acm.cdoj.net.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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

    static String TAG = "网络";

    public static String getJsonString(Context context, String url) {
        HttpURLConnection mUrlConnection = null;
        String resultJsonString = null;
        try {
            SharedPreferences mPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);

            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            mUrlConnection.addRequestProperty("Cookie", mPreferences.getString("cookie", ""));

            BufferedReader input = new BufferedReader(new InputStreamReader(mUrlConnection.getInputStream()));
            resultJsonString = input.readLine();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        return resultJsonString;
    }

    public static String postJsonString(Context context, String url, String json) {
        HttpURLConnection mUrlConnection = null;
        String resultJsonString = null;
        try {
            SharedPreferences mPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor mPreferencesEditor = mPreferences.edit();

            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            mUrlConnection.setRequestMethod("POST");
            mUrlConnection.setDoOutput(true);
            mUrlConnection.setChunkedStreamingMode(0);
            mUrlConnection.addRequestProperty("Content-Type", "application/json");
            mUrlConnection.addRequestProperty("Cookie", mPreferences.getString("cookie", ""));

            OutputStream output = new BufferedOutputStream(mUrlConnection.getOutputStream());
            output.write(json.getBytes());
            output.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(mUrlConnection.getInputStream()));
            resultJsonString = input.readLine();
            input.close();

            mPreferencesEditor.putString("cookie", mUrlConnection.getHeaderField("Cookie"));
            mPreferencesEditor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        return resultJsonString;
    }

    public static BitmapDrawable getAvatar(String url) {
        HttpURLConnection mUrlConnection = null;
        BitmapDrawable resultDrawable = null;
        try {
            mUrlConnection = (HttpURLConnection) new URL(url).openConnection();

            InputStream input = mUrlConnection.getInputStream();

            resultDrawable =  (BitmapDrawable) BitmapDrawable.createFromStream(input, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) mUrlConnection.disconnect();
        }
        return resultDrawable;
    }
}
