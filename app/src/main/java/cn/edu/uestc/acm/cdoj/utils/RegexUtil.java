package cn.edu.uestc.acm.cdoj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex related utils
 * Created by lagranmoon on 2017/8/21.
 */

public class RegexUtil {
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmail(String s){
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

}
