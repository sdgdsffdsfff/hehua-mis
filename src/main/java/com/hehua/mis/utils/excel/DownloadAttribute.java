package com.hehua.mis.utils.excel;

import org.apache.commons.beanutils.PropertyUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * <p> 项目名称：renren-profile
 *
 * <p> 类名称：DownloadAttribute     
 *
 * <p> 类描述：  
 *
 * <p> 创建人：wen.he1@renren-inc.com
 *
 * <p> 创建时间：2012-5-9 上午11:42:41  
 * 
 * <p> @version    1.0
 */
public class DownloadAttribute<K,V> implements Serializable {

    private static final long serialVersionUID = -794907995989268913L;
    private String            rowName;
    private String            excelName;
    private String            excelType;
    private String            format;
    private Map<K,V>               codeMap;

    public Object getObjectValue(Object obj) {
        try {
            return PropertyUtils.getProperty(obj, this.rowName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getExcelType() {
        return this.excelType;
    }

    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }

    public String getExcelName() {
        return this.excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getRowName() {
        return this.rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setCodeMap(Map<K,V> codeMap) {
        this.codeMap = codeMap;
    }

    public  Map<K,V> getCodeMap() {
        return this.codeMap;
    }

}
