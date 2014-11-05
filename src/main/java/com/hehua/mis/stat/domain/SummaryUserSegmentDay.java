package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryUserSegmentDay {
    private int dt;
    private String channelid;
    private long usercount;
    private long newreg_usercount;
    private long retention_usercount;
    private long return_usercount;
    private long buy_usercount;
    private long directbuy_usercount;
    private long buyandbuy_usercount;
    private long returnbuy_usercount;
    private double buy_payamount;
    private double directbuy_payamount;
    private double buyandbuy_payamount;
    private double returnbuy_payamount;

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

    public long getNewreg_usercount() {
        return newreg_usercount;
    }

    public void setNewreg_usercount(long newreg_usercount) {
        this.newreg_usercount = newreg_usercount;
    }

    public long getRetention_usercount() {
        return retention_usercount;
    }

    public void setRetention_usercount(long retention_usercount) {
        this.retention_usercount = retention_usercount;
    }

    public long getReturn_usercount() {
        return return_usercount;
    }

    public void setReturn_usercount(long return_usercount) {
        this.return_usercount = return_usercount;
    }

    public long getBuy_usercount() {
        return buy_usercount;
    }

    public void setBuy_usercount(long buy_usercount) {
        this.buy_usercount = buy_usercount;
    }

    public long getDirectbuy_usercount() {
        return directbuy_usercount;
    }

    public void setDirectbuy_usercount(long directbuy_usercount) {
        this.directbuy_usercount = directbuy_usercount;
    }

    public long getBuyandbuy_usercount() {
        return buyandbuy_usercount;
    }

    public void setBuyandbuy_usercount(long buyandbuy_usercount) {
        this.buyandbuy_usercount = buyandbuy_usercount;
    }

    public long getReturnbuy_usercount() {
        return returnbuy_usercount;
    }

    public void setReturnbuy_usercount(long returnbuy_usercount) {
        this.returnbuy_usercount = returnbuy_usercount;
    }

    public double getBuy_payamount() {
        return buy_payamount;
    }

    public void setBuy_payamount(double buy_payamount) {
        this.buy_payamount = buy_payamount;
    }

    public double getDirectbuy_payamount() {
        return directbuy_payamount;
    }

    public void setDirectbuy_payamount(double directbuy_payamount) {
        this.directbuy_payamount = directbuy_payamount;
    }

    public double getBuyandbuy_payamount() {
        return buyandbuy_payamount;
    }

    public void setBuyandbuy_payamount(double buyandbuy_payamount) {
        this.buyandbuy_payamount = buyandbuy_payamount;
    }

    public double getReturnbuy_payamount() {
        return returnbuy_payamount;
    }

    public void setReturnbuy_payamount(double returnbuy_payamount) {
        this.returnbuy_payamount = returnbuy_payamount;
    }
}
