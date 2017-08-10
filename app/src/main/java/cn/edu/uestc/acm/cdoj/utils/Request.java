package cn.edu.uestc.acm.cdoj.utils;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leigu on 2017/4/19.
 */

public class Request {


    public static byte[] post(String url, String path, String request) {
        CookieManager cookieManager = CookieManager.getInstance();
        CookieSyncManager cookieSyncManager;
        String cookie = "";
        try {
            cookieSyncManager = CookieSyncManager.getInstance();
        } catch (IllegalStateException e) {
            cookieSyncManager = null;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url+path).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);

            if (cookieManager != null) {
                cookie = cookieManager.getCookie(url);
            }
            connection.addRequestProperty("Cookie", cookie);
            connection.addRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(request.getBytes());
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            cookie = connection.getHeaderField("Set-Cookie");
            if (cookie != null && cookieManager != null) {
                cookieManager.setCookie(url, cookie);
                cookieSyncManager.sync();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            inputStream.close();
            bos.flush();
            byte[] response = bos.toByteArray();
            bos.close();
            Log.i("***************", "post: ******************"+response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] get(String url, String path) {
        CookieManager cookieManager = CookieManager.getInstance();
        CookieSyncManager cookieSyncManager;
        String cookie = "";
        try {
            cookieSyncManager = CookieSyncManager.getInstance();
        } catch (IllegalStateException e) {
            cookieSyncManager = null;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url+path).openConnection();
            connection.setRequestMethod("GET");
            if (cookieManager != null) {
                cookie = cookieManager.getCookie(url);
            }
            connection.addRequestProperty("Cookie", cookie);

            InputStream inputStream = connection.getInputStream();
            cookie = connection.getHeaderField("Set-Cookie");
            if (cookie != null && cookieManager != null) {
                cookieManager.setCookie(url, cookie);
                cookieSyncManager.sync();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            inputStream.close();
            byte[] response = bos.toByteArray();
            bos.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
