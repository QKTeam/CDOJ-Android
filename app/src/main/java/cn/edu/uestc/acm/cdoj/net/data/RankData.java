package cn.edu.uestc.acm.cdoj.net.data;

import java.util.List;

/**
 * Created by Grea on 2016/10/26.
 */

public class RankData {
    private long lastFetched;
    private List<RankProblem> problemList;
    private List<RankCompactor> rankList;

    public long getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(long lastFetched) {
        this.lastFetched = lastFetched;
    }

    public List<RankProblem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<RankProblem> problemList) {
        this.problemList = problemList;
    }

    public List<RankCompactor> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankCompactor> rankList) {
        this.rankList = rankList;
    }
}
