/**
 * 
 */
package com.hehua.mis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhihua
 *
 */
@Controller
public class WelcomeController {

    @RequestMapping("/welcome")
    public ModelAndView showWelcomePage() {
        return new ModelAndView("home/welcome");
    }

}
