package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryKpiWeek {
    private int yearweek;
    private int min_datekey;
    private int max_datekey;
    private String channelid;
    private long tracecount;
    private long anonymous_tracecount;
    private long erroranonymous_tracecount;
    private long erroranonymous_noregrequest_tracecount;
    private long anonymous_regrequest_tracecount;
    private long noanonymous_regrequest_tracecount;
    private long errorregrequest_tracecount;
    private long errorregrequest_norelogin_tracecount;
    private long regrequest_reglogin_tracecount;
    private long noregrequest_reglogin_tracecount;
    private long errorreglogin_tracecount;
    private long login_tracecount;
    private long errorlogin_tracecount;
    private long usercount;
    private long reglogin_usercount;
    private long loadcart_usercount;
    private long payinfo_usercount;
    private long order_usercount;
    private long payreturn_usercount;
    private long ordercount;
    private long payreturn_ordercount;
    private long errorpayreturn_ordercount;
    private double orderamount;
    private double payamount;
    private long clientcount;
    private long reglogin_clientcount;
    private long onesession_clientcount;
    private long onelog_clientcount;
    private long nouser_clientcount;
    private long order_clientcount;

    public int getYearweek() {
        return yearweek;
    }

    public void setYearweek(int yearweek) {
        this.yearweek = yearweek;
    }

    public int getMin_datekey() {
        return min_datekey;
    }

    public void setMin_datekey(int min_datekey) {
        this.min_datekey = min_datekey;
    }

    public int getMax_datekey() {
        return max_datekey;
    }

    public void setMax_datekey(int max_datekey) {
        this.max_datekey = max_datekey;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public long getTracecount() {
        return tracecount;
    }

    public void setTracecount(long tracecount) {
        this.tracecount = tracecount;
    }

    public long getAnonymous_tracecount() {
        return anonymous_tracecount;
    }

    public void setAnonymous_tracecount(long anonymous_tracecount) {
        this.anonymous_tracecount = anonymous_tracecount;
    }

    public long getErroranonymous_tracecount() {
        return erroranonymous_tracecount;
    }

    public void setErroranonymous_tracecount(long erroranonymous_tracecount) {
        this.erroranonymous_tracecount = erroranonymous_tracecount;
    }

    public long getErroranonymous_noregrequest_tracecount() {
        return erroranonymous_noregrequest_tracecount;
    }

    public void setErroranonymous_noregrequest_tracecount(long erroranonymous_noregrequest_tracecount) {
        this.erroranonymous_noregrequest_tracecount = erroranonymous_noregrequest_tracecount;
    }

    public long getAnonymous_regrequest_tracecount() {
        return anonymous_regrequest_tracecount;
    }

    public void setAnonymous_regrequest_tracecount(long anonymous_regrequest_tracecount) {
        this.anonymous_regrequest_tracecount = anonymous_regrequest_tracecount;
    }

    public long getNoanonymous_regrequest_tracecount() {
        return noanonymous_regrequest_tracecount;
    }

    public void setNoanonymous_regrequest_tracecount(long noanonymous_regrequest_tracecount) {
        this.noanonymous_regrequest_tracecount = noanonymous_regrequest_tracecount;
    }

    public long getErrorregrequest_tracecount() {
        return errorregrequest_tracecount;
    }

    public void setErrorregrequest_tracecount(long errorregrequest_tracecount) {
        this.errorregrequest_tracecount = errorregrequest_tracecount;
    }

    public long getErrorregrequest_norelogin_tracecount() {
        return errorregrequest_norelogin_tracecount;
    }

    public void setErrorregrequest_norelogin_tracecount(long errorregrequest_norelogin_tracecount) {
        this.errorregrequest_norelogin_tracecount = errorregrequest_norelogin_tracecount;
    }

    public long getRegrequest_reglogin_tracecount() {
        return regrequest_reglogin_tracecount;
    }

    public void setRegrequest_reglogin_tracecount(long regrequest_reglogin_tracecount) {
        this.regrequest_reglogin_tracecount = regrequest_reglogin_tracecount;
    }

    public long getNoregrequest_reglogin_tracecount() {
        return noregrequest_reglogin_tracecount;
    }

    public void setNoregrequest_reglogin_tracecount(long noregrequest_reglogin_tracecount) {
        this.noregrequest_reglogin_tracecount = noregrequest_reglogin_tracecount;
    }

    public long getErrorreglogin_tracecount() {
        return errorreglogin_tracecount;
    }

    public void setErrorreglogin_tracecount(long errorreglogin_tracecount) {
        this.errorreglogin_tracecount = errorreglogin_tracecount;
    }

    public long getLogin_tracecount() {
        return login_tracecount;
    }

    public void setLogin_tracecount(long login_tracecount) {
        this.login_tracecount = login_tracecount;
    }

    public long getErrorlogin_tracecount() {
        return errorlogin_tracecount;
    }

    public void setErrorlogin_tracecount(long errorlogin_tracecount) {
        this.errorlogin_tracecount = errorlogin_tracecount;
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
}
