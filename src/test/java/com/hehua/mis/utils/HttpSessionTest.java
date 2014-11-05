package com.hehua.mis.utils;

import com.peaceful.util.Util;
import org.junit.Before;
import org.junit.Test;

public class HttpSessionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testEncode() throws Exception {

    }

    @Test
    public void testDecode() throws Exception {

    }

    @Test
    public void testBuildKey() throws Exception {

    }

    @Test
    public void get() {
        Util.report(HttpSession.session("123"));
    }

    @Test
    public void set() {
        HttpSession.session("123","hello");
    }
}