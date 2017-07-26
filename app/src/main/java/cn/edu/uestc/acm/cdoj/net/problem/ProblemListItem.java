package cn.edu.uestc.acm.cdoj.net.problem;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemListItem {

    /**
     * difficulty : 1
     * isSpj : false
     * isVisible : true
     * problemId : 1
     * solved : 2281
     * source : Classic Problem
     * title : A+B Problem
     * tried : 2365
     */

    private int difficulty;
    private boolean isSpj;
    private boolean isVisible;
    private int problemId;
    private int solved;
    private String source;
    private String title;
    private int tried;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isIsSpj() {
        return isSpj;
    }

    public void setIsSpj(boolean isSpj) {
        this.isSpj = isSpj;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
