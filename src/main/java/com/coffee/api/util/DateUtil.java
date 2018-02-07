package com.coffee.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String toStr(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
