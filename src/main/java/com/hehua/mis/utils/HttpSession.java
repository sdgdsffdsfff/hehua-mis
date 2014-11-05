package com.hehua.mis.utils;

import com.peaceful.util.StringUtils;
import com.peaceful.web.util.Http;

import java.util.Date;

/**
 * Date 14/10/22.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class HttpSession extends Session {

    private final static Session session = new Session();

    private HttpSession() {

    }

    /**
     * 获取session
     *
     * @param key
     * @return
     */
    public static String session(String key) {
        String sessionId = Http.getCookie("mis_session", "/");
        if (StringUtils.isEmpty(sessionId))
            return null;
        String value = session.get(sessionId + key);
        session(key, value);
        return value;
    }


    /**
     * 存入session
     *
     * @param key
     * @param value
     */
    public static void session(String key, String value) {
        String sessionId = Http.getCookie("mis_session", "/");
        if (StringUtils.isEmpty(sessionId))
            sessionId = new Date().getTime() + "";
        Http.addCookie("mis_session", sessionId, "/", -1, true);
        session.set(sessionId + key, value);
    }

    /**
     * 清除session
     *
     * @param key
     */
    public static void clearSession(String key) {
        String sessionId = Http.getCookie("mis_session", "/");
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(sessionId))
            return;
        session.del(sessionId + key);
    }

    ;
}
