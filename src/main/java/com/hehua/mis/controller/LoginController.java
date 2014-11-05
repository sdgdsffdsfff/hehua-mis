/**
 *
 */
package com.hehua.mis.controller;

import com.hehua.framework.web.annotation.XssParamFilter;
import com.hehua.mis.utils.HttpSession;
import com.hehua.mis.utils.MisConstants;
import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.data.domain.JSONUser;
import com.peaceful.auth.exception.CreateSessionException;
import com.peaceful.util.PasswordObject;
import com.peaceful.util.PasswordUtils;
import com.peaceful.web.util.Http;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 28, 2012 11:01:51 AM
 */
@Controller
public class LoginController {
    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPre() {
        return new ModelAndView("../../login");
    }


    @RequestMapping(value = "/gogo", method = RequestMethod.POST)
    public ModelAndView toLogin(HttpServletRequest request, @XssParamFilter String username, @XssParamFilter String password, HttpServletResponse response) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        boolean validExist = false;
        boolean validSuc = false;
        PasswordObject passwordObject = null;
        JSONUser user = authService.getUser(username);
        if (user != null) {
            validExist = true;
            passwordObject = PasswordUtils.getPasswordObject(user.updateTime, 30);
            validSuc = authService.identificationEmail(username, md5PasswordEncoder.encodePassword(password, "woshinidaye"));
        }
        if (validExist && validSuc) {
            if (user.passwordState == 0) {
                if (passwordObject.expireRemainDay < 25) {
                    authService.clearSession(username);
                    request.setAttribute("tip", "密码已过期");
                    authService.clearSession(username);
                    return new ModelAndView("../../login");
                } else {
                    HttpSession.session(MisConstants.SYSTEM_NOTICE, "请及时修改初始密码,密码将在" + (passwordObject.expireRemainDay - 25) + "天后过期");
                    HttpSession.session(MisConstants.CURRENT_USER,user.email);
                }
            } else {
                if (passwordObject.isExpire) {
                    authService.clearSession(username);
                    request.setAttribute("tip", "密码已过期");
                    return new ModelAndView("../../login");
                } else {
                    if (passwordObject.expireRemainDay < 6)
                        HttpSession.session(MisConstants.SYSTEM_NOTICE, "密码将在" + (passwordObject.expireRemainDay + 1) + "天后过期,请及时修改");
                    HttpSession.session(MisConstants.CURRENT_USER,user.email);
                }
            }

        } else if (!validExist) {
            request.setAttribute("tip", "用户名或密码错误");
            return new ModelAndView("../../login");
        } else {
            request.setAttribute("tip", "你没有权限登陆");
            return new ModelAndView("../../login");
        }
        return new ModelAndView("redirect:/home");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        String user = HttpSession.session(MisConstants.CURRENT_USER);
        authService.clearSession(user);
        HttpSession.clearSession(MisConstants.CURRENT_USER);
        Http.deleteCookie("mis_session");
        return new ModelAndView("redirect:/login.jsp");
    }


}
