package cn.edu.uestc.acm.cdoj.net.data;

import java.util.List;

/**
 * Created by Grea on 2016/10/26.
 */

public class RankData {
    private long lastFetched;
    private List<RankProblemData> problemList;
    private List<RankCompactorData> rankList;

    public long getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(long lastFetched) {
        this.lastFetched = lastFetched;
    }

    public List<RankProblemData> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<RankProblemData> problemList) {
        this.problemList = problemList;
    }

    public List<RankCompactorData> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankCompactorData> rankList) {
        this.rankList = rankList;
    }
}
