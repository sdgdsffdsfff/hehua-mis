package com.hehua.mis.service.unit;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hewenjerry on 14-8-24.
 */
public class Test {
    public static void main(String[] args) {
        Date now = new Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 2);
        System.out.println(calendar.getTime());
    }
}
