package cn.edu.uestc.acm.cdoj.data.bean.user;

import java.util.List;

import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;
import cn.edu.uestc.acm.cdoj.data.bean.base.User;

/**
 * Created by lagranmoon on 2018/2/1.
 * 根据给定信息显示用户列表
 */

public class UserListResponse extends ResposeSuccess{

    /**
     * list : [{"email":"449540179@qq.com","lastLogin":1461128296000,"motto":"吃惊","nickName":"前排出售咸鱼神功，练至大成可变身咸鱼王","school":"UESTC","solved":345,"tried":349,"type":0,"userId":2961,"userName":"skACM"},{"email":"muziriyun@qq.com","lastLogin":1444380317000,"motto":"岳蛤君，神兄贵，超牛逼，不解释","nickName":"Galgame理论捣毁中心","school":"Google","solved":294,"tried":296,"type":0,"userId":2,"userName":"UESTC_Izayoi"}]
     * pageInfo : {"countPerPage":20,"currentPage":1,"displayDistance":2,"totalItems":6736,"totalPages":337}
     * result : success
     */


    private List<User> list;


    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

}
