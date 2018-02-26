/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model;

/**
 * 元数据对象
 *
 * @author caibenxiang
 */
public class Metadata {

    /**
     * 服务回复时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private String responseTime;

    /**
     * 服务回复校验码，用于审计数据防篡改
     */
    private String md5;

    /**
     * 服务记录分页信息
     */
    private Pagination pagination;

    /**
     * 服务回复时间，格式为yyyy-MM-dd HH:mm:ss
     * @return the responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * 服务回复时间，格式为yyyy-MM-dd HH:mm:ss
     * @param responseTime the responseTime to set
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * 服务回复校验码，用于审计数据防篡改
     * @return the md5
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 服务回复校验码，用于审计数据防篡改
     * @param md5 the md5 to set
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * 服务记录分页信息
     * @return the pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * 服务记录分页信息
     * @param pagination the pagination to set
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
