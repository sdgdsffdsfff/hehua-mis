package com.hehua.mis.stat.domain;

/**
 * Created by huasheng on 10/3/14.
 */
public class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        if (start <= end) {
            this.start = start;
            this.end = end;
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
