/**
 * 
 */
package com.hehua.mis.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hehua.commons.encrypt.MD5Util;
import com.hehua.framework.image.FileService;
import com.hehua.framework.image.ImageService;
import com.hehua.framework.image.domain.Image;
import com.hehua.user.domain.PhoneVersion;
import com.hehua.user.service.PhoneVersionService;

/**
 * @author zhihua
 *
 */
@Controller
public class UploadController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private PhoneVersionService phoneVersionService;

    @Autowired
    private FileService fileService;

    private static final Log logger = LogFactory.getLog(UploadController.class);

    @RequestMapping("/upload")
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        try {
            Iterator<String> names = multipartHttpServletRequest.getFileNames();
            String name = names.next();

            MultipartFile file = multipartHttpServletRequest.getFile(name);
            byte[] imageBytes = file.getBytes();

            Image image = imageService.createImage(imageBytes);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", image.getId());
            jsonObject.put("state", "SUCCESS");
            jsonObject.put("size", image.getSize());
            jsonObject.put("fid", image.getFid());
            jsonObject.put("title", image.getFid());
            jsonObject.put("url", image.getUrl());
            jsonObject.put("type", image.getFormat());
            jsonObject.put("original", file.getOriginalFilename());
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        } catch (Exception e) {
            logger.error("upload static error", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", "ERROR");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }
    }

    @RequestMapping("/upload/multi")
    public ModelAndView uploadMulti(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        try {

            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<String, MultipartFile> entry : multipartHttpServletRequest.getFileMap()
                    .entrySet()) {
                MultipartFile file = entry.getValue();
                byte[] imageBytes = file.getBytes();

                Image image = imageService.createImage(imageBytes);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("size", image.getSize());
                jsonObject.put("title", image.getFid());
                jsonObject.put("url", image.getUrl());
                jsonObject.put("fid", image.getFid());
                jsonObject.put("type", image.getFormat());
                jsonObject.put("original", file.getOriginalFilename());

                jsonArray.add(jsonObject);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", "SUCCESS");
            jsonObject.put("files", jsonArray);
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        } catch (Exception e) {
            logger.error("upload static error", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", "ERROR");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }
    }

    @RequestMapping(value = "/version/upload", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
    public ModelAndView uploadApk(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stat", "FAIL");

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        try {
            Iterator<String> names = multipartHttpServletRequest.getFileNames();
            String name = names.next();

            MultipartFile file = multipartHttpServletRequest.getFile(name);
            byte[] fileBytes = file.getBytes();
            String url = fileService.createFile(file.getOriginalFilename(), fileBytes);
            if (org.apache.commons.lang3.StringUtils.isEmpty(url)) {
                jsonObject.put("msg", "存储文件失败");
                com.hehua.framework.web.util.ResponseUtils.output(response,
                        jsonObject.toJSONString());
                return null;
            }

            jsonObject.put("url", url);
            jsonObject.put("stat", "SUCCESS");
            jsonObject.put("fileName", file.getOriginalFilename());
            response.setContentType("application/json");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());

        } catch (Exception e) {
            logger.error("upload static error", e);
            jsonObject.put("stat", "FAIL");
            jsonObject.put("msg", "系统错误");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
        }
        return null;

    }

    @RequestMapping(value = "/apk/upload", method = { RequestMethod.POST })
    public ModelAndView uploadApk(HttpServletRequest request,
            @RequestParam(value = "releaseNote", required = true) String releaseNote,
            @RequestParam(value = "passwd", required = true) String passwd,
            @RequestParam(value = "version", required = true) String version,
            @RequestParam(value = "channel", required = true) String channel,
            @RequestParam(value = "forceupdate", required = true) int forceupdate,
            HttpServletResponse response) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", false);
        if (org.apache.commons.lang3.StringUtils.isEmpty(releaseNote)) {
            jsonObject.put("msg", "版本升级内容为空");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }
        if (forceupdate > 1 || forceupdate < 0) {
            jsonObject.put("msg", "是否升级数据格式错误");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }
        if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(version)) {
            jsonObject.put("msg", "渠道名称或者版本名称为空");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }

        String str = version + channel + forceupdate + releaseNote;

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        try {
            Iterator<String> names = multipartHttpServletRequest.getFileNames();
            String name = names.next();

            MultipartFile file = multipartHttpServletRequest.getFile(name);
            byte[] fileBytes = file.getBytes();
            //str += new String(fileBytes, "UTF-8");
            logger.info("my=" + MD5Util.MD5Encrypt(str) + "  passwd=" + passwd);
            if (!org.apache.commons.lang3.StringUtils.equals(MD5Util.MD5Encrypt(str), passwd)) {
                jsonObject.put("msg", "非法请求");
                com.hehua.framework.web.util.ResponseUtils.output(response,
                        jsonObject.toJSONString());
                return null;
            }

            String url = fileService.createFile(file.getOriginalFilename(), fileBytes);
            if (org.apache.commons.lang3.StringUtils.isEmpty(url)) {
                jsonObject.put("msg", "存储文件失败");
                com.hehua.framework.web.util.ResponseUtils.output(response,
                        jsonObject.toJSONString());
                return null;
            }

            PhoneVersion phoneVersion = new PhoneVersion();
            phoneVersion.setForceupdate(forceupdate);
            phoneVersion.setVersion(version);
            phoneVersion.setChannel(channel);
            phoneVersion.setReleasenote(releaseNote);
            phoneVersion.setDownloadurl(url);
            boolean saveStatus = phoneVersionService.createPhoneVersion(phoneVersion);
            if (saveStatus) {
                jsonObject.put("status", true);
                jsonObject.put("msg", "上传成功");
                jsonObject.put("filename", file.getOriginalFilename());
            } else {
                jsonObject.put("msg", "上传文件失败,文件重复提交");
            }

            jsonObject.put("original", file.getOriginalFilename());
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        } catch (Exception e) {
            logger.error("upload static error", e);
            jsonObject.put("status", false);
            jsonObject.put("msg", "系统错误");
            com.hehua.framework.web.util.ResponseUtils.output(response, jsonObject.toJSONString());
            return null;
        }
    }
}
