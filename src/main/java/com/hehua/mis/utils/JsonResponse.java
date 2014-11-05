/**
 * 
 */
package com.hehua.mis.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 17, 2013 6:20:25 PM
 */
public class JsonResponse {

    private  int code;

    private  String message;

    private final Map<String, Object> data;

    /**
     * @param code
     * @param message
     */
    public JsonResponse(int code, String message) {
        super();
        this.code = code;
        this.message = message;
        this.data = new HashMap<>();
    }

    public void setMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param data
     */
    public JsonResponse(Map<String, Object> data) {
        super();
        this.code = 0;
        this.message = "";
        this.data = data;
    }

    /**
     *
     */
    public JsonResponse() {
        super();
        this.code = 0;
        this.message = "";
        this.data = new HashMap<>();
    }

    public JsonResponse data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
