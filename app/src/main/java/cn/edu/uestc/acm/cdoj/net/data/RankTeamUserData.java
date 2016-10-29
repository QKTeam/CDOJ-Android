package cn.edu.uestc.acm.cdoj.net.data;

/**
 * Created by Grea on 2016/10/29.
 */

public class RankTeamUserData {
    private String email;
    private String userName;
    private String nickName;
    private String name;
    private boolean allow;
    private int userId;
    private int teamUserId;
    private int teamId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTeamUserId() {
        return teamUserId;
    }

    public void setTeamUserId(int teamUserId) {
        this.teamUserId = teamUserId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
