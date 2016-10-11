package cn.edu.uestc.acm.cdoj.ui.statusBar;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by fitzz on 16-8-23.
 */
public class FlyMeUtils {
    public static boolean isFlyMe(){
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }
}
