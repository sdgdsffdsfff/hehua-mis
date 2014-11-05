package com.hehua.mis.utils.web;

import com.hehua.framework.web.antispam.csrf.CSRFTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hesheng on 14-10-2.
 */
public class MisCSRFTokenManager extends CSRFTokenManager<ModelAndView> {

    private static final Logger logger = LoggerFactory.getLogger(MisCSRFTokenManager.class);

    @Override
    public boolean appendCSRFToken(String csrfToken, ModelAndView modelAndView) {
        try {
            modelAndView.addObject(CSRF_TOKEN_NAME, csrfToken);
            return true;
        } catch (Exception e) {
            logger.warn("Set CSRFToken {} to modelAndView error", csrfToken);
            return false;
        }
    }

    @Override
    public int getLoginUserId(HttpServletRequest request) {
        return 111;
        //return LoginAuthParser.getLoginUidByMis(request);
    }
}
