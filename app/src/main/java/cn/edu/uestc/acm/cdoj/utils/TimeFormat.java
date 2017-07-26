package cn.edu.uestc.acm.cdoj.utils;

import java.text.SimpleDateFormat;

/**
 * Created by 14779 on 2017-7-18.
 */

public class TimeFormat {
    private long time;

    public static String changeDataFormat(long time, String format){
        return new SimpleDateFormat(format).format(time);
    }
}
