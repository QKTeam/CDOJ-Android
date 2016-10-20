package cn.edu.uestc.acm.cdoj.tools.OJEditText.modules;


public final class ColorPlan {
    public int grammarColor,commentColor,charColor,strColor,numberColor;
    public ColorPlan( int grammarColor,int commentColor,int charColor,int strColor,int numberColor){
        this.charColor =charColor;
        this.commentColor = commentColor;
        this.strColor =strColor;
        this.grammarColor = grammarColor;
        this.numberColor = numberColor;
    }
}
