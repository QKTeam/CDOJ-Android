package cn.edu.uestc.acm.cdoj.net;

import java.io.FileOutputStream;

/**
 * Created by qwe on 16-8-17.
 */
public class TestTool {
    public static void writeToFile(String s){
        try {
            new FileOutputStream("/sdcard/log.txt").write(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
