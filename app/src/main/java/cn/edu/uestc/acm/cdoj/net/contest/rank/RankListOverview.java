package cn.edu.uestc.acm.cdoj.net.contest.rank;

import java.util.List;

/**
 * Created by 14779 on 2017-8-5.
 * 比赛排名列表以及问题被解决的次数和lastFetched的信息
 */

public class RankListOverview {
    private long lastFetched;
    private List<RankListProblemInfo> problemList;
    private List<RankListItem> rankList;

    public long getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(long lastFetched) {
        this.lastFetched = lastFetched;
    }

    public List<RankListProblemInfo> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<RankListProblemInfo> problemList) {
        this.problemList = problemList;
    }

    public List<RankListItem> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankListItem> rankList) {
        this.rankList = rankList;
    }

    public void clear(){
        lastFetched = 0;
        problemList.clear();
        rankList.clear();
    }
}
