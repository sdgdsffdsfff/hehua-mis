/**
 * 
 */
package com.hehua.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.mis.domain.Acluser;
import com.hehua.mis.service.user.AcluserManager;
import com.hehua.mis.utils.JsonResponse;
import com.hehua.mis.utils.ResponseUtils;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Oct 11, 2013 12:23:21 PM
 */
@Controller
public class PasswordController {

    @Autowired
    private AcluserManager acluserManager;

    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @RequestMapping(value = "/setting/password", method = RequestMethod.GET)
    public ModelAndView showPasswordPage() {
        ModelAndView modelAndView = new ModelAndView("/setting/password");
        Acluser gmAcluser = (Acluser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("gm_user", gmAcluser);
        return modelAndView;
    }

    @RequestMapping(value = "/setting/password", method = RequestMethod.POST)
    public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response) {

        String password = ServletRequestUtils.getStringParameter(request, "password", null);
        String newPassword = ServletRequestUtils.getStringParameter(request, "new_password", null);
        String newPasswordConfirm = ServletRequestUtils.getStringParameter(request,
                "new_password_confirm", null);

        if (StringUtils.isBlank(password)) {
            return ResponseUtils.output(response, new JsonResponse(-1, "密码不能为空"));
        }

        // 新密码不能为空
        if (StringUtils.isBlank(newPassword)) {
            return ResponseUtils.output(response, new JsonResponse(-1, "新密码不能为空"));
        }

        // 密码输入不一致
        if (!StringUtils.equals(newPassword, newPasswordConfirm)) {
            return ResponseUtils.output(response, new JsonResponse(-1, "密码输入不一致"));
        }

        Acluser gmAcluser = (Acluser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String inputEncodePassword = md5PasswordEncoder.encodePassword(password, gmAcluser.getSalt());

        if (!StringUtils.equals(inputEncodePassword, gmAcluser.getPassword())) {
            return ResponseUtils.output(response, new JsonResponse(-1, "密码错误"));
        }

        acluserManager.changePassword(gmAcluser.getId(), newPassword);

        String newEncodePassword = md5PasswordEncoder.encodePassword(newPassword, gmAcluser.getSalt());
        gmAcluser.setPassword(newEncodePassword);

        return ResponseUtils.output(response, new JsonResponse(0, "修改成功"));
    }
}
