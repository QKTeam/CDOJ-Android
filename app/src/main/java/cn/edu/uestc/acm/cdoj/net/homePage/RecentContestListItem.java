package cn.edu.uestc.acm.cdoj.net.homePage;

/**
 * Created by 14779 on 2017-8-10.
 */

public class RecentContestListItem {

    /**
     * access : Private
     * id : 22822
     * link : http://acm.hdu.edu.cn/contests/contest_show.php?cid=764
     * name : 2017 Multi-University Training Contest - Team 6
     * oj : HDU
     * start_time : 1502337600000
     * week : THU
     */

    private String access;
    private int id;
    private String link;
    private String name;
    private String oj;
    private long start_time;
    private String week;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOj() {
        return oj;
    }

    public void setOj(String oj) {
        this.oj = oj;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
