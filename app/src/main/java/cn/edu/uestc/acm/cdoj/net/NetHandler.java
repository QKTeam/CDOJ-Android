package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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

    public static class Request {
        public static final int GET = 0x1;
        public static final int POST = 0x2;
        public static final int AVATAR = 0x3;
    }

    public static class Thread {
        public static final int UITHREAD = 0x11;
        public static final int NETTHREAD = 0x12;
    }

    public static class Status {
        public static final int DEFAULT = 0x100;
        public static final int NETNOTCONECT = 0x101;
        public static final int CONECTOVERTIME = 0x102;
        public static final int FALSE = 0x103;
        public static final int SUCCESS = 0x104;
        public static final int FINISH = 0x105;
        public static final int DATAISNULL = 0x106;
        public static final int GETDATAERROR = 0x107;
    }

    @IntDef({Request.GET, Request.POST, Request.AVATAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestMethod {
    }

    @IntDef({Status.NETNOTCONECT, Status.CONECTOVERTIME, Status.FALSE,
            Status.SUCCESS, Status.FINISH, Status.DATAISNULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultStatus {
    }

    private int requestMethod = Request.GET;
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
        result = new Result(requestType, Status.DEFAULT, extra, null);
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

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Thread.UITHREAD:
                if (result.getStatus() == Status.GETDATAERROR) {
                    onNetDataConverted(result);
                    return;
                }
                new ConvertNetDataThread(this)
                        .execute(jsonStringReceived);
                break;
        }
    }

    public void onGetNetData() {
        if (!isNetworkConnected(context)) {
            result.setStatus(Status.NETNOTCONECT);
            onNetDataConverted(result);
            return;
        }
        switch (requestMethod) {
            case Request.GET:
                jsonStringReceived = NetWorkUtils.getJsonString(context, urlString);
                return;
            case Request.POST:
                jsonStringReceived = NetWorkUtils.postJsonString(context, urlString, postJsonString);
        }
        if (jsonStringReceived == null) {
            result.setStatus(Status.GETDATAERROR);
        }
        sendEmptyMessage(Thread.UITHREAD);
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
