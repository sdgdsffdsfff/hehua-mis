package com.hehua.mis.item.domain;

import java.util.Date;

/**
 * Created by hewenjerry on 14-8-19.
 */
public class Material {
    private Date uts;
    private Date cts;
    private int id;
    private String name;

    public Date getUts() {
        return uts;
    }

    public void setUts(Date uts) {
        this.uts = uts;
    }

    public Date getCts() {
        return cts;
    }

    public void setCts(Date cts) {
        this.cts = cts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
