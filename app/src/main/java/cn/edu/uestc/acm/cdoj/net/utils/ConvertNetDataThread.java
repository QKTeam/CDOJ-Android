package cn.edu.uestc.acm.cdoj.net.utils;

import android.os.AsyncTask;


import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;

/**
 * Created by Grea on 2016/10/23.
 */

public class ConvertNetDataThread extends AsyncTask<String, Void, Result> {
    private NetHandler mHandler;

    public ConvertNetDataThread(NetHandler mHandler) {
        super();
        this.mHandler = mHandler;
    }

    @Override
    protected Result doInBackground(String... dataReceive) {
        return mHandler.onConvertNetData(dataReceive[0]);
    }

    @Override
    protected void onPostExecute(Result result) {
        mHandler.onNetDataConverted(result);
    }
}
