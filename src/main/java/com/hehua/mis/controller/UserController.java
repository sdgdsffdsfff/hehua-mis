/**
 *
 */
package com.hehua.mis.controller;

import com.hehua.framework.image.ImageService;
import com.hehua.framework.image.domain.Image;
import com.hehua.item.domain.Daren;
import com.hehua.item.service.DarenService;
import com.hehua.mis.service.user.UserServiceManage;
import com.hehua.mis.utils.HttpSession;
import com.hehua.mis.utils.MisConstants;
import com.hehua.mis.utils.Pagination;
import com.hehua.mis.utils.Response;
import com.hehua.user.dao.UserDAO;
import com.hehua.user.domain.User;
import com.hehua.user.model.RegisterResult;
import com.hehua.user.service.LocationManager;
import com.hehua.user.service.RegisterService;
import com.hehua.user.service.UserService;
import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.data.domain.JSONUser;
import com.peaceful.auth.exception.CreateSessionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhihua
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserDAO userDao;

    @Autowired
    @Qualifier("userServiceManage")
    private UserServiceManage userService;

    @Autowired
    @Qualifier("userService")
    private UserService userService_;

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private DarenService darenService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RegisterService registerService;


    @RequestMapping({"list"})
    public ModelAndView showUserListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("users/list");
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(40, pageNo);

        List<User> users = userDao.getAllUserByPage(pagination.getStartIndex(), pagination.getSize());
        pagination.setCount(userDao.getAllCount());
        modelAndView.addObject("users", users);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @RequestMapping(value = "updatePassPre", method = RequestMethod.GET)
    public String updatePassPre() {
        return "users/updatePassword";
    }

    @RequestMapping(value = "updatePass", method = RequestMethod.POST)
    public void updatePass(HttpServletRequest request,
                             HttpServletResponse response,
                             String password, String oldPassword) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        JSONUser user = authService.getUser(HttpSession.session(MisConstants.CURRENT_USER));
        boolean valid = authService.identificationEmail(user.email, new Md5PasswordEncoder().encodePassword(oldPassword, "woshinidaye"));
        if (valid) {
            user.setPasswordState(1);
            user.setPassword(new Md5PasswordEncoder().encodePassword(password, "woshinidaye"));
            com.peaceful.auth.data.response.Response response_ = authService.updateUser(user, false, null);
            if (response_.defineState == 1) {
                Response.jsonResponse(1, "更新密码成功，需要重新登录");
                return;
            }
            else {
                Response.jsonResponse(0, response_.detailDesc);
                return;
            }
        } else {
             Response.jsonResponse( 0, "旧密码错误");
        }


    }

    @RequestMapping("/search")
    public ModelAndView searchUser(HttpServletRequest request, HttpServletResponse response) {
        String account = ServletRequestUtils.getStringParameter(request, "account", "");
        String userID = ServletRequestUtils.getStringParameter(request, "id", "");
        String email = ServletRequestUtils.getStringParameter(request, "email", "");
        int mama_status = ServletRequestUtils.getIntParameter(request, "mama_status", 0);
        String baby_birth = ServletRequestUtils.getStringParameter(request, "baby_birth", "");
        int baby_gender = ServletRequestUtils.getIntParameter(request, "baby_gender", 0);

        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 0);
        Pagination pagination = new Pagination(40, pageNo);

        ModelAndView modelAndView = new ModelAndView("users/list");

        if (!StringUtils.isBlank(userID)) {
            pagination.setCount(userDao.getCountUserByIdAndPage(Long.parseLong(userID)));
            User u = userDao.getUserByIdAndPage(Long.parseLong(userID), pagination.getStartIndex(), pagination.getSize());
            List<User> users = new ArrayList<User>();
            users.add(u);
            modelAndView.addObject("users", users);
            modelAndView.addObject("pagination", pagination);

            return modelAndView;
        }
        if (!StringUtils.isBlank(account)) {
            pagination.setCount(userDao.getCountUserByAccountByPage(account));

            User u = userDao.getUserByAccountByPage(account, pagination.getStartIndex(), pagination.getSize());
            List<User> users = new ArrayList<User>();
            users.add(u);
            modelAndView.addObject("users", users);
            modelAndView.addObject("pagination", pagination);


            return modelAndView;
        }

        List<User> users = userDao.getAllUser();
        pagination.setCount(userDao.getAllCount());
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("addTarentoPre")
    public String addTarentoPre(HttpServletRequest request) {
        request.setAttribute("location", locationManager.getAllProvince());
        return "users/addTarento";
    }

    @RequestMapping(value = "addTarento", method = RequestMethod.POST)
    @ResponseBody
    public void addTarento(Daren daren,
                           @RequestParam(value = "password", required = true) String password,
                           @RequestParam(value = "account", required = true) String account,
                           HttpServletRequest request, HttpServletResponse response) {
        if (daren.getStage().equals("3")) {
            daren.setStage("已有宝宝");
        } else if (daren.getStage().equals("1")) {
            daren.setStage("备孕中");
        } else
            daren.setStage("怀孕中");
        User user = userService_.findUserByAccount(account);
        if (user == null) {
            RegisterResult result = registerService.registerUser(account,daren.getName(), password);
            if (result == null) {
                Response.jsonResponse(-1, "添加用户失败");
                return;
            }
        }

        Date now = new Date();
        daren.setCts(now);
        daren.setUts(now);
        daren.setUserid(user.getId());
        darenService.addDarens(daren);
        userService_.updateDarenState(daren.getUserid(), 1);
        Response.jsonResponse( 1, "添加成功");
    }

    @RequestMapping(value = "updateTarento", method = RequestMethod.POST)
    @ResponseBody
    public void updateTarento(Daren daren, HttpServletRequest request,HttpServletResponse response, String account) {
        if (daren.getStage().equals("3")) {
            daren.setStage("已有宝宝");
        } else if (daren.getStage().equals("1")) {
            daren.setStage("备孕中");
        } else
            daren.setStage("怀孕中");
        Date now = new Date();
        daren.setUts(now);
        User user = userService_.findUserByAccount(account);
        daren.setUserid(user.getId());
        darenService.updateDaren(daren);
        Response.jsonResponse( 1, "更新成功");
    }

    @RequestMapping("tarentoList")
    public String tarentoList(HttpServletRequest request) {
        request.setAttribute("darenList", darenService.findAllDarens());
        return "users/darenList";
    }

    @RequestMapping("findTarento")
    public String findTarento(HttpServletRequest request, long userid) {
        Daren daren = darenService.getDarenInfo(userid);
        request.setAttribute("location", locationManager.getAllProvince());
        request.setAttribute("daren", daren);
        Image image = imageService.getImageById(daren.getAvatarid());
        if (image != null)
            request.setAttribute("image", image.getUrl());
        request.setAttribute("account", userService_.findUserById(daren.getUserid()).getAccount());
        return "users/updateTarento";
    }

    @RequestMapping(value = "validUserId")
    @ResponseBody
    public void validUserId(String account,HttpServletResponse response) {
        User user = userService_.findUserByAccount(account);
        Daren daren = null;
        if (user != null)
            daren = darenService.getDarenInfo(user.getId());
        if (daren != null) {
            Response.jsonResponse(-1, "该达人账户已被添加");
            return;
        } else if (user != null) {
            Response.jsonResponse(1, "存在");
            return;

        }
        else
             Response.jsonResponse(1, "达人账户不存在");

    }


}
