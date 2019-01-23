package com.neo.common.config;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public JsonData(boolean ret,String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public static JsonData success(Object object, String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData(true,Global.SUCCESS);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success(String msg) {
        return new JsonData(true,msg);
    }

    public static JsonData success() {
        return new JsonData(true,Global.SUCCESS);
    }

    public static JsonData error(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData error() {
        return new JsonData(false,Global.ERROR);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
