package cn.edu.uestc.acm.cdoj.ui.modules;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistory;
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
    public static Bitmap listFooterIcon_done;
    public static Bitmap listFooterIcon_noData;
    public static Bitmap listFooterIcon_problem;
    public static ArrayList<SearchHistory> problemSearchHistory;
    public static ArrayList<SearchHistory> contestSearchHistory;
}
