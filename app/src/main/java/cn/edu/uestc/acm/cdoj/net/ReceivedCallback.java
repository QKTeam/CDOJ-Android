package cn.edu.uestc.acm.cdoj.net;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;

/**
 * Created by 14779 on 2017-7-21.
 */

public interface ReceivedCallback<T> {
    void onDataReceived(T t);

    void onLoginDataReceived(ContentReceived dataReceived);
}
