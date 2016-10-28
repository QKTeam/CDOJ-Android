package cn.edu.uestc.acm.cdoj.net.data;

import android.graphics.drawable.BitmapDrawable;

import java.util.List;

/**
 * Created by Grea on 2016/10/26.
 */

public class RankCompactor {
    private String email;
    private String reallyName;
    private String nickName;
    private String name;
    private int tried;
    private int solved;
    private int rank;
    private int penalty;
    private List<RankCompactorProblem> itemList;

    public BitmapDrawable avatar;
    public String triedString;
    public String solvedString;
    public String rankString;
    public String penaltyString;

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
        triedString = String.valueOf(tried);
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
        solvedString = String.valueOf(solved);
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

    public List<RankCompactorProblem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RankCompactorProblem> itemList) {
        this.itemList = itemList;
    }
}
