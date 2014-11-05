/*
 * Copyright (c) 2014.
 * Author WangJun
 * Email  wangjuntytl@163.com
 */

/**
 *
 */
package com.hehua.mis.controller;

import com.alibaba.fastjson.JSON;
import com.hehua.framework.web.annotation.CSRFToken;
import com.hehua.framework.web.annotation.CSRFVerifyToken;
import com.hehua.framework.web.annotation.XssParamFilter;
import com.hehua.mis.utils.MisWebUtil;
import com.hehua.mis.utils.Pagination;
import com.hehua.mis.utils.Response;
import com.hehua.user.domain.PhoneVersion;
import com.hehua.user.service.PhoneVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PhoneVersionController {

    @Autowired
    PhoneVersionService phoneVersionService;
    @RequestMapping(value = "/users/version/list", method = {RequestMethod.GET})
    public ModelAndView getVersionList(Integer pageNo) {
        if (pageNo == null)
            pageNo = 1;
        ModelAndView modelAndView = new ModelAndView("users/phoneVersionList");
        Pagination pagination = new Pagination(15, pageNo);
        pagination.setCount(phoneVersionService.getPhoneVersionCount());
        List<PhoneVersion> phoneVersionList = phoneVersionService.getPhoneVersionByPage(pagination.getStartIndex(), pagination.getSize());
        modelAndView.addObject("phoneVersionList", phoneVersionList);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @RequestMapping(value = "/add/phoneVersion", method = {RequestMethod.GET})
    @CSRFToken
    public ModelAndView addPhoneVersion(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        ModelAndView model) {
        ModelAndView modelAndView = new ModelAndView("users/addPhoneVersion", model.getModel());
        MisWebUtil.getIp(httpServletRequest);
        MisWebUtil.getUrl(httpServletRequest);
        return modelAndView;
    }

    @RequestMapping(value = "/users/addPhoneVersion", method = {RequestMethod.POST})
    @CSRFVerifyToken
    @ResponseBody
    public String insertPhoneVersion(
            @XssParamFilter("version") String version,
            @XssParamFilter("channel") String channel,
            @XssParamFilter("downloadurl") String downloadurl,
            @XssParamFilter("releasenote") String releasenote,
            @RequestParam("forceupdate") int forceupdate,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        PhoneVersion phoneVersion = new PhoneVersion();
        phoneVersion.setForceupdate(forceupdate);
        phoneVersion.setVersion(version);
        phoneVersion.setDownloadurl(downloadurl);
        phoneVersion.setReleasenote(releasenote);
        phoneVersion.setChannel(channel);
        try {
            boolean retStatus = phoneVersionService.createPhoneVersion(phoneVersion);
            return retStatus ? JSON.toJSONString(new Response(1,"操作成功")) : JSON.toJSONString(new Response(0,"保存失败,数据库存在该版本信息"));
        } catch (Exception e) {
            return  JSON.toJSONString(new Response(0,"操作失败"));
        }
    }

    @RequestMapping(value = "/users/updateVersion", method = {RequestMethod.POST})
    @ResponseBody
    public String updatePhoneVersion(int id, int isForceUpdate ,HttpServletResponse response) {
        try {
            phoneVersionService.updatePhoneVersionByIdAndForceupdate(id, isForceUpdate);
            return JSON.toJSONString(new Response(1,"操作成功"));
        } catch (Exception e) {
            return  JSON.toJSONString(new Response(0,"操作失败"));
        }
    }
}
