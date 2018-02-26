/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model;

/**
 * 分页信息对象
 *
 * @author caibenxiang
 */
public class Pagination {

    /**
     * 查询页数，序号从 1 开始，低于 1 时自动调整为 1。
     */
    private int page;

    /**
     * 查询每页记录数
     */
    private int limit;

    /**
     * 总记录数
     */
    private int total;

    /**
     * 查询页数，序号从 1 开始，低于 1 时自动调整为 1。
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * 查询页数，序号从 1 开始，低于 1 时自动调整为 1。
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 查询每页记录数
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 查询每页记录数
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 总记录数
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * 总记录数
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
