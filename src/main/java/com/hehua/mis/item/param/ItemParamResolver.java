package com.hehua.mis.item.param;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by hewenjerry on 14-9-4.
 */
public class ItemParamResolver implements HandlerMethodArgumentResolver {
    private static final Log logger = LogFactory.getLog(ItemParamResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ItemDTOI.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String objName = parameter.getParameterName() + ".";

        if (logger.isInfoEnabled()) {
            logger.info(objName + "paramterName=" + webRequest.getParameterNames());
        }
//        Object o = BeanUtils.instantiate(parameter.getParameterType());
//        StringBuffer tmp;
//        String[] val;
//        Field[] frr = parameter.getParameterType().getDeclaredFields();
//        for (Iterator<String> itr = webRequest.getParameterNames(); itr.hasNext();) {
//            tmp = new StringBuffer(itr.next());
//            if (tmp.indexOf(objName) < 0)
//                continue;
//            for (int i = 0; i < frr.length; i++) {
//                frr[i].setAccessible(true);
//                if (tmp.toString().equals(objName + frr[i].getName())) {
//                        val = webRequest.getParameterValues(tmp.toString());
//                        frr[i].set(o, val[0]);
//                    }
//                }
//            }
        return null;
    }
}
