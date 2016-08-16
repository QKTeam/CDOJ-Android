package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/7.
 */
public class Problem_old {
    int dataCount, difficulty, javaMemoryLimit, javaTimeLimit, memoryLimit, timeLimit, outputLimit, problemId, solved, tried;
    boolean isSpj, isVisible;
    String description, title, source, input, output, sampleInput, sampleOutput, hint;
    public Problem_old(JSONObject jsonObject){
        try {
            dataCount = jsonObject.getInt("dataCount");
            difficulty = jsonObject.getInt("difficulty");
            javaMemoryLimit = jsonObject.getInt("javaMemoryLimit");
            javaTimeLimit = jsonObject.getInt("javaTimeLimit");
            memoryLimit = jsonObject.getInt("memoryLimit");
            timeLimit = jsonObject.getInt("timeLimit");
            outputLimit = jsonObject.getInt("outputLimit");
            problemId = jsonObject.getInt("problemId");
            solved = jsonObject.getInt("solved");
            tried = jsonObject.getInt("tried");

            description = jsonObject.getString("description");
            title = jsonObject.getString("title");
            source = jsonObject.getString("source");
            input = jsonObject.getString("input");
            output = jsonObject.getString("output");
            sampleInput = jsonObject.getString("sampleInput");
            sampleOutput = jsonObject.getString("sampleOutput");
            hint = jsonObject.getString("hint");

            isSpj = jsonObject.getBoolean("isSpj");
            isVisible = jsonObject.getBoolean("isVisible");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
