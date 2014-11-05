package com.hehua.mis.filter;

import com.hehua.mis.utils.HttpSession;
import com.hehua.mis.utils.MisConstants;
import com.peaceful.web.util.Http;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date 14-10-8.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class UserLoginCheckFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request_ = (HttpServletRequest) request;
        HttpServletResponse response_ = (HttpServletResponse) response;
        String current_user = HttpSession.session(MisConstants.CURRENT_USER);
        String url = request_.getRequestURI();
        if (StringUtils.equals("/login.jsp", url) || url.indexOf("/apk") != -1 || StringUtils.equals("/login", url) || url.indexOf(".css") != -1 || url.indexOf(".js") != -1 || url.indexOf("/gogo") != -1 || url.indexOf("/images/") != -1) {
            chain.doFilter(request, response);
            return;
        }
        if (current_user == null) {
            response_.sendRedirect("/login");
        } else {
            Http.setCurrentUser(current_user);
            chain.doFilter(request, response);
            return;
        }


    }

    @Override
    public void destroy() {

    }
}
