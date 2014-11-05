package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryOrderDay {
    private int dt;
    private String channelid;
    private long ordercount;
    private long payreturn_ordercount;
    private long errorpayreturn_ordercount;
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

    public long getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(long ordercount) {
        this.ordercount = ordercount;
    }

    public long getPayreturn_ordercount() {
        return payreturn_ordercount;
    }

    public void setPayreturn_ordercount(long payreturn_ordercount) {
        this.payreturn_ordercount = payreturn_ordercount;
    }

    public long getErrorpayreturn_ordercount() {
        return errorpayreturn_ordercount;
    }

    public void setErrorpayreturn_ordercount(long errorpayreturn_ordercount) {
        this.errorpayreturn_ordercount = errorpayreturn_ordercount;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
