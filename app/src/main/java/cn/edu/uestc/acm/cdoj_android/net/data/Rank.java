package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.net.DateTemp;

/**
 * Created by qwe on 16-8-21.
 */
public class Rank {
    public boolean result = false;
    public long lastFecthed;
    ArrayList<ProblemInfo> problemInfoList = new ArrayList<>(15);
    ArrayList<RankList> rankList = new ArrayList();

    public Rank(String json) {
        if (json == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getString("result").equals("success");
            JSONObject rank = jsonObject.getJSONObject("rankList");
            lastFecthed = rank.getLong("lastFecthed");
            JSONArray plist = rank.getJSONArray("problemList");
            for (int i = 0; i< plist.length(); i ++) {
                problemInfoList.add(new ProblemInfo(plist.getJSONObject(i)));
            }
            JSONArray rlist = rank.getJSONArray("rankList");
            for (int i = 0; i < rlist.length(); i++) {
                rankList.add(new RankList(rlist.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<ProblemInfo> getProblemInfoList(){
        return problemInfoList;
    }
    public ArrayList<RankList> getRankList(){
        return rankList;
    }
    class RankList{
        public String email, name, nickName, realName;
        public int penalty, rank, solved, tried;
        public String penaltyString;
        ArrayList<ProblemStatus> problemStutasList = new ArrayList<>(15);
        public RankList(JSONObject jsonObject) {
            JSONArray itemList = jsonObject.optJSONArray("itemList");
            for (int i = 0; i < itemList.length(); i++) {
                problemStutasList.add(new ProblemStatus(itemList.optJSONObject(i)));
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
            return problemStutasList;
        }
        class ProblemStatus{
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
