package cn.edu.uestc.acm.cdoj.net.contest.rank;

import java.util.List;

/**
 * Created by 14779 on 2017-8-5.
 */

public class RankListItem {

    /**
     * 比赛排名列表的item
     * email : m15520771658@163.com
     * itemList : []
     * name : Falcone
     * nickName : cmdyxdz
     * penalty : 65087
     * rank : 1
     * reallyName : 刘丰瑞
     * solved : 4
     */

    private String email;
    private String name;
    private String nickName;
    private int penalty;
    private int rank;
    private String reallyName;
    private int solved;
    private List<RankListDetailItem> itemList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getReallyName() {
        return reallyName;
    }

    public void setReallyName(String reallyName) {
        this.reallyName = reallyName;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public List<RankListDetailItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RankListDetailItem> itemList) {
        this.itemList = itemList;
    }


}

