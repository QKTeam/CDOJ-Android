package cn.edu.uestc.acm.cdoj.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by qwe on 16-10-10.
 */

public class JsonUtils {
    public static Map getMapFromJson(JSONObject jsonObject){
        Map map = new HashMap<String, Object>();
        try {
            Iterator<String> keyIterator = jsonObject.keys();
            String key;
            Object value;
            while (keyIterator.hasNext()){
                key = keyIterator.next();
                value = jsonObject.get(key);
                if (value instanceof JSONObject){
                    value = getMapFromJson((JSONObject) value);
                }
                else if(value instanceof JSONArray){
                    value = getMapListFromJson((JSONArray) value);
                }
                map.put(key, value);
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Map<String, Object>> getMapListFromJson(JSONArray jsonArray){
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        try {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                list.add(getMapFromJson(jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
