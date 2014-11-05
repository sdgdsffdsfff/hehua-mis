package com.hehua.mis.utils;

/**
 * Date 14-10-14.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class TldUtils {

    public static  String split(String src, String holder,int index){
        if (src == null)
            return "";
        String[] res = src.split(holder);
        if ((res.length-1)<index){
            return "";
        }
        return res[index];
    }
}
