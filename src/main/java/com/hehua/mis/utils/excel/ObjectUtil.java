package com.hehua.mis.utils.excel;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ObjectUtil {

	public static Map<String, Object> transform(final Object obj) {
		if (obj==null) {
			return null;
		}
		final Map<String, Object> propertyMap = new HashMap<String, Object>();
		ReflectionUtils.doWithFields(obj.getClass(), new FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                field.setAccessible(true);
                propertyMap.put(field.getName(), field.get(obj));
            }
        }, new FieldFilter() {
            @Override
            public boolean matches(Field field) {
                return true;
            }
        });
		
		ReflectionUtils.doWithMethods(obj.getClass(), new MethodCallback() {

            @Override
            public void doWith(Method method) throws IllegalArgumentException,
                    IllegalAccessException {
                String name = method.getName();
                name = name.substring(3);
                name = StringUtils.uncapitalize(name);
                try {
                    propertyMap.put(name, method.invoke(obj, new Object[0]));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }, new MethodFilter() {

            @Override
            public boolean matches(Method method) {
                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    return false;
                }
                if (method.getParameterTypes().length != 0) {
                    return false;
                }
                if (!method.getName().startsWith("get")) {
                    return false;
                }
                if (method.getName().equals("getClass")) {
                    return false;
                }
                return true;
            }
        });
		return propertyMap;
	}
	
	public static List<Map<String, Object>> transformList(final List<Object> objList) {
		if (objList==null) {
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(objList.size());
		for (Object obj : objList) {
			list.add(transform(obj));
		}
		return list;
	}
}
