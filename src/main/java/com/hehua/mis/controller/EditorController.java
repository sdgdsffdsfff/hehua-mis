/**
 * 
 */
package com.hehua.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import com.hehua.framework.web.util.ResponseUtils;

/**
 * @author zhihua
 *
 */
@Controller
@RequestMapping("/js/ueditor")
public class EditorController {

    @Autowired
    private UploadController uploadController;

    @RequestMapping("/editor")
    public ModelAndView showTestPage() {
        return new ModelAndView("editor");
    }

    @RequestMapping("config")
    public ModelAndView getUeditorConfig(@RequestParam("action") String action,
            HttpServletRequest request, HttpServletResponse response) {
        String host = request.getHeader(HttpHeaders.HOST);
        host = StringUtils.defaultString(host, "api.s.hehua.com");

        switch (action) {
            case "uploadimage":
                return uploadController.upload(request, response);
            default:
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("imageActionName", "uploadimage");
                jsonObject.put("imageUrl", "http://" + host + "/upload");
                jsonObject.put("imageFieldName", "upfile");
                jsonObject.put("imageMaxSize", 2048000000);
                jsonObject.put("imageAllowFiles", new String[] { ".png", ".jpg", ".jpeg", ".gif",
                        ".bmp" });
                jsonObject.put("imageUrlPrefix", "");
                ResponseUtils.output(response, jsonObject.toJSONString());
                return null;
        }

    }
}
