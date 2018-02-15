package cn.edu.uestc.acm.cdoj.data.source.repo;

import cn.edu.uestc.acm.cdoj.data.bean.User;

/**
 * Created by lagranmoon on 2018/2/14.
 * 定义获取用户信息的接口
 */

public interface UserDataSource {
    boolean login(String userName,String password);
    User getUserInfo(String userName);
}
