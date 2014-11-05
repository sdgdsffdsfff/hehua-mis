/**
 * 
 */
package com.hehua.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 28, 2012 11:01:51 AM
 */
@Controller
public class HeaderController {

    private static final String PAGE_HEADER = "home/header";

    private static final String PAGE_HOME = "home/home";

    @RequestMapping("/header")
    public ModelAndView showHomePage(HttpServletRequest request, HttpServletResponse respons) {

        ModelAndView modelAndView = new ModelAndView(PAGE_HEADER);

        return modelAndView;
    }

}
