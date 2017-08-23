package cn.edu.uestc.acm.cdoj.utils;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by lagranmoon on 2017/8/23.
 */

public class FastJsonRequest<T> extends com.android.volley.Request<T> {

    private final Response.Listener<T> mListener;
    private Class<T> mClass;

    public FastJsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mClass = clazz;
        mListener = listener;
    }

    public FastJsonRequest( String url, Class<T> clazz,Response.Listener<T> listener,
                            Response.ErrorListener errorListener ) {
       this(Method.GET,url,clazz,listener,errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String (response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(JSON.parseObject(jsonString,mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


}
