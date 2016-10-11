package cn.edu.uestc.acm.cdoj.ui.modules;

import android.app.Activity;
import android.graphics.Bitmap;

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
    public static Bitmap didNothingIcon;
    public static Bitmap triedIcon;
    public static Bitmap solvedIcon;
    public static Bitmap theFirstSolvedIcon;
    public static Bitmap listFootericon_done;
    public static Bitmap listFootericon_noData;
    public static Bitmap listFootericon_problem;
}
