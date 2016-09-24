package cn.edu.uestc.acm.cdoj_android.net;

/**
 * Created by qwe on 16-9-24.
 */
public interface NetStateListener {
    void onNetStateChange(int last, int now);
}
