package cn.edu.uestc.acm.cdoj;

/**
 * Created by Grea on 2016/9/30.
 */

public class TimeFormat {
    public final static int TIME = 0;
    public final static int DATA = 1;

    public static String getFormatTime(long timeLength) {
        timeLength /= 1000;
        String formatTime;
        long days = timeLength / (60 * 60 * 24);
        long hours = (timeLength % (60 * 60 * 24)) / (60 * 60);
        long minutes = (timeLength % (60 * 60)) / 60;
        long seconds = timeLength % 60;
        if (days > 0) {
            formatTime = days + ":" + hours + ":" + minutes + ":" + seconds;
        } else if (hours > 0) {
            formatTime = hours + ":" + minutes + ":" + seconds;
        } else if (minutes > 0) {
            formatTime = minutes + ":" + seconds;
        } else {
            formatTime = seconds + "";
        }
        return formatTime;
    }
}
