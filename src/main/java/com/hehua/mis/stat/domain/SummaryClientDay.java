package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryClientDay {
    private int dt;
    private String channelid;
    private long clientcount;
    private long reglogin_clientcount;
    private long onesession_clientcount;
    private long onelog_clientcount;
    private long nouser_clientcount;
    private long order_clientcount;
    private double orderamount;

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

    public long getClientcount() {
        return clientcount;
    }

    public void setClientcount(long clientcount) {
        this.clientcount = clientcount;
    }

    public long getReglogin_clientcount() {
        return reglogin_clientcount;
    }

    public void setReglogin_clientcount(long reglogin_clientcount) {
        this.reglogin_clientcount = reglogin_clientcount;
    }

    public long getOnesession_clientcount() {
        return onesession_clientcount;
    }

    public void setOnesession_clientcount(long onesession_clientcount) {
        this.onesession_clientcount = onesession_clientcount;
    }

    public long getOnelog_clientcount() {
        return onelog_clientcount;
    }

    public void setOnelog_clientcount(long onelog_clientcount) {
        this.onelog_clientcount = onelog_clientcount;
    }

    public long getNouser_clientcount() {
        return nouser_clientcount;
    }

    public void setNouser_clientcount(long nouser_clientcount) {
        this.nouser_clientcount = nouser_clientcount;
    }

    public long getOrder_clientcount() {
        return order_clientcount;
    }

    public void setOrder_clientcount(long order_clientcount) {
        this.order_clientcount = order_clientcount;
    }

    public double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(double orderamount) {
        this.orderamount = orderamount;
    }
}
