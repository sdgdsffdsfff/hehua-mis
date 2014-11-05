package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 9/28/14.
 */
public class SummaryTraceDay {
    private int dt;
    private String channelid;
    private String version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
}
