package com.hehua.mis.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Date 14-10-11.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class Menu extends TagSupport {

    private String msg;

    public void setMsg(String msg){
        this.msg = msg;
    }

    public int doStartTag() {
        JspWriter out = this.pageContext.getOut();

        try {
            out.print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("对象正在处理开始标记.....");
        return EVAL_BODY_INCLUDE;
    }
    public int doAfterBody() throws JspException {
        System.out.println("处理标签体（after body）....");
        return SKIP_BODY;
    }
    public int doEndTag() throws JspException{
        System.out.println("对象正在处理结束标记.....");
        return EVAL_PAGE;
    }
}
