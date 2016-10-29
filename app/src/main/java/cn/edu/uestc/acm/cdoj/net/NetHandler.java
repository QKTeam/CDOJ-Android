package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.net.utils.ConvertNetDataThread;
import cn.edu.uestc.acm.cdoj.net.utils.NetWorkUtils;

/**
 * Created by Grea on 2016/10/23.
 */

public class NetHandler extends Handler {

    String TAG = "这是HANdle";

    public static final int GET = 0x1001;
    public static final int POST = 0x1002;
    public static final int AVATAR = 0x1003;

    public static final int CONVERT = 0x1005;
    public static final int AFTERCONVERT = 0x1006;

    @IntDef({GET, POST, AVATAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestMethod {
    }

    private int requestMethod = GET;
    private Context context;
    private String postJsonString;
    private String urlString;
    private String jsonStringReceived;
    private ConvertNetData convertNetData;
    private Result result;


    public NetHandler(Context context, @NetData.NetRequestType int requestType,
                      String urlString, Object extra, ConvertNetData convertNetData) {
        super();
        this.context = context;
        this.urlString = urlString;
        this.convertNetData = convertNetData;
        result = new Result(requestType, Result.DEFAULT, extra, null);
}


    public void setRequestMethod(@RequestMethod int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setPostJsonString(String postJsonString) {
        this.postJsonString = postJsonString;
    }

    public Context getContext() {
        return context;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setContent(Object content) {
        result.setContent(content);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case CONVERT:
                if (result.getStatus() == Result.NORESPONSE) {
                    onNetDataConverted(result);
                }else {
                    new ConvertNetDataThread(this)
                            .execute(jsonStringReceived);
                }
                break;

            case AFTERCONVERT:
                onNetDataConverted(result);
        }
    }

    public void onGetNetData() {
        if (!isNetworkConnected(context)) {
            result.setStatus(Result.NETNOTCONECT);
            sendEmptyMessage(AFTERCONVERT);
            return;
        }
        switch (requestMethod) {
            case GET:
                jsonStringReceived = NetWorkUtils.getJsonString(context, urlString);
                break;
            case POST:
                jsonStringReceived = NetWorkUtils.postJsonString(context, urlString, postJsonString);
        }
        if (jsonStringReceived == null) {
            result.setStatus(Result.NORESPONSE);
        }
        sendEmptyMessage(CONVERT);
    }

    public Result onConvertNetData(String jsonString) {
        return convertNetData.onConvertNetData(jsonString, result);
    }

    public void onNetDataConverted(Result result) {
        convertNetData.onNetDataConverted(result);
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
