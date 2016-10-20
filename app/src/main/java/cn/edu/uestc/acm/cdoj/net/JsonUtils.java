package cn.edu.uestc.acm.cdoj.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                    value = getListFromJson((JSONArray) value);
                }
                map.put(key, value);
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List getListFromJson(JSONArray jsonArray) {
        ArrayList list = new ArrayList<>();
        try {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                Object temp = jsonArray.get(i);
                if (temp instanceof JSONObject){
                    list.add(getMapFromJson((JSONObject) temp));
                }
                else if (temp instanceof JSONArray){
                    list.add(getListFromJson((JSONArray) temp));
                }
                else {
                    list.add(temp);
//                    throw new RuntimeException("假设只有json对象与数组，没有基础数据类型");
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject getJsonObjectFromMap(Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        Set keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();
        String key;
        Object val;
        try {
            while (keyIterator.hasNext()){
                key = keyIterator.next();
                val = map.get(key);
                if (val instanceof List){
                    jsonObject.put(key, getJsonArrayFromList((List) val));
                }
                else {
                    jsonObject.put(key, val);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static JSONArray getJsonArrayFromList(List list){
        JSONArray jsonArray = new JSONArray();
        for (Object temp:list) {
            if (temp instanceof Map) {
                jsonArray.put(getJsonObjectFromMap((Map<String, Object>) temp));
            } else if (temp instanceof List) {
                jsonArray.put(getJsonArrayFromList((List) temp));
            } else {
                jsonArray.put(temp);
            }
        }
        return jsonArray;
    }
}
