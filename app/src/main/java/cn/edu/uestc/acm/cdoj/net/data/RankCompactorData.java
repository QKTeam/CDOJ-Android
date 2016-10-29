package cn.edu.uestc.acm.cdoj.net.data;

import android.graphics.drawable.BitmapDrawable;

import java.util.List;

/**
 * Created by Grea on 2016/10/26.
 */

public class RankCompactorData {
    private String email;
    private String reallyName;
    private String nickName;
    private String name;
    private int tried;
    private int solved;
    private int rank;
    private int penalty;
    private List<RankCompactorProblemData> itemList;
    private List<RankTeamUserData> teamUsers;

    public BitmapDrawable avatar;
    public String triedString;
    public String solvedString;
    public String rankString;
    public String penaltyString;
    public String temUsersName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReallyName() {
        return reallyName;
    }

    public void setReallyName(String reallyName) {
        this.reallyName = reallyName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
        rankString = String.valueOf(rank);
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
        penaltyString = String.valueOf(penalty);
    }

    public List<RankCompactorProblemData> getItemList() {
        return itemList;
    }

    public void setItemList(List<RankCompactorProblemData> itemList) {
        this.itemList = itemList;
    }

    public List<RankTeamUserData> getTeamUsers() {
        return teamUsers;
    }

    public void setTeamUsers(List<RankTeamUserData> teamUsers) {
        this.teamUsers = teamUsers;
    }
}
