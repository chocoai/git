/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 采集器档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CjqdaSpec {

    /**
     * 采集器标识
     */
    private String cjqbs;

    /**
     * 采集器营销编码
     */
    private String cjqbh;

    /**
     * 终端标识
     */
    private String zdbs;

    /**
     * 终端逻辑地址
     */
    private String zdljdz;

    /**
     * 台区编号
     */
    private String tqbh;

    /**
     * 生产厂家标识
     */
    private String sccjbs;

    /**
     * 通讯规约代码，详见：VW_TXGYDM
     */
    private String txgydm;

    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 资产编号
     */
    private String zcbh;

    /**
     * 采集器名称
     */
    private String cjqmc;

    /**
     * 采集器标识
     *
     * @return the cjqbs
     */
    public String getCjqbs() {
        return cjqbs;
    }

    /**
     * 采集器标识
     *
     * @param cjqbs the cjqbs to set
     */
    public void setCjqbs(String cjqbs) {
        this.cjqbs = cjqbs;
    }

    /**
     * 采集器营销编码
     *
     * @return the cjqbh
     */
    public String getCjqbh() {
        return cjqbh;
    }

    /**
     * 采集器营销编码
     *
     * @param cjqbh the cjqbh to set
     */
    public void setCjqbh(String cjqbh) {
        this.cjqbh = cjqbh;
    }

    /**
     * 终端标识
     *
     * @return the zdbs
     */
    public String getZdbs() {
        return zdbs;
    }

    /**
     * 终端标识
     *
     * @param zdbs the zdbs to set
     */
    public void setZdbs(String zdbs) {
        this.zdbs = zdbs;
    }

    /**
     * 终端逻辑地址
     *
     * @return the zdljdz
     */
    public String getZdljdz() {
        return zdljdz;
    }

    /**
     * 终端逻辑地址
     *
     * @param zdljdz the zdljdz to set
     */
    public void setZdljdz(String zdljdz) {
        this.zdljdz = zdljdz;
    }

    /**
     * 台区编号
     *
     * @return the tqbh
     */
    public String getTqbh() {
        return tqbh;
    }

    /**
     * 台区编号
     *
     * @param tqbh the tqbh to set
     */
    public void setTqbh(String tqbh) {
        this.tqbh = tqbh;
    }

    /**
     * 生产厂家标识
     *
     * @return the sccjbs
     */
    public String getSccjbs() {
        return sccjbs;
    }

    /**
     * 生产厂家标识
     *
     * @param sccjbs the sccjbs to set
     */
    public void setSccjbs(String sccjbs) {
        this.sccjbs = sccjbs;
    }

    /**
     * 通讯规约代码，详见：VW_TXGYDM
     *
     * @return the txgydm
     */
    public String getTxgydm() {
        return txgydm;
    }

    /**
     * 通讯规约代码，详见：VW_TXGYDM
     *
     * @param txgydm the txgydm to set
     */
    public void setTxgydm(String txgydm) {
        this.txgydm = txgydm;
    }

    /**
     * 供电单位编码
     *
     * @return the gddwbm
     */
    public String getGddwbm() {
        return gddwbm;
    }

    /**
     * 供电单位编码
     *
     * @param gddwbm the gddwbm to set
     */
    public void setGddwbm(String gddwbm) {
        this.gddwbm = gddwbm;
    }

    /**
     * 资产编号
     *
     * @return the zcbh
     */
    public String getZcbh() {
        return zcbh;
    }

    /**
     * 资产编号
     *
     * @param zcbh the zcbh to set
     */
    public void setZcbh(String zcbh) {
        this.zcbh = zcbh;
    }

    /**
     * 采集器名称
     *
     * @return the cjqmc
     */
    public String getCjqmc() {
        return cjqmc;
    }

    /**
     * 采集器名称
     *
     * @param cjqmc the cjqmc to set
     */
    public void setCjqmc(String cjqmc) {
        this.cjqmc = cjqmc;
    }
}
