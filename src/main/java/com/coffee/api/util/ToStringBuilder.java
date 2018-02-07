/**
 * Project Name:coffee-server-api
 * File Name:ToStringBuilder.java
 * Package Name:com.coffee.api.util
 * Date:2018年2月7日下午6:28:42
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:ToStringBuilder <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:28:42 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class ToStringBuilder {
	public static String toString(Object objectInstance) {
        return getStringUsingBean(objectInstance);
    }

    private static String getStringUsingBean(Object objectInstance) {
        StringBuilder buildString = new StringBuilder("[");

        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(objectInstance.getClass())
                .getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method getMethod = propertyDescriptor.getReadMethod();
                if (getMethod != null && !"class".equals(propertyDescriptor.getName())) {

                    String filedName = propertyDescriptor.getName();

                    // 过滤字段
                    if (needContinue(filedName)) {
                        continue;
                    }

                    buildString.append(filedName).append("=");

                    // Check if there exists any parent. This check will avoid stack over flow if any.
                    if (isParent(filedName, getMethod, buildString)) {
                        continue;
                    } else {
                        Object objectReturned = getMethod.invoke(objectInstance);
                        if (objectReturned instanceof Calendar) {
                            buildString.append(getCalendarString((Calendar) objectReturned));
                        } else if (objectReturned instanceof Date) {
                            buildString.append(getDateString((Date) objectReturned));
                        } else {
                            buildString.append(objectReturned);
                        }
                        buildString.append(",");
                    }
                }
            }
        } catch (Exception e) {
        }

        // remove last ','
        if (buildString.length() > 0 && buildString.lastIndexOf(",") == buildString.length() - 1) {
            buildString = new StringBuilder(buildString.substring(0, buildString.length() - 1));
        }

        buildString.append("]");
        return buildString.toString();
    }

    private static boolean isParent(String methodName, Method method, StringBuilder buildString) {
        if ("ParentItem".equals(methodName) || "ParentRoot".equals(methodName)) {
            buildString.append(method.getDeclaringClass());
            return true;
        }
        return false;
    }

    private static String getCalendarString(Calendar calendarReturned) {
        StringBuilder buildString = new StringBuilder(13);

        buildString.append(calendarReturned.get(Calendar.YEAR));
        buildString.append("-");
        buildString.append(calendarReturned.get(Calendar.MONTH) + 1);
        buildString.append("-");
        buildString.append(calendarReturned.get(Calendar.DAY_OF_MONTH));
        buildString.append(" ");
        buildString.append(calendarReturned.get(Calendar.HOUR_OF_DAY));
        buildString.append(":");
        buildString.append(calendarReturned.get(Calendar.MINUTE));
        buildString.append(":");
        buildString.append(calendarReturned.get(Calendar.SECOND));
        buildString.append(".");
        buildString.append(calendarReturned.get(Calendar.MILLISECOND));

        return buildString.toString();
    }

    private static String getDateString(Date date) {
        Calendar calendarReturned = Calendar.getInstance();
        calendarReturned.setTime(date);
        return getCalendarString(calendarReturned);
    }

    // 不需要放入最终字符串
    private static boolean needContinue(String filedName) {
        return "typeReference".equals(filedName) || "serialVersionUID".equals(filedName);
    }
}

