package com.hehua.mis.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hesheng on 14-10-2.
 */
public class MisWebUtil {
    public static final int DEFAULT_PORT = 80;
    public static final int MAX_IP_LENGTH = 15;

    private static final Logger LOGGER = LoggerFactory.getLogger(MisWebUtil.class);

    public static boolean isCrossDomain(HttpServletRequest request) {
        String host = request.getServerName().toLowerCase();
        String referer = request.getHeader("Referer");
        return referer == null || !referer.toLowerCase().startsWith("http://" + host);
    }

    /**
     * 根据HttpServletRequest获得url
     *
     * @param request 请求
     * @return url
     */
    public static String getUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String url = getDomain(request)
                + request.getContextPath() + request.getRequestURI();
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            url = url + "?" + StringEscapeUtils.escapeHtml(request.getQueryString());
        }
        return url;
    }

    public static String getDomain(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        return  request.getServerName()
                + (request.getServerPort() == DEFAULT_PORT ? "" : ":" + request.getServerPort());
    }



    /**
     * 根据HttpServletRequest获得访问ip
     *
     * @param request 请求
     * @return ip
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || "".equals(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.split(",")[0].trim().length() > MAX_IP_LENGTH) {
            return "";
        }
        return ip.split(",")[0].trim();
    }

    public static boolean writeCookie(HttpServletResponse response, String name, String value, String domain, int time) {
        try {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/");
            if (!domain.equals("localhost")) {
                cookie.setDomain(domain);
            }
            if (time > 0) {
                cookie.setMaxAge(time);
            }
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("write cookie %s:%s error, e:%s", name, value, e.getMessage()));
            return false;
        }
    }
}
