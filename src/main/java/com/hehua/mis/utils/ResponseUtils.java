/**
 * 
 */
package com.hehua.mis.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.hehua.commons.utils.ObjectMapperUtils;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 5, 2013 7:39:03 PM
 */
public class ResponseUtils {

    public static ModelAndView output(HttpServletResponse response, Object object) {
        String json = toJson(object);
        try {
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static ModelAndView output(HttpServletResponse response, String key, Object object) {
        String json = toJson(key, object);
        return output(response, json);

    }

    private static String toJson(String key, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, object);
        return toJson(map);
    }

    /**
     * @param object
     * @return
     */
    private static String toJson(Object object) {
        String json = null;
        if (object == null) {
            json = "";
        } else if (object instanceof String) {
            json = (String) object;
        } else {
            json = ObjectMapperUtils.toJSON(object);
        }
        return json;
    }

}
