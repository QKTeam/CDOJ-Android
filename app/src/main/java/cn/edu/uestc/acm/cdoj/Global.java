package cn.edu.uestc.acm.cdoj;

import android.app.Activity;

import cn.edu.uestc.acm.cdoj.layout.DetailsContainer;
import cn.edu.uestc.acm.cdoj.layout.ListContainer;
import cn.edu.uestc.acm.cdoj.layout.User;
import cn.edu.uestc.acm.cdoj.net.UserManager;


/**
 * Created by great on 2016/8/22.
 */
public class Global {
    public static Activity currentMainActivity;
    public static NetContent netContent;
    public static UserManager userManager;
    public static User user;
    public static boolean isTwoPane;
    public static DetailsContainer detailsContainer;
    public static ListContainer listContainer;
    public static String HTMLDATA_ARTICLE;
    public static String HTMLDATA_PROBLEM;
    public static String HTMLDATA_CONTEST;

}
