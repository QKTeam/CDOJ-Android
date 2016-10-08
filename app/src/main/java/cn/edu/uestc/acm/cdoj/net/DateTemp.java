package cn.edu.uestc.acm.cdoj.net;

/**
 * Created by qwe on 16-8-23.
 */
public class DateTemp {
    public static String format(long time){
        long day = time / (24*60*60*1000);
        time %= 24*60*60*1000;
        long h = time / (60*60*1000);
        time %= 60*60*1000;
        long m = time / (60*1000);
        time %= 60*1000;
        long s = time /1000;
        return (day >0?day + " days ":"") + h + ":" + (m < 10?"0"+m:m) + ":" + (s < 10?"0"+s:s);
    }
}
