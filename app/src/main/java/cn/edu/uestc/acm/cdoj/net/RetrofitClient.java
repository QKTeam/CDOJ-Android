package cn.edu.uestc.acm.cdoj.net;

import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lagranmoon on 2018/2/14.
 * 用来初始化Retrofit
 */

public class RetrofitClient {

    private static RetrofitClient instance;
    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    public RetrofitClient(){
        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(mOkHttpBuilder.build());
    }

    public static RetrofitClient getInstance(){
        synchronized (RetrofitClient.class){
            if (instance == null){
                return new  RetrofitClient();
            }
        }
        return instance;
    }

    public Retrofit.Builder getRetrofitBuilder(){
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit(){
        return mRetrofitBuilder.build();
    }

}
