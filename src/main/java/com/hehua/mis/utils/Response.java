/*
 * Copyright (c) 2014.
 * Author WangJun
 * Email  wangjuntytl@163.com
 */

package com.hehua.mis.utils;

import com.alibaba.fastjson.JSON;
import com.peaceful.web.util.Http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Date 14-8-30.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class Response implements Serializable {
    public  int code;
    public String result;
    public Response(int code,String result){
        this.code=code;
        this.result=result;
    }

    public static void jsonResponse(int code,String result){
        HttpServletResponse response = Http.getResponse();
        try {
            response.setContentType("application/x-json");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new Response(code,result)));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
