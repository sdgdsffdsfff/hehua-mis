package com.hehua.mis.utils;

import com.peaceful.util.Util;
import org.junit.Test;

public class MenuUtilsTest {

    @Test
    public void testGetMenu() throws Exception {
        com.peaceful.util.Util.report(MenuUtils.getFirstLevelMenu("order"));
    }

    @Test
    public void test(){
        int i= 1234567890;
        Util.report(i);
    }

    @Test
    public void testLength() {
        System.out.println("何文ssd,".length());
        System.out.println(org.apache.commons.lang.math.NumberUtils.isNumber("3.4"));
    }
}