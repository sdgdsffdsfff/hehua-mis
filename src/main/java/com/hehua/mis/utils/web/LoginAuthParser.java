package com.hehua.mis.utils.web;

import com.hehua.framework.web.util.AesCryptUtil;
import com.hehua.framework.web.util.StringUtil;
import com.hehua.mis.utils.MisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * hewenjerry
 */
public final class LoginAuthParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthParser.class);

    private LoginAuthParser() {
    }

    public static int getLoginUidByMis(HttpServletRequest request) {
        return getUidByMisLoginCookie(request);
    }

    private static int getUidByMisLoginCookie(HttpServletRequest request) {
        Cookie c = getCookie(request, MisConstants.AUTH_COOKIE_KEY_MIS);
        String decode;
        if (c == null
                || StringUtil.isEmpty(c.getValue())
                || StringUtil.isEmpty(decode = AesCryptUtil.decrypt(c.getValue(), MisConstants.AUTH_COOKIE_KEY_MIS))) {
            return 0;
        }
        String[] info = decode.split("\\|");
        if (info.length != 2 || !StringUtil.isInteger(info[0])) {//info[0]:uid,info[1]:密码
            return 0;
        }
        int uid = Integer.parseInt(info[0]);
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(String.format("======>>>>Get uid from mis login cookie failed.cookie value is %s", c.getValue()));
        }
        return uid;
    }


    private static Cookie getCookie(HttpServletRequest request, String key) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c;
            }
        }
        return null;
    }

}
