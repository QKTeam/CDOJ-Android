package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/7.
 */
public class ProblemInfo {
    public int difficulty, problemId, solved, tried;
    public boolean isSpj, isVisible;
    public String source, title;
    public ProblemInfo(JSONObject jsonObject){
        try {
            difficulty = jsonObject.getInt("difficulty");
            problemId = jsonObject.getInt("problemId");
            solved = jsonObject.getInt("solved");
            tried = jsonObject.getInt("tried");

            isSpj = jsonObject.getBoolean("isSpj");
            isVisible = jsonObject.getBoolean("isVisible");

            source = jsonObject.getString("source");
            title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
