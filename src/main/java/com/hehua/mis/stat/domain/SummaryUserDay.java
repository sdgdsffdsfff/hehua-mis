package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryUserDay {
    private int dt;
    private String channelid;
    private long usercount;
    private long reglogin_usercount;
    private long loadcart_usercount;
    private long payinfo_usercount;
    private long order_usercount;
    private long payreturn_usercount;
    private double orderamount;
    private double payamount;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public long getUsercount() {
        return usercount;
    }

    public void setUsercount(long usercount) {
        this.usercount = usercount;
    }

    public long getReglogin_usercount() {
        return reglogin_usercount;
    }

    public void setReglogin_usercount(long reglogin_usercount) {
        this.reglogin_usercount = reglogin_usercount;
    }

    public long getLoadcart_usercount() {
        return loadcart_usercount;
    }

    public void setLoadcart_usercount(long loadcart_usercount) {
        this.loadcart_usercount = loadcart_usercount;
    }

    public long getPayinfo_usercount() {
        return payinfo_usercount;
    }

    public void setPayinfo_usercount(long payinfo_usercount) {
        this.payinfo_usercount = payinfo_usercount;
    }

    public long getOrder_usercount() {
        return order_usercount;
    }

    public void setOrder_usercount(long order_usercount) {
        this.order_usercount = order_usercount;
    }

    public long getPayreturn_usercount() {
        return payreturn_usercount;
    }

    public void setPayreturn_usercount(long payreturn_usercount) {
        this.payreturn_usercount = payreturn_usercount;
    }

    public double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(double orderamount) {
        this.orderamount = orderamount;
    }

    public double getPayamount() {
        return payamount;
    }

    public void setPayamount(double payamount) {
        this.payamount = payamount;
    }
}
