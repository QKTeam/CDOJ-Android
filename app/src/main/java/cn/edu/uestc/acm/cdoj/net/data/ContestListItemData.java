package cn.edu.uestc.acm.cdoj.net.data;

/**
 * Created by Grea on 2016/10/27.
 */

public class ContestListItemData {
    private String typeName;
    private String title;
    private String status;
    private int type;
    private int contestId;
    private long time;
    private long length;
    private boolean isVisible;

    public String contestIdString;
    public String timeString;
    public String lengthString;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
        contestIdString = "ID:" + contestId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
