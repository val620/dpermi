package com.dpermi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by val620@126.com on 2018/8/17.
 */
public class JsonUtil {
    /**
     * json对象序列化成字符串
     */
    public static String toJsonString(Object obj){
        String jsonStr= JSON.toJSONString(obj);
        return jsonStr;
    }

    /**
     * json字符串反序列化为对象
     */
    public static Object toObject(String jsonStr){
        Object obj= JSON.parseObject(jsonStr);
        return obj;
    }

    /**
     * json字符串反序列化为Json对象
     */
    public static JSONObject toJsonObject(String jsonStr){
        JSONObject obj = JSONObject.parseObject(jsonStr);
        return obj;
    }
}
