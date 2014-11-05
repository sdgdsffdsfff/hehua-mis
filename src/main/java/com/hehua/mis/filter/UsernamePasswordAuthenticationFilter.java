package com.hehua.mis.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehua.framework.web.util.CookieUtils;
import com.hehua.mis.domain.Acluser;
import com.hehua.mis.utils.MisConstants;
import com.hehua.mis.utils.MisWebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

public class UsernamePasswordAuthenticationFilter extends
        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

    public UsernamePasswordAuthenticationFilter() {
        super();
        this.setFilterProcessesUrl("/login");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request,
            HttpServletResponse response) {
        if (!StringUtils.equalsIgnoreCase("POST", request.getMethod())) {
            return false;
        }
        return super.requiresAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        Acluser acluser = (Acluser) authResult.getPrincipal();
        super.successfulAuthentication(request, response, chain, authResult);
        CookieUtils.saveCookie(response, MisConstants.AUTH_COOKIE_KEY_MIS, 123 + "|aswd",
                MisConstants.AUTH_COOKIE_EXPIRE_TIME, MisWebUtil.getDomain(request), "/", true);
    }

}
