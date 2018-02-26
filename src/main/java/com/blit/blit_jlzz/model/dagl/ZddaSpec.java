/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户终端档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZddaSpec {

    /**
     * 供电单位编码
     */
    private String gddwbm;
    
    /**
     * 供电单位名称
     */
    private String gddwbm_mc;

    
    /**
     * 终端标识
     */
    private String zdbs;
    
    /**
     * 终端地址
     */
    private String zddz;
    
    /**
     * 终端资产编号
     */
    private String zcbh;
    
    /**
     * 通信规约代码，详见：VW_TXGYDM
     */
    private String txgydm;
    
    /**
     * 通信规约
     */
    private String txgydm_mc;
    
    /**
     * 终端生产厂家标识
     */
    private String zdsccjdm;
    
    /**
     * 终端生产厂家名称
     */
    private String zdsccjdm_mc;
    
    /**
     * 终端设备型号
     */
    private String zdsbxh;
    
    /**
     * 通信方式
     */
    private String txfs;
    
    /**
     * 出厂日期
     */
    private String ccrq;
    
    /**
     * 安装时间
     */
    private String azsj;
    
    /**
     * 终端类型代码
     */
    private String zdlxdm;
    
    /**
     * 终端类型名称
     */
    private String zdlxdm_mc;
    
    /**
     *
     * 运行状态，01运行；02停用；03拆除。
     */
    private String yxztdm;
    
    /**
     * 终端运行状态名称
     */
    private String yxztdm_mc;
    
    /**
     * 终端名称
     */
    private String zdmc;
    
    /**
     * 低压集中器属性维护：1 I型集中器，2 II型集中器，0 其他
     */
    private String zdlxsx;
    
    /**
     * 低压集中器属性维护名称
     */
    private String zdlxsx_mc;
    
    /**
     * 用户编号
     */
    private String yhbh;
    
    /**
     * 用户名称
     */
    private String yhmc;
    
    /**
     * 终端是否在线
     */
    private Boolean sfzx;
    
    /**
     * 节点类型，开发匹配用
     */
    @JsonProperty("nodetype") 
    private String nodeType;

    /**
     * 供电单位
     * @return the gddwbm
     */
    public String getGddwbm() {
        return gddwbm;
    }
    
    /**
     * 供电单位
     * @param gddwbm the gddwbm to set
     */
    public void setGddwbm(String gddwbm) {
        this.gddwbm = gddwbm;
    }

    /**
     * 终端标识
     * @return the zdbs
     */
    public String getZdbs() {
        return zdbs;
    }

    /**
     * 终端标识
     * @param zdbs the zdbs to set
     */
    public void setZdbs(String zdbs) {
        this.zdbs = zdbs;
    }

    /**
     * 终端地址
     * @return the zddz
     */
    public String getZddz() {
        return zddz;
    }

    /**
     * 终端地址
     * @param zddz the zddz to set
     */
    public void setZddz(String zddz) {
        this.zddz = zddz;
    }

    /**
     * 终端资产编号
     * @return the zcbh
     */
    public String getZcbh() {
        return zcbh;
    }

    /**
     * 终端资产编号
     * @param zcbh the zcbh to set
     */
    public void setZcbh(String zcbh) {
        this.zcbh = zcbh;
    }

    /**
     * 通信规约代码，详见：VW_TXGYDM
     * @return the txgydm
     */
    public String getTxgydm() {
        return txgydm;
    }

    /**
     * 通信规约代码，详见：VW_TXGYDM
     * @param txgydm the txgydm to set
     */
    public void setTxgydm(String txgydm) {
        this.txgydm = txgydm;
    }

    /**
     * 通信规约
     * @return the txgydm_mc
     */
    public String getTxgydm_mc() {
        return txgydm_mc;
    }

    /**
     * 通信规约
     * @param txgydm_mc the txgydm_mc to set
     */
    public void setTxgydm_mc(String txgydm_mc) {
        this.txgydm_mc = txgydm_mc;
    }

    /**
     * 终端生产厂家标识
     * @return the zdsccjdm
     */
    public String getZdsccjdm() {
        return zdsccjdm;
    }

    /**
     * 终端生产厂家标识
     * @param zdsccjdm the zdsccjdm to set
     */
    public void setZdsccjdm(String zdsccjdm) {
        this.zdsccjdm = zdsccjdm;
    }

    /**
     * 终端生产厂家名称
     * @return the zdsccjdm_mc
     */
    public String getZdsccjdm_mc() {
        return zdsccjdm_mc;
    }

    /**
     * 终端生产厂家名称
     * @param zdsccjdm_mc the zdsccjdm_mc to set
     */
    public void setZdsccjdm_mc(String zdsccjdm_mc) {
        this.zdsccjdm_mc = zdsccjdm_mc;
    }

    /**
     * 终端设备型号
     * @return the zdsbxh
     */
    public String getZdsbxh() {
        return zdsbxh;
    }

    /**
     * 终端设备型号
     * @param zdsbxh the zdsbxh to set
     */
    public void setZdsbxh(String zdsbxh) {
        this.zdsbxh = zdsbxh;
    }

    /**
     * 通信方式
     * @return the txfs
     */
    public String getTxfs() {
        return txfs;
    }

    /**
     * 通信方式
     * @param txfs the txfs to set
     */
    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    /**
     * 出厂日期
     * @return the ccrq
     */
    public String getCcrq() {
        return ccrq;
    }

    /**
     * 出厂日期
     * @param ccrq the ccrq to set
     */
    public void setCcrq(String ccrq) {
        this.ccrq = ccrq;
    }

    /**
     * 安装时间
     * @return the azsj
     */
    public String getAzsj() {
        return azsj;
    }

    /**
     * 安装时间
     * @param azsj the azsj to set
     */
    public void setAzsj(String azsj) {
        this.azsj = azsj;
    }

    /**
     * 终端类型代码
     * @return the zdlxdm
     */
    public String getZdlxdm() {
        return zdlxdm;
    }

    /**
     * 终端类型代码
     * @param zdlxdm the zdlxdm to set
     */
    public void setZdlxdm(String zdlxdm) {
        this.zdlxdm = zdlxdm;
    }

    /**
     * 终端类型名称
     * @return the zdlxdm_mc
     */
    public String getZdlxdm_mc() {
        return zdlxdm_mc;
    }

    /**
     * 终端类型名称
     * @param zdlxdm_mc the zdlxdm_mc to set
     */
    public void setZdlxdm_mc(String zdlxdm_mc) {
        this.zdlxdm_mc = zdlxdm_mc;
    }

    /**
     * 运行状态，01运行；02停用；03拆除。
     * @return the yxztdm
     */
    public String getYxztdm() {
        return yxztdm;
    }

    /**
     * 运行状态，01运行；02停用；03拆除。
     * @param yxztdm the yxztdm to set
     */
    public void setYxztdm(String yxztdm) {
        this.yxztdm = yxztdm;
    }

    /**
     * 终端运行状态名称
     * @return the yxztdm_mc
     */
    public String getYxztdm_mc() {
        return yxztdm_mc;
    }

    /**
     * 终端运行状态名称
     * @param yxztdm_mc the yxztdm_mc to set
     */
    public void setYxztdm_mc(String yxztdm_mc) {
        this.yxztdm_mc = yxztdm_mc;
    }

    /**
     * 终端名称
     * @return the zdmc
     */
    public String getZdmc() {
        return zdmc;
    }

    /**
     * 终端名称
     * @param zdmc the zdmc to set
     */
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    /**
     * 低压集中器属性维护：1 I型集中器，2 II型集中器，0 其他
     * @return the zdlxsx
     */
    public String getZdlxsx() {
        return zdlxsx;
    }

    /**
     * 低压集中器属性维护：1 I型集中器，2 II型集中器，0 其他
     * @param zdlxsx the zdlxsx to set
     */
    public void setZdlxsx(String zdlxsx) {
        this.zdlxsx = zdlxsx;
    }

    /**
     * 低压集中器属性维护名称
     * @return the zdlxsx_mc
     */
    public String getZdlxsx_mc() {
        return zdlxsx_mc;
    }

    /**
     * 低压集中器属性维护名称
     * @param zdlxsx_mc the zdlxsx_mc to set
     */
    public void setZdlxsx_mc(String zdlxsx_mc) {
        this.zdlxsx_mc = zdlxsx_mc;
    }

    /**
     * 用户编号
     * @return the yhbh
     */
    public String getYhbh() {
        return yhbh;
    }

    /**
     * 用户编号
     * @param yhbh the yhbh to set
     */
    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    /**
     * 用户名称
     * @return the yhmc
     */
    public String getYhmc() {
        return yhmc;
    }

    /**
     * 用户名称
     * @param yhmc the yhmc to set
     */
    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    /**
     * 节点类型，开发匹配用
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 节点类型，开发匹配用
     * @param nodeType the nodeType to set
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * 供电单位名称
     * @return the gddwbm_mc
     */
    public String getGddwbm_mc() {
        return gddwbm_mc;
    }

    /**
     * 供电单位名称
     * @param gddwbm_mc the gddwbm_mc to set
     */
    public void setGddwbm_mc(String gddwbm_mc) {
        this.gddwbm_mc = gddwbm_mc;
    }

    /**
     * 终端是否在线
     * @return the sfzx
     */
    public Boolean getSfzx() {
        return sfzx;
    }

    /**
     * 终端是否在线
     * @param sfzx the sfzx to set
     */
    public void setSfzx(Boolean sfzx) {
        this.sfzx = sfzx;
    }
}
