package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/7.
 */
public class ProblemInfo {
    public int difficulty, problemId, solved, tried;
    public boolean isSpj, isVisible;
    public String source, title;
    public ProblemInfo(JSONObject jsonObject){
        difficulty = jsonObject.optInt("difficulty");
        problemId = jsonObject.optInt("problemId");
        solved = jsonObject.optInt("solved");
        tried = jsonObject.optInt("tried");

        isSpj = jsonObject.optBoolean("isSpj");
        isVisible = jsonObject.optBoolean("isVisible");

        source = jsonObject.optString("source");
        title = jsonObject.optString("title");


    }
}
