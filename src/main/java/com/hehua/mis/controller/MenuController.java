/**
 * 
 */
package com.hehua.mis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author zhouzhihua <zhihua@afanda.com>
 * @version 1.0 create at Aug 28, 2012 11:01:51 AM
 */
@Controller
public class MenuController {

    private static final String PAGE_MENU = "home/menu";

    @RequestMapping("/menu")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView(PAGE_MENU);
        return modelAndView;
    }
}
