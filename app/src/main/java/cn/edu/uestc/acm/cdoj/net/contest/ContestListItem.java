package cn.edu.uestc.acm.cdoj.net.contest;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestListItem {

    /**
     * contestId : 2
     * isVisible : true
     * length : 18000000
     * status : Ended
     * time : 1375329600000
     * title : UESTC 2013 Summer Training #18 Div.2
     * type : 0
     * typeName : Public
     */

    private int contestId;
    private boolean isVisible;
    private int length;
    private String status;
    private long time;
    private String title;
    private int type;
    private String typeName;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
