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
 * 电能表档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DnbdaSpec {

    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 电能表资产标识
     */
    private String dnbbs;

    /**
     * 电能表资产标识
     */
    private String dnbzcbs;

    /**
     * 电表资产编号。
     */
    private String dbzcbh;

    /**
     * 接线方式代码
     */
    private String jxfsdm;
    
    /**
     * 接线方式名称
     */
    private String jxfsdm_mc;

    /**
     * 出厂编号。
     */
    private String ccbh;

    /**
     * 精度等级。
     */
    private String jddj;

    /**
     * 额定电压。
     */
    private String eddy;

    /**
     * 额定电流。
     */
    private String eddl;

    /**
     * 电表综合倍率。
     */
    private String zhbl;

    /**
     * CT变比。
     */
    private String ct;

    /**
     * PT变比。
     */
    private String pt;

    /**
     * 装接容量分类。
     */
    private String rldj_mc;

    /**
     * 用户类型(KVA)。
     */
    private String yhlbdm_mc;

    /**
     * 通信规约。
     */
    private String dnbgy_mc;

    /**
     * 表码位数。
     */
    private String bmwsdm;

    /**
     * 安装物理位置。
     */
    private String azwz;

    /**
     * 安装日期。
     */
    private String azrq;

    /**
     * 通信地址。
     */
    private String txdz;

    /**
     * 波特率。
     */
    private String btl;

    /**
     * 通信方式。
     */
    private String txfsdm;

    /**
     * 运行状态代码。
     */
    private String yxztdm;
    
    /**
     * 运行状态名称。
     */
    private String yxztdm_mc;

    /**
     * 电表倍率
     */
    private String dbbl;

    /**
     * 创建时间。
     */
    private String cjsj;

    /**
     * 变更时间
     */
    private String czsj;

    /**
     * 运行终端标识
     */
    private String zdbs;

    /**
     * 电能表资产编号
     */
    private String dnbzch;

    /**
     * 资产名称
     */
    private String zcmc;

    /**
     * 电能表通讯规约代码，详见：VW_TXGYDM
     */
    private String txgydm;
    
    /**
     * 电能表通讯规约名称
     */
    private String txgydm_mc;

    /**
     * 主副表标志，1主表，0副表
     */
    private String zfbbz;

    /**
     * 生产厂家标识
     */
    private String sccjdm;

    /**
     * 生产厂家名称
     */
    private String sccjdm_mc;
    
    /**
     * 节点类型，开发匹配用
     */
    @JsonProperty("nodetype") 
    private String nodeType;

    /**
     * 供电单位编码
     * @return the gddwbm
     */
    public String getGddwbm() {
        return gddwbm;
    }

    /**
     * 供电单位编码
     * @param gddwbm the gddwbm to set
     */
    public void setGddwbm(String gddwbm) {
        this.gddwbm = gddwbm;
    }

    /**
     * 电能表资产标识
     * @return the dnbbs
     */
    public String getDnbbs() {
        return dnbbs;
    }

    /**
     * 电能表资产标识
     * @param dnbbs the dnbbs to set
     */
    public void setDnbbs(String dnbbs) {
        this.dnbbs = dnbbs;
    }

    /**
     * 电能表资产标识
     * @return the dnbzcbs
     */
    public String getDnbzcbs() {
        return dnbzcbs;
    }

    /**
     * 电能表资产标识
     * @param dnbzcbs the dnbzcbs to set
     */
    public void setDnbzcbs(String dnbzcbs) {
        this.dnbzcbs = dnbzcbs;
    }

    /**
     * 电表资产编号。
     * @return the dbzcbh
     */
    public String getDbzcbh() {
        return dbzcbh;
    }

    /**
     * 电表资产编号。
     * @param dbzcbh the dbzcbh to set
     */
    public void setDbzcbh(String dbzcbh) {
        this.dbzcbh = dbzcbh;
    }

    /**
     * 接线方式代码
     * @return the jxfsdm
     */
    public String getJxfsdm() {
        return jxfsdm;
    }

    /**
     * 接线方式代码
     * @param jxfsdm the jxfsdm to set
     */
    public void setJxfsdm(String jxfsdm) {
        this.jxfsdm = jxfsdm;
    }

    /**
     * 接线方式名称
     * @return the jxfsdm_mc
     */
    public String getJxfsdm_mc() {
        return jxfsdm_mc;
    }

    /**
     * 接线方式名称
     * @param jxfsdm_mc the jxfsdm_mc to set
     */
    public void setJxfsdm_mc(String jxfsdm_mc) {
        this.jxfsdm_mc = jxfsdm_mc;
    }

    /**
     * 出厂编号。
     * @return the ccbh
     */
    public String getCcbh() {
        return ccbh;
    }

    /**
     * 出厂编号。
     * @param ccbh the ccbh to set
     */
    public void setCcbh(String ccbh) {
        this.ccbh = ccbh;
    }

    /**
     * 精度等级。
     * @return the jddj
     */
    public String getJddj() {
        return jddj;
    }

    /**
     * 精度等级。
     * @param jddj the jddj to set
     */
    public void setJddj(String jddj) {
        this.jddj = jddj;
    }

    /**
     * 额定电压。
     * @return the eddy
     */
    public String getEddy() {
        return eddy;
    }

    /**
     * 额定电压。
     * @param eddy the eddy to set
     */
    public void setEddy(String eddy) {
        this.eddy = eddy;
    }

    /**
     * 额定电流。
     * @return the eddl
     */
    public String getEddl() {
        return eddl;
    }

    /**
     * 额定电流。
     * @param eddl the eddl to set
     */
    public void setEddl(String eddl) {
        this.eddl = eddl;
    }

    /**
     * 电表综合倍率。
     * @return the zhbl
     */
    public String getZhbl() {
        return zhbl;
    }

    /**
     * 电表综合倍率。
     * @param zhbl the zhbl to set
     */
    public void setZhbl(String zhbl) {
        this.zhbl = zhbl;
    }

    /**
     * CT变比。
     * @return the ct
     */
    public String getCt() {
        return ct;
    }

    /**
     * CT变比。
     * @param ct the ct to set
     */
    public void setCt(String ct) {
        this.ct = ct;
    }

    /**
     * PT变比。
     * @return the pt
     */
    public String getPt() {
        return pt;
    }

    /**
     * PT变比。
     * @param pt the pt to set
     */
    public void setPt(String pt) {
        this.pt = pt;
    }

    /**
     * 装接容量分类。
     * @return the rldj_mc
     */
    public String getRldj_mc() {
        return rldj_mc;
    }

    /**
     * 装接容量分类。
     * @param rldj_mc the rldj_mc to set
     */
    public void setRldj_mc(String rldj_mc) {
        this.rldj_mc = rldj_mc;
    }

    /**
     * 用户类型(KVA)。
     * @return the yhlbdm_mc
     */
    public String getYhlbdm_mc() {
        return yhlbdm_mc;
    }

    /**
     * 用户类型(KVA)。
     * @param yhlbdm_mc the yhlbdm_mc to set
     */
    public void setYhlbdm_mc(String yhlbdm_mc) {
        this.yhlbdm_mc = yhlbdm_mc;
    }

    /**
     * 通信规约。
     * @return the dnbgy_mc
     */
    public String getDnbgy_mc() {
        return dnbgy_mc;
    }

    /**
     * 通信规约。
     * @param dnbgy_mc the dnbgy_mc to set
     */
    public void setDnbgy_mc(String dnbgy_mc) {
        this.dnbgy_mc = dnbgy_mc;
    }

    /**
     * 表码位数。
     * @return the bmwsdm
     */
    public String getBmwsdm() {
        return bmwsdm;
    }

    /**
     * 表码位数。
     * @param bmwsdm the bmwsdm to set
     */
    public void setBmwsdm(String bmwsdm) {
        this.bmwsdm = bmwsdm;
    }

    /**
     * 安装物理位置。
     * @return the azwz
     */
    public String getAzwz() {
        return azwz;
    }

    /**
     * 安装物理位置。
     * @param azwz the azwz to set
     */
    public void setAzwz(String azwz) {
        this.azwz = azwz;
    }

    /**
     * 安装日期。
     * @return the azrq
     */
    public String getAzrq() {
        return azrq;
    }

    /**
     * 安装日期。
     * @param azrq the azrq to set
     */
    public void setAzrq(String azrq) {
        this.azrq = azrq;
    }

    /**
     * 通信地址。
     * @return the txdz
     */
    public String getTxdz() {
        return txdz;
    }

    /**
     * 通信地址。
     * @param txdz the txdz to set
     */
    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    /**
     * 波特率。
     * @return the btl
     */
    public String getBtl() {
        return btl;
    }

    /**
     * 波特率。
     * @param btl the btl to set
     */
    public void setBtl(String btl) {
        this.btl = btl;
    }

    /**
     * 通信方式。
     * @return the txfsdm
     */
    public String getTxfsdm() {
        return txfsdm;
    }

    /**
     * 通信方式。
     * @param txfsdm the txfsdm to set
     */
    public void setTxfsdm(String txfsdm) {
        this.txfsdm = txfsdm;
    }

    /**
     * 运行状态代码。
     * @return the yxztdm
     */
    public String getYxztdm() {
        return yxztdm;
    }

    /**
     * 运行状态代码。
     * @param yxztdm the yxztdm to set
     */
    public void setYxztdm(String yxztdm) {
        this.yxztdm = yxztdm;
    }

    /**
     * 运行状态名称。
     * @return the yxztdm_mc
     */
    public String getYxztdm_mc() {
        return yxztdm_mc;
    }

    /**
     * 运行状态名称。
     * @param yxztdm_mc the yxztdm_mc to set
     */
    public void setYxztdm_mc(String yxztdm_mc) {
        this.yxztdm_mc = yxztdm_mc;
    }

    /**
     * 电表倍率
     * @return the dbbl
     */
    public String getDbbl() {
        return dbbl;
    }

    /**
     * 电表倍率
     * @param dbbl the dbbl to set
     */
    public void setDbbl(String dbbl) {
        this.dbbl = dbbl;
    }

    /**
     * 创建时间。
     * @return the cjsj
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 创建时间。
     * @param cjsj the cjsj to set
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 变更时间
     * @return the czsj
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 变更时间
     * @param czsj the czsj to set
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 运行终端标识
     * @return the zdbs
     */
    public String getZdbs() {
        return zdbs;
    }

    /**
     * 运行终端标识
     * @param zdbs the zdbs to set
     */
    public void setZdbs(String zdbs) {
        this.zdbs = zdbs;
    }

    /**
     * 电能表资产编号
     * @return the dnbzch
     */
    public String getDnbzch() {
        return dnbzch;
    }

    /**
     * 电能表资产编号
     * @param dnbzch the dnbzch to set
     */
    public void setDnbzch(String dnbzch) {
        this.dnbzch = dnbzch;
    }

    /**
     * 资产名称
     * @return the zcmc
     */
    public String getZcmc() {
        return zcmc;
    }

    /**
     * 资产名称
     * @param zcmc the zcmc to set
     */
    public void setZcmc(String zcmc) {
        this.zcmc = zcmc;
    }

    /**
     * 电能表通讯规约代码，详见：VW_TXGYDM
     * @return the txgydm
     */
    public String getTxgydm() {
        return txgydm;
    }

    /**
     * 电能表通讯规约代码，详见：VW_TXGYDM
     * @param txgydm the txgydm to set
     */
    public void setTxgydm(String txgydm) {
        this.txgydm = txgydm;
    }

    /**
     * 电能表通讯规约名称
     * @return the txgydm_mc
     */
    public String getTxgydm_mc() {
        return txgydm_mc;
    }

    /**
     * 电能表通讯规约名称
     * @param txgydm_mc the txgydm_mc to set
     */
    public void setTxgydm_mc(String txgydm_mc) {
        this.txgydm_mc = txgydm_mc;
    }

    /**
     * 主副表标志，1主表，0副表
     * @return the zfbbz
     */
    public String getZfbbz() {
        return zfbbz;
    }

    /**
     * 主副表标志，1主表，0副表
     * @param zfbbz the zfbbz to set
     */
    public void setZfbbz(String zfbbz) {
        this.zfbbz = zfbbz;
    }

    /**
     * 生产厂家标识
     * @return the sccjdm
     */
    public String getSccjdm() {
        return sccjdm;
    }

    /**
     * 生产厂家标识
     * @param sccjdm the sccjdm to set
     */
    public void setSccjdm(String sccjdm) {
        this.sccjdm = sccjdm;
    }

    /**
     * 生产厂家名称
     * @return the sccjdm_mc
     */
    public String getSccjdm_mc() {
        return sccjdm_mc;
    }

    /**
     * 生产厂家名称
     * @param sccjdm_mc the sccjdm_mc to set
     */
    public void setSccjdm_mc(String sccjdm_mc) {
        this.sccjdm_mc = sccjdm_mc;
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
    
}
