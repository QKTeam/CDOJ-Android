package cn.edu.uestc.acm.cdoj_android;

import android.app.Activity;

import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainer;
import cn.edu.uestc.acm.cdoj_android.layout.ListContainer;
import cn.edu.uestc.acm.cdoj_android.layout.User;
import cn.edu.uestc.acm.cdoj_android.net.UserManager;


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
}
