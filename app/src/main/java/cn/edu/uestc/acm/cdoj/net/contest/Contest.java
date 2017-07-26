package cn.edu.uestc.acm.cdoj.net.contest;

/**
 * Created by 14779 on 2017-7-24.
 */

public class Contest {

    /**
     * contestId : 94
     * currentTime : 1500884124380
     * description :
     * endTime : 1460192400000
     * frozenTime : 3600000
     * isVisible : true
     * length : 18000000
     * startTime : 1460174400000
     * status : Ended
     * timeLeft : 0
     * title : The 14th UESTC Programming Contest Final 重现赛
     * type : 0
     * typeName : Public
     */

    private int contestId;
    private long currentTime;
    private String description;
    private long endTime;
    private int frozenTime;
    private boolean isVisible;
    private int length;
    private long startTime;
    private String status;
    private int timeLeft;
    private String title;
    private int type;
    private String typeName;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getFrozenTime() {
        return frozenTime;
    }

    public void setFrozenTime(int frozenTime) {
        this.frozenTime = frozenTime;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
