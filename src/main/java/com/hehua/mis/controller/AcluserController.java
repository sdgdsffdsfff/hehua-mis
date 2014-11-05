/**
 * 
 */
package com.hehua.mis.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehua.mis.domain.Acluser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.mis.service.user.AcluserManager;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 5, 2013 2:31:49 PM
 */
@RequestMapping("/gm")
@Controller()
public class AcluserController {

    private static final String PAGE_CREATE = "gm/create";

    private static final String PAGE_MODIFY = "gm/modify";

    private static final String PAGE_LIST = "gm/list";

    private final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Autowired
    private AcluserManager acluserManager;

    @RequestMapping({ "/", "/list" })
    public ModelAndView showUserListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(PAGE_LIST);
        List<Acluser> aclusers = acluserManager.getUsers();
        modelAndView.addObject("gm_users", aclusers);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = { RequestMethod.GET })
    public ModelAndView showCreateUserPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(PAGE_CREATE);
    }

    @RequestMapping(value = "/modify", method = { RequestMethod.GET })
    public ModelAndView showModifyUserPage(HttpServletRequest request, HttpServletResponse response) {

        int userid = ServletRequestUtils.getIntParameter(request, "userid", 0);
        Acluser gmAcluser = acluserManager.getUser(userid);
        if (gmAcluser == null) {
            return new ModelAndView("redirect:/gm/");
        }

        ModelAndView modelAndView = new ModelAndView(PAGE_MODIFY);
        modelAndView.addObject("gm_user", gmAcluser);
        return modelAndView;
    }

    private ModelAndView showErrorPage(String errorMsg) {
        ModelAndView modelAndView = new ModelAndView("/error/error");
        modelAndView.addObject("errorMsg", errorMsg);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) {
        // TODO 添加密码验证
        // TODO 添加验证，防止xss
        String username = ServletRequestUtils.getStringParameter(request, "username", "");
        String nickname = ServletRequestUtils.getStringParameter(request, "nickname", "");
        String email = ServletRequestUtils.getStringParameter(request, "email", "");
        String role = ServletRequestUtils.getStringParameter(request, "role", "");
        String rawPassword = ServletRequestUtils.getStringParameter(request, "password", "");
        //        String adminPassword = ServletRequestUtils.getStringParameter(request, "adminPassword", "");

        if (StringUtils.isBlank(username)) {
            return showErrorPage("用户名不能为空");
        }

        if (StringUtils.isBlank(nickname)) {
            return showErrorPage("昵称不能为空");
        }

        if (StringUtils.isBlank(email)) {
            return showErrorPage("邮件不能为空");
        }

        if (StringUtils.isBlank(role)) {
            return showErrorPage("邮件不能为空");
        }

        if (StringUtils.isBlank(rawPassword)) {
            return showErrorPage("密码不能为空");
        }

        String salt = RandomStringUtils.randomAlphanumeric(8);
        String password = passwordEncoder.encodePassword(rawPassword, salt);

        Acluser acluser = new Acluser();
        acluser.setUsername(username);
        acluser.setPassword(password);
        acluser.setNickname(nickname);
        acluser.setEmail(email);
        acluser.setSalt(salt);
        acluser.setRole(role);
        acluser.setEnabled(false);
        acluser.setCreatedTime(new Date());
        acluserManager.createUser(acluser);
        ModelAndView modelAndView = new ModelAndView("redirect:/gm/");
        return modelAndView;
    }

    @RequestMapping(value = "/modify", method = { RequestMethod.POST })
    public ModelAndView modifyUser(HttpServletRequest request, HttpServletResponse response) {

        int userid = ServletRequestUtils.getIntParameter(request, "userid", 0);
        String password = ServletRequestUtils.getStringParameter(request, "password", "");
        String role = ServletRequestUtils.getStringParameter(request, "role", "");

        if (userid <= 0) {
            return showErrorPage("用户id错误，用户不存在，操作失败");
        }

        Acluser acluser = acluserManager.getUser(userid);
        if (acluser == null) {
            return showErrorPage("用户id错误，用户不存在，操作失败");
        }

        if (StringUtils.isNotBlank(password)) {
            acluserManager.changePassword(userid, password);
        }

        if (acluser.getRole() != role) {
            acluserManager.changeRole(userid, role);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/gm/");
        return modelAndView;
    }

    @RequestMapping(value = "/active", method = { RequestMethod.POST })
    public ModelAndView activeUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        acluserManager.activeUser(userId);
        ResponseUtils.output(response, new JsonResponse());
        return null;
    }

    @RequestMapping(value = "/unactive", method = { RequestMethod.POST })
    public ModelAndView unactiveUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        acluserManager.unactiveUser(userId);
        ResponseUtils.output(response, new JsonResponse());
        return null;
    }

    public static void main(String[] args) {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        String encodePassword = passwordEncoder.encodePassword("user2", "YLOghjAA");
        System.out.println(encodePassword);
    }
}
