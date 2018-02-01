package cn.edu.uestc.acm.cdoj.data.bean.user;

import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;
import cn.edu.uestc.acm.cdoj.data.bean.base.User;

/**
 * Created by lagranmoon on 2018/2/1.
 * 用户的详细信息
 */

public class UserDetailResponse extends ResposeSuccess {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
