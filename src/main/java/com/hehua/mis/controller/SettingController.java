/**
 * 
 */
package com.hehua.mis.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hehua.mis.domain.Acluser;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Oct 11, 2013 12:09:22 PM
 */
@Controller
public class SettingController {

    @RequestMapping("/setting")
    public ModelAndView showSettingPaage() {
        ModelAndView modelAndView = new ModelAndView("setting/setting");
        Acluser gmAcluser = (Acluser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("gm_user", gmAcluser);
        return modelAndView;
    }
}
