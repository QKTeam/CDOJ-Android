package cn.edu.uestc.acm.cdoj.net;

/**
 * Created by qwe on 16-9-24.
 * 在NetWorkTool里注册的网络监听接口
 */
public interface NetStateListener {
    void onNetStateChange(int last, int now);
}
