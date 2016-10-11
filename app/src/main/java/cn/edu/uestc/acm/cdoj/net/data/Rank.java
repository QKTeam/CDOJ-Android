package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.DateTemp;
import cn.edu.uestc.acm.cdoj.net.JsonUtils;

/**
 * Created by qwe on 16-8-21.
 */
public class Rank {
    String TAG = "--------RankTag-------";

    public long lastFetched;
    ArrayList<Map> problemList;
    ArrayList<Performance> rankList = new ArrayList();

    public Rank(JSONObject jsonObject) {
        try {
            JSONObject rank = jsonObject.getJSONObject("rankList");
            lastFetched = rank.getLong("lastFetched");
            JSONArray plist = rank.getJSONArray("problemList");
            problemList = JsonUtils.getMapListFromJson(plist);
            JSONArray rlist = rank.getJSONArray("rankList");
            for (int i = 0; i < rlist.length(); i++) {
                rankList.add(new Performance(rlist.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList getProblemList(){
        return problemList;
    }
    public ArrayList<Performance> getPerformanceList(){
        return rankList;
    }
    public class Performance{
        public String email, name, nickName, realName;
        public int penalty, rank, solved, tried;
        public String penaltyString;
        ArrayList<ProblemStatus> problemStatusList = new ArrayList<>(15);
        public Performance(JSONObject jsonObject) {
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            for (int i = 0; i < itemList.length(); i++) {
                problemStatusList.add(new ProblemStatus(itemList.optJSONObject(i)));
            }
            email = jsonObject.optString("email");
            name = jsonObject.optString("name");
            nickName = jsonObject.optString("nickName");
            realName = jsonObject.optString("realName");

            penalty = jsonObject.optInt("penalty");
            penaltyString = DateTemp.format(penalty);
            rank = jsonObject.optInt("rank");
            solved = jsonObject.optInt("solved");
            tried = jsonObject.optInt("tried");

        }
        public ArrayList<ProblemStatus> getProblemStatusList(){
            return problemStatusList;
        }
        public class ProblemStatus{
            public boolean firstBlood, solved, triedAfterFrozen;
            public int penalty, solvedTime, tried;
            public ProblemStatus(JSONObject jsonObject) {
                firstBlood = jsonObject.optBoolean("firstBlood");
                solved = jsonObject.optBoolean("solved");
                triedAfterFrozen = jsonObject.optBoolean("triedAfterFrozen");

                penalty = jsonObject.optInt("penalty");
                solvedTime = jsonObject.optInt("solvedTime");
                tried = jsonObject.optInt("tried");
            }
        }
    }
}
