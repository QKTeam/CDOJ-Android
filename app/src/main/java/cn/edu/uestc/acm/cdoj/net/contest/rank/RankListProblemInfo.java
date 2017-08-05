package cn.edu.uestc.acm.cdoj.net.contest.rank;

/**
 * Created by 14779 on 2017-8-5.
 */

public class RankListProblemInfo {

    /**比赛中问题被solved和tried的信息
     * solved : 1
     * title : 1296
     * tried : 73
     */

    private int solved;
    private String title;
    private int tried;

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }
}
