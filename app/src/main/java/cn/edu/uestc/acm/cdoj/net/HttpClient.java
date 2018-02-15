package cn.edu.uestc.acm.cdoj.net;

import okhttp3.OkHttpClient;

/**
 * Created by lagranmoon on 2018/2/14.
 * 用来初始化OkHttp Client
 */

public class HttpClient {

    private static HttpClient instance;
    private OkHttpClient.Builder builder;

    private HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {

        synchronized (HttpClient.class) {
            if (instance == null) {
                return new HttpClient();
            }
        }

        return instance;
    }

    public OkHttpClient.Builder getBuilder(){
        return builder;
    }

}
