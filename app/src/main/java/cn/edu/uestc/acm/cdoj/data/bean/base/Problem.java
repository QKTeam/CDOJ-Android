package cn.edu.uestc.acm.cdoj.data.bean.base;

/**
 * Created by lagranmoon on 2018/2/1.
 * 题目的一些基本属性
 */

public class Problem {

    /**
     * dataCount : 3
     * description : Calculate $a+b$
     * difficulty : 1
     * hint : ####For GNU C
     * input : Two integer $a$,$b$ ($0<a,b<10$)
     * isSpj : false
     * isVisible : true
     * javaMemoryLimit : 65535
     * javaTimeLimit : 3000
     * memoryLimit : 65535
     * output : Output $a+b$
     * outputLimit : 8192
     * problemId : 1
     * sampleInput : ["1 2","2 3"]
     * sampleOutput : ["3","5"]
     * solved : 2281
     * source : Classic Problem
     * timeLimit : 1000
     * title : A+B Problem
     * tried : 2365
     */

    private int dataCount;
    private String description;
    private int difficulty;
    private String hint;
    private String input;
    private boolean isSpj;
    private boolean isVisible;
    private int javaMemoryLimit;
    private int javaTimeLimit;
    private int memoryLimit;
    private String output;
    private int outputLimit;
    private int problemId;
    private String sampleInput;
    private String sampleOutput;
    private int solved;
    private String source;
    private int timeLimit;
    private String title;
    private int tried;

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
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

    public int getJavaMemoryLimit() {
        return javaMemoryLimit;
    }

    public void setJavaMemoryLimit(int javaMemoryLimit) {
        this.javaMemoryLimit = javaMemoryLimit;
    }

    public int getJavaTimeLimit() {
        return javaTimeLimit;
    }

    public void setJavaTimeLimit(int javaTimeLimit) {
        this.javaTimeLimit = javaTimeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getOutputLimit() {
        return outputLimit;
    }

    public void setOutputLimit(int outputLimit) {
        this.outputLimit = outputLimit;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
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

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
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
