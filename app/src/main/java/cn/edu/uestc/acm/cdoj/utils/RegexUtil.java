package cn.edu.uestc.acm.cdoj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式相关工具类
 * Created by lagranmoon on 2017/8/21.
 */

public class RegexUtil {
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 用于判断用户输入的字符串是否为合法的邮箱格式
     * @param s 待匹配的字符串
     * @return 布尔值，表示输入是否为邮箱
     */
    public static boolean isEmail(String s){
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

}
