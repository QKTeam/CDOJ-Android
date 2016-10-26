package cn.edu.uestc.acm.cdoj.net;


import android.support.annotation.NonNull;

/**
 * Created by Grea on 2016/10/24.
 */

public interface ConvertNetData {
    @NonNull Result onConvertNetData(String jsonString, Result result);

    void onNetDataConverted(Result result);
}
