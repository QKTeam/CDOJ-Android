package cn.edu.uestc.acm.cdoj.ui.modules;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistory;
import cn.edu.uestc.acm.cdoj.ui.user.User;


/**
 * Created by great on 2016/8/22.
 */
public class Global {

    public static Object tem;

    public static Activity currentMainUIActivity;
    public static boolean isTwoPane;
    public static User user;

    public static String HTMLDATA_ARTICLE;
    public static String HTMLDATA_PROBLEM;
    public static String HTMLDATA_CONTEST;

    public static float[] mainColorMatrix;
    public static BitmapDrawable defaultLogo;

    public static Bitmap rankIcon_didNothing;
    public static Bitmap rankIcon_tried;
    public static Bitmap rankIcon_solved;

    public static Bitmap rankIcon_theFirstSolved;
    public static Bitmap listFooterIcon_done;
    public static Bitmap listFooterIcon_noData;
    public static Bitmap listFooterIcon_problem;

    public static Bitmap listFooterIcon_netProblem;
    public static ArrayList<SearchHistory> problemSearchHistory;

    public static ArrayList<SearchHistory> contestSearchHistory;

    public static String filesDirPath;
}
