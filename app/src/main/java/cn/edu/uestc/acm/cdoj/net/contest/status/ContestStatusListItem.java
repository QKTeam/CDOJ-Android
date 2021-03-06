package cn.edu.uestc.acm.cdoj.net.contest.status;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralStatusListItem;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListItem;

/**
 * Created by 14779 on 2017-8-4.
 */

public class ContestStatusListItem extends GeneralStatusListItem {
    private int contestId;private int caseNumber;
    private String email;
    private String language;
    private int length;
    private int memoryCost;
    private String name;
    private String nickName;
    private String returnType;
    private int returnTypeId;
    private int statusId;
    private long time;
    private int timeCost;
    private String userName;

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMemoryCost() {
        return memoryCost;
    }

    public void setMemoryCost(int memoryCost) {
        this.memoryCost = memoryCost;
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

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public int getReturnTypeId() {
        return returnTypeId;
    }

    public void setReturnTypeId(int returnTypeId) {
        this.returnTypeId = returnTypeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

}
