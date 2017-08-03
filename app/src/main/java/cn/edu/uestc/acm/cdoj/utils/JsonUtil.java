package cn.edu.uestc.acm.cdoj.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leigu on 2017/4/20.
 * to generate json Strings
 */

public class JsonUtil {
    public static String getJsonString(String key[], Object value[]) {
        Map<String, Object> map = new HashMap<>();
        if (key != null && value != null && value.length <= key.length) {
            for (int i = 0; i < key.length; i++) {
                map.put(key[i], value[i]);
            }
        }
        return JSON.toJSONString(map);
    }

}
