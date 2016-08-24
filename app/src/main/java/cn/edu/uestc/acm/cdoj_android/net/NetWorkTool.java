package cn.edu.uestc.acm.cdoj_android.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lenovo on 2016/8/7.
 */
public class NetWorkTool {
    static String TAG = "---------NetWorkTool----------";
    static HttpClient httpClient;

    static {
        httpClient = new DefaultHttpClient();
    }

    public static String post(String url, String params) {
//        Log.d("TAG", "new post: ");
        HttpPost httpPost = new HttpPost(url);
        Log.d(TAG, "post: " + params);
        httpPost.addHeader("Content-Type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post1(String url, String params) {
        return getString(_post(url, params));
    }
    public static String get1(String url) {
        return getString(_get(url));
    }
    public static InputStream _post(String url, String params) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes("utf-8"));
            outputStream.flush();
            Log.d("_post", "" + connection.getResponseCode());
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static InputStream _get(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getString(InputStream is) {
        String string = null;
        if (is == null) {
            return null;
        }
        byte[] buffer = new byte[1024 * 320];
        int len = 0, tlen = 0;
        try {
            while (tlen != -1) {
                tlen = is.read(buffer, len, 1024);
                if (tlen != -1) {
                    len += tlen;
                }
            }
            string = new String(buffer, 0, len, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return string;
    }
    public static String sha1(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = s.getBytes();
            md.update(bytes);
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

}
