package com.hehua.mis.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hesheng on 14-9-25.
 */
public class GlobleExceptionResolver implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(GlobleExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception exception) {
        logException(httpServletRequest, httpServletResponse, handler, exception);
        return new ModelAndView("error/500");
    }

    private void logException(HttpServletRequest request, HttpServletResponse response,
                              Object handler, Exception exception) {

       logger.error("mis internal ops", exception);
    }
}
