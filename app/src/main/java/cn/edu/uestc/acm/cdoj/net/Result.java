package cn.edu.uestc.acm.cdoj.net;

/**
 * Created by Grea on 2016/10/24.
 */

public class Result {

    public static final int DEFAULT = 0x100;
    public static final int NETNOTCONECT = 0x101;
    public static final int CONECTOVERTIME = 0x102;
    public static final int FALSE = 0x103;
    public static final int SUCCESS = 0x104;
    public static final int FINISH = 0x105;
    public static final int DATAISNULL = 0x106;
    public static final int NORESPONSE = 0x107;

    private int dataType;
    private int status;
    private Object content;
    private Object extra;

    public Result(int dataType, int status) {
        this(dataType, status, null, null);
    }
    public Result(int dataType, int status, Object extra, Object content) {
        this.dataType = dataType;
        this.status = status;
        this.content = content;
        this.extra = extra;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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
