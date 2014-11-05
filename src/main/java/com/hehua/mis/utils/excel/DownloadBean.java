/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.hehua.mis.utils.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p> 项目名称：renren-profile
 *
 * <p> 类名称：DownloadBean     
 *
 * <p> 类描述： 下载配置文件类  
 *
 * <p> 创建人：wen.he1@renren-inc.com
 *
 * <p> 创建时间：2012-5-9 上午11:43:07  
 * 
 * <p> @version    1.0
 */
public class DownloadBean<E> implements Serializable {

    private static final long serialVersionUID = -794907995989268913L;
    private String            id;
    private String            name;
    private String            content;
    private List<E>              attributes       = new ArrayList<E>();

    public List<E> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<E> attributes) {
        this.attributes = attributes;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
