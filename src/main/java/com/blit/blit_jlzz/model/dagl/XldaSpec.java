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
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XldaSpec {

    /**
     * 管理单位编码
     */
    private String gddwbm;

    /**
     * 管理单位名称
     */
    private String gldwmc;

    /**
     * 变电站名称
     */
    private String bdzmc;

    /**
     * 线路名称
     */
    private String xlmc;

    /**
     * 线路编号
     */
    private String xlbh;

    /**
     * 线路类别代码
     */
    private String xllbdm;

    /**
     * 线路类别名称
     */
    private String xllbdm_mc;

    /**
     * 线路是否分段标志
     */
    private String fdbz;

    /**
     * 线路最大负荷（KW）
     */
    private String xlzdfh;

    /**
     * 线路已有负荷（KW）
     */
    private String xlyyfh;

    /**
     * 电压等级代码
     */
    private String dydjdm;

    /**
     * 电压等级名称
     */
    private String dydjdm_mc;

    /**
     * 运行状态代码
     */
    private String yxztdm;

    /**
     * 线路运行状态名称
     */
    private String xlyxztdm_mc;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 操作时间
     */
    private String czsj;

    /**
     * 线路线段标识
     */
    private String xlxdbs;

    /**
     * 上级线路标识
     */
    private String sjxlbs;

    /**
     * 管理所名称
     */
    private String glsmc;

    /**
     * GIS系统标识
     */
    private String gisid;

    /**
     * 农网标志
     */
    private String nwbz;

    /**
     * 环网标志
     */
    private String hwbz;

    /**
     * 线路类型代码
     */
    private String xllxdm;

    /**
     * 线路类型名称
     */
    private String xllxdm_mc;

    /**
     * 线路节点类型，开发匹配用
     */
    @JsonProperty("nodetype") 
    private String nodeType;

    /**
     * 管理单位名称
     *
     * @return the gldwmc
     */
    public String getGldwmc() {
        return gldwmc;
    }

    /**
     * 管理单位名称
     *
     * @param gldwmc the gldwmc to set
     */
    public void setGldwmc(String gldwmc) {
        this.gldwmc = gldwmc;
    }

    /**
     * 变电站名称
     *
     * @return the bdzmc
     */
    public String getBdzmc() {
        return bdzmc;
    }

    /**
     * 变电站名称
     *
     * @param bdzmc the bdzmc to set
     */
    public void setBdzmc(String bdzmc) {
        this.bdzmc = bdzmc;
    }

    /**
     * 线路名称
     *
     * @return the xlmc
     */
    public String getXlmc() {
        return xlmc;
    }

    /**
     * 线路名称
     *
     * @param xlmc the xlmc to set
     */
    public void setXlmc(String xlmc) {
        this.xlmc = xlmc;
    }

    /**
     * 线路编号
     *
     * @return the xlbh
     */
    public String getXlbh() {
        return xlbh;
    }

    /**
     * 线路编号
     *
     * @param xlbh the xlbh to set
     */
    public void setXlbh(String xlbh) {
        this.xlbh = xlbh;
    }

    /**
     * 线路类别代码
     *
     * @return the xllbdm
     */
    public String getXllbdm() {
        return xllbdm;
    }

    /**
     * 线路类别代码
     *
     * @param xllbdm the xllbdm to set
     */
    public void setXllbdm(String xllbdm) {
        this.xllbdm = xllbdm;
    }

    /**
     * 线路类别名称
     *
     * @return the xllbdm_mc
     */
    public String getXllbdm_mc() {
        return xllbdm_mc;
    }

    /**
     * 线路类别名称
     *
     * @param xllbdm_mc the xllbdm_mc to set
     */
    public void setXllbdm_mc(String xllbdm_mc) {
        this.xllbdm_mc = xllbdm_mc;
    }

    /**
     * 线路是否分段标志
     *
     * @return the fdbz
     */
    public String getFdbz() {
        return fdbz;
    }

    /**
     * 线路是否分段标志
     *
     * @param fdbz the fdbz to set
     */
    public void setFdbz(String fdbz) {
        this.fdbz = fdbz;
    }

    /**
     * 线路最大负荷（KW）
     *
     * @return the xlzdfh
     */
    public String getXlzdfh() {
        return xlzdfh;
    }

    /**
     * 线路最大负荷（KW）
     *
     * @param xlzdfh the xlzdfh to set
     */
    public void setXlzdfh(String xlzdfh) {
        this.xlzdfh = xlzdfh;
    }

    /**
     * 线路已有负荷（KW）
     *
     * @return the xlyyfh
     */
    public String getXlyyfh() {
        return xlyyfh;
    }

    /**
     * 线路已有负荷（KW）
     *
     * @param xlyyfh the xlyyfh to set
     */
    public void setXlyyfh(String xlyyfh) {
        this.xlyyfh = xlyyfh;
    }

    /**
     * 电压等级代码
     *
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级代码
     *
     * @param dydjdm the dydjdm to set
     */
    public void setDydjdm(String dydjdm) {
        this.dydjdm = dydjdm;
    }

    /**
     * 电压等级名称
     *
     * @return the dydjdm_mc
     */
    public String getDydjdm_mc() {
        return dydjdm_mc;
    }

    /**
     * 电压等级名称
     *
     * @param dydjdm_mc the dydjdm_mc to set
     */
    public void setDydjdm_mc(String dydjdm_mc) {
        this.dydjdm_mc = dydjdm_mc;
    }

    /**
     * 运行状态代码
     *
     * @return the yxztdm
     */
    public String getYxztdm() {
        return yxztdm;
    }

    /**
     * 运行状态代码
     *
     * @param yxztdm the yxztdm to set
     */
    public void setYxztdm(String yxztdm) {
        this.yxztdm = yxztdm;
    }

    /**
     * 线路运行状态名称
     *
     * @return the xlyxztdm_mc
     */
    public String getXlyxztdm_mc() {
        return xlyxztdm_mc;
    }

    /**
     * 线路运行状态名称
     *
     * @param xlyxztdm_mc the xlyxztdm_mc to set
     */
    public void setXlyxztdm_mc(String xlyxztdm_mc) {
        this.xlyxztdm_mc = xlyxztdm_mc;
    }

    /**
     * 创建时间
     *
     * @return the cjsj
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 创建时间
     *
     * @param cjsj the cjsj to set
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 操作时间
     *
     * @return the czsj
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 操作时间
     *
     * @param czsj the czsj to set
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 线路线段标识
     *
     * @return the xlxdbs
     */
    public String getXlxdbs() {
        return xlxdbs;
    }

    /**
     * 线路线段标识
     *
     * @param xlxdbs the xlxdbs to set
     */
    public void setXlxdbs(String xlxdbs) {
        this.xlxdbs = xlxdbs;
    }

    /**
     * 上级线路标识
     *
     * @return the sjxlbs
     */
    public String getSjxlbs() {
        return sjxlbs;
    }

    /**
     * 上级线路标识
     *
     * @param sjxlbs the sjxlbs to set
     */
    public void setSjxlbs(String sjxlbs) {
        this.sjxlbs = sjxlbs;
    }

    /**
     * 管理所名称
     *
     * @return the glsmc
     */
    public String getGlsmc() {
        return glsmc;
    }

    /**
     * 管理所名称
     *
     * @param glsmc the glsmc to set
     */
    public void setGlsmc(String glsmc) {
        this.glsmc = glsmc;
    }

    /**
     * GIS系统标识
     *
     * @return the gisid
     */
    public String getGisid() {
        return gisid;
    }

    /**
     * GIS系统标识
     *
     * @param gisid the gisid to set
     */
    public void setGisid(String gisid) {
        this.gisid = gisid;
    }

    /**
     * 农网标志
     *
     * @return the nwbz
     */
    public String getNwbz() {
        return nwbz;
    }

    /**
     * 农网标志
     *
     * @param nwbz the nwbz to set
     */
    public void setNwbz(String nwbz) {
        this.nwbz = nwbz;
    }

    /**
     * 环网标志
     *
     * @return the hwbz
     */
    public String getHwbz() {
        return hwbz;
    }

    /**
     * 环网标志
     *
     * @param hwbz the hwbz to set
     */
    public void setHwbz(String hwbz) {
        this.hwbz = hwbz;
    }

    /**
     * 线路类型代码
     *
     * @return the xllxdm
     */
    public String getXllxdm() {
        return xllxdm;
    }

    /**
     * 线路类型代码
     *
     * @param xllxdm the xllxdm to set
     */
    public void setXllxdm(String xllxdm) {
        this.xllxdm = xllxdm;
    }

    /**
     * 线路类型名称
     *
     * @return the xllxdm_mc
     */
    public String getXllxdm_mc() {
        return xllxdm_mc;
    }

    /**
     * 线路类型名称
     *
     * @param xllxdm_mc the xllxdm_mc to set
     */
    public void setXllxdm_mc(String xllxdm_mc) {
        this.xllxdm_mc = xllxdm_mc;
    }

    /**
     * 线路节点类型，开发匹配用
     *
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 线路节点类型，开发匹配用
     *
     * @param nodeType the nodeType to set
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * 管理单位编码
     * @return the gddwbm
     */
    public String getGddwbm() {
        return gddwbm;
    }

    /**
     * 管理单位编码
     * @param gddwbm the gddwbm to set
     */
    public void setGddwbm(String gddwbm) {
        this.gddwbm = gddwbm;
    }

}
