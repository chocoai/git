/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 系统用户实体类
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpec {

    /**
     * 系统用户标识
     */
    private String xtyhbs;

    /**
     * 操作员编号
     */
    private String czybh;

    /**
     * 供电单位编码
     */
    private String gddwbh;

    /**
     * 部门编号
     */
    private String bmbh;

    /**
     * 业务人员姓名
     */
    private String ywryxm;

    /**
     * 系统用户标识
     *
     * @return the xtyhbs
     */
    public String getXtyhbs() {
        return xtyhbs;
    }

    /**
     * 系统用户标识
     *
     * @param xtyhbs the xtyhbs to set
     */
    public void setXtyhbs(String xtyhbs) {
        this.xtyhbs = xtyhbs;
    }

    /**
     * 操作员编号
     *
     * @return the czybh
     */
    public String getCzybh() {
        return czybh;
    }

    /**
     * 操作员编号
     *
     * @param czybh the czybh to set
     */
    public void setCzybh(String czybh) {
        this.czybh = czybh;
    }

    /**
     * 供电单位编码
     *
     * @return the gddwbh
     */
    public String getGddwbh() {
        return gddwbh;
    }

    /**
     * 供电单位编码
     *
     * @param gddwbh the gddwbh to set
     */
    public void setGddwbh(String gddwbh) {
        this.gddwbh = gddwbh;
    }

    /**
     * 部门编号
     *
     * @return the bmbh
     */
    public String getBmbh() {
        return bmbh;
    }

    /**
     * 部门编号
     *
     * @param bmbh the bmbh to set
     */
    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    /**
     * 业务人员姓名
     *
     * @return the ywryxm
     */
    public String getYwryxm() {
        return ywryxm;
    }

    /**
     * 业务人员姓名
     *
     * @param ywryxm the ywryxm to set
     */
    public void setYwryxm(String ywryxm) {
        this.ywryxm = ywryxm;
    }

}
