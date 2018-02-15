package cn.edu.uestc.acm.cdoj.data.source.local;

import cn.edu.uestc.acm.cdoj.data.bean.User;
import cn.edu.uestc.acm.cdoj.data.source.repo.UserDataSource;

/**
 * Created by lagranmoon on 2018/2/14.
 * 存储用户信息的本地仓库
 */

public class UserLocalDataSource implements UserDataSource {
    @Override
    public boolean login(String userName, String password) {
        return false;
    }

    @Override
    public User getUserInfo(String userName) {
        return null;
    }
}
