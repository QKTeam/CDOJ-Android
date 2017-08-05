package cn.edu.uestc.acm.cdoj.net.contest.rank;

/**
 * Created by 14779 on 2017-8-5.
 */

public class RankListDetailItem {

    /**
     * 比赛排名内每个人对每道题的解答详情
     * firstBlood : false
     * penalty : 11906
     * solved : true
     * solvedTime : 10706000
     * tried : 1
     * triedAfterFrozen : false
     */

    private boolean firstBlood;
    private int penalty;
    private boolean solved;
    private int solvedTime;
    private int tried;
    private boolean triedAfterFrozen;

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getSolvedTime() {
        return solvedTime;
    }

    public void setSolvedTime(int solvedTime) {
        this.solvedTime = solvedTime;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    public boolean isTriedAfterFrozen() {
        return triedAfterFrozen;
    }

    public void setTriedAfterFrozen(boolean triedAfterFrozen) {
        this.triedAfterFrozen = triedAfterFrozen;
    }
}
