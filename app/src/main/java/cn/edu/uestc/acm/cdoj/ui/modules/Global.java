package cn.edu.uestc.acm.cdoj.ui.modules;

import android.app.Activity;

import cn.edu.uestc.acm.cdoj.ui.user.User;
import cn.edu.uestc.acm.cdoj.net.UserManager;


/**
 * Created by great on 2016/8/22.
 */
public class Global {
    public static Activity currentMainUIActivity;
    public static boolean isTwoPane;
    public static UserManager userManager;
    public static User user;
    public static String HTMLDATA_ARTICLE;
    public static String HTMLDATA_PROBLEM;
    public static String HTMLDATA_CONTEST;
    public static float[] mainColorMatrix;
}
