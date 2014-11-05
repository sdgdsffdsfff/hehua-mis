package com.hehua.mis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Date 14-10-11.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class AssertUtils extends TagSupport {

    private static Logger logger = LoggerFactory.getLogger(AssertUtils.class);

    private String name;

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            if (GlobalApp.isTest() || GlobalApp.isProduct()) {
                if (name.endsWith(".js") && !name.endsWith(".min.js"))
                    out.print("<script type=\"text/javascript\" src=\"" + name.substring(0, name.length() - 3) + "." + GlobalApp.getBuildVersion() + ".js\" ></script>\n");
                else if (name.endsWith(".css") && !name.endsWith(".min.css"))
                    out.print("<link href=\"" + name.substring(0, name.length() - 4) + "." + GlobalApp.getBuildVersion() + ".css\" rel=\"stylesheet\">\n");
                else if (name.endsWith(".min.js")) {
                    out.print("<script type=\"text/javascript\" src=\"" + name + "\" ></script>\n");
                } else if (!name.endsWith(".min.css")) {
                    out.print("<link href=\"" + name + "\" rel=\"stylesheet\">\n");
                } else
                    out.print(name);
            } else {
                if (name.endsWith(".js"))
                    out.print("<script type=\"text/javascript\" src=\"" + name + "\" ></script>\n");
                else if (name.endsWith(".css"))
                    out.print("<link href=\"" + name + "\" rel=\"stylesheet\">\n");
                else
                    out.print(name);
            }

        } catch (Exception e) {
            logger.error("doStartTag:{}", e);
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                logger.error("doStartTag:{}", e);
            }
        }
        return EVAL_BODY_INCLUDE;
    }
}
