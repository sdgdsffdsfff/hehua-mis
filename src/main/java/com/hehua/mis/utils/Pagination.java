package com.hehua.mis.utils;

/**
 * User: hewenjerry
 * Date: 11-7-8
 * Time: 上午10:39
 * 列表分页信息
 */
public class Pagination {
    private static final int DEFAULTSIZE = 15;
    private static final int HEADNO = 1;

    public Pagination() {
        this(DEFAULTSIZE, HEADNO);
    }

    public Pagination(int size, int no) {
        setSize(size);
        setNo(no);
    }

    private int count;
    private int size;
    private int no;

    /**
     * 元素总数
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count > 0 ? count : 0;
    }

    /**
     * 单页中元素数量
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : DEFAULTSIZE;
    }

    /**
     * 当前页码
     *
     * @return
     */
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no > 1 ? no : HEADNO;
    }

    /**
     * 起始索引位置
     *
     * @return
     */
    public int getStartIndex() {
        int index = size * (no - 1);
        return index >= 0 ? index : 0;
    }

    /**
     * 结束索引位置
     *
     * @return
     */
    public int getEndIndex() {
        int index = size * no;
        return index <= count ? index : count;
    }

    /**
     * 首页码
     *
     * @return
     */
    public int getHeadNo() {
        return HEADNO;
    }

    /**
     * 到首页
     *
     * @return
     */
    public boolean isHeadNo() {
        return no <= getHeadNo();
    }

    /**
     * 尾页码
     *
     * @return
     */
    public int getEndNo() {
        return getPageCount();
    }

    /**
     * 到尾页
     *
     * @return
     */
    public boolean isEndNo() {
        return no >= getEndNo();
    }

    /**
     * 上一页码
     *
     * @return
     */
    public int getPreNo() {
        return isHeadNo() ? getHeadNo() : no - 1;
    }

    /**
     * 下一页码
     *
     * @return
     */
    public int getNextNo() {
        return isEndNo() ? getEndNo() : no + 1;
    }

    /**
     * 页数
     *
     * @return
     */
    public int getPageCount() {
        return count % size == 0 ? count / size : count / size + 1;
    }
}
