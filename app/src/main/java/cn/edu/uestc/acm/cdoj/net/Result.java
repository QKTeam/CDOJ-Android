package cn.edu.uestc.acm.cdoj.net;

/**
 * Created by Grea on 2016/10/24.
 */

public class Result {
    private int type;
    private int status;
    private Object content;
    private Object extra;

    public Result(int type, int status) {
        this(type, status, null, null);
    }
    public Result(int type, int status, Object extra, Object content) {
        this.type = type;
        this.status = status;
        this.content = content;
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
