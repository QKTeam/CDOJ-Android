package cn.edu.uestc.acm.cdoj.net.data;

/**
 * Created by Grea on 2016/10/27.
 */

public class RankCompactorProblem {
    private Boolean triedAfterFrozen;
    private Boolean solved;
    private Boolean firstBlood;
    private int penalty;
    private int solvedTime;
    private int tried;

    public int solvedStatus;
    public String penaltyString;
    public String solvedTimeString;
    public String triedString;
    public String order;
    public String solvedPercent;

    public Boolean getTriedAfterFrozen() {
        return triedAfterFrozen;
    }

    public void setTriedAfterFrozen(Boolean triedAfterFrozen) {
        this.triedAfterFrozen = triedAfterFrozen;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Boolean getFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(Boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
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
        triedString = String.valueOf(tried);
    }
}
