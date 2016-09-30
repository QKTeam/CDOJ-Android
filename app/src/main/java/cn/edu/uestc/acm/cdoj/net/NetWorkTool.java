package cn.edu.uestc.acm.cdoj.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
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
import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/7.
 */
public class NetWorkTool {
    public final static int STATE_OK = 1, STATE_ERROR = 2;
    private static ArrayList<NetStateListener> listeners = new ArrayList();
    private static int state = STATE_ERROR;
    final static String TAG = "------NetWorkTool------";
    static HttpClient httpClient;

    static {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,
                HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https",
                SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                params, schReg);

        httpClient = new DefaultHttpClient(conMgr, params);
    }
    public static void addNetStateListener(NetStateListener listener){
        listeners.add(listener);
    }
    public static String getOrPost(String req[]){
        String re = req.length == 1?get(req[0]):post(req[0], req[1]);
        int now;
        if (re == null){
            now = STATE_ERROR;
        }
        else {
            now = STATE_OK;
        }
        for (NetStateListener listener:listeners) {
            listener.onNetStateChange(state, now);
        }
        state = now;
        return re;
    }
    public static String post(String url, String params) {
//        HttpClient httpClient = new DefaultHttpClient();
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
//        HttpClient httpClient = new DefaultHttpClient();
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
    public static Object[] getBytes(InputStream is){
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
            return new Object[]{buffer, len};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getString(InputStream is) {
        String string = null;
        try {
            Object[] objects = getBytes(is);
            if(objects != null){
                string = new String((byte[]) objects[0], 0, (int)objects[1], "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return string;
    }
    public static String md(String s, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = s.getBytes();
            md.update(bytes);
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String sha1(String s){
        return md(s, "SHA-1");
    }
}
