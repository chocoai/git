/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 变压器档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ByqdaSpec {

    /**
     * 主键，实时库用
     */
    private String keyid;

    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 供电单位名称
     */
    private String gddwbm_mc;

    /**
     * 运行变压器标识
     */
    private String zbbs;

    /**
     * 变压器名称
     */
    private String byqmc;

    /**
     * 变压器资产编号
     */
    private String byqzcbh;

    /**
     * 变电站编号
     */
    private String bdzbh;

    /**
     * 变电站名称
     */
    private String bdzbs;

    /**
     * 变电站地址
     */
    private String bdzdz;

    /**
     * 计量点编号
     */
    private String jldbh;

    /**
     * 计量点类型
     */
    private String jldlx;

    /**
     * 计量点类型编码
     */
    private String jldlxdm;

    /**
     * 计量点类型名称
     */
    private String jldlxmc;

    /**
     * 运行状态代码
     */
    private String yxztdm;

    /**
     * 运行状态名称
     */
    private String yxztdm_mc;

    /**
     * 额定容量
     */
    private String edrl;

    /**
     * 数据创建时间
     */
    private String cjsj;

    /**
     * 数据变更时间
     */
    private String czsj;

    /**
     * 高压额定电压，详见：VW_DYDJDM
     */
    private String gyeddy;

    /**
     * 高压额定电压名称
     */
    private String gyeddy_mc;

    /**
     * 中压额定电压，详见：VW_DYDJDM
     */
    private String zyeddy;

    /**
     * 中压额定电压名称
     */
    private String zyeddy_mc;

    /**
     * 低压额定电压，详见：VW_DYDJDM
     */
    private String dyeddy;

    /**
     * 低压额定电压名称
     */
    private String dyeddy_mc;

    /**
     * 生产厂家
     */
    private String cjmc;

    /**
     * 设备型号代码
     */
    private String sbxhdm;

    /**
     * 设备型号名称
     */
    private String sbxhdm_mc;

    /**
     * 运行变压器标识
     */
    private String yxbyqbs;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 台区标识
     */
    private String tqbs;

    /**
     * 设备类型代码，详见：VW_BYQLX
     */
    private String sblxdm;

    /**
     * 设备类型名称
     */
    private String sblxdm_mc;

    /**
     * 公变专变标志，详见：VW_GBZBBZ
     */
    private String gbzbbz;

    /**
     * 公变专变标志名称
     */
    private String gbzbbz_mc;

    /**
     * GIS系统标识，在GIS系统中的ID号，用来与GIS系统集成
     */
    private String gisid;

    /**
     * GPS纬度
     */
    private String gpswd;

    /**
     * GPS经度
     */
    private String gpsjd;

    /**
     * 设备的出厂编号
     */
    private String ccbh;

    /**
     * 电源组号
     */
    private String dyzh;

    /**
     * 冷却方式代码
     */
    private String lqfsdm;

    /**
     * 冷却方式名称
     */
    private String lqfsdm_mc;

    /**
     * 电气主接线方式
     */
    private String dqzjxfs;

    /**
     * 主键，实时库用
     * @return the keyid
     */
    public String getKeyid() {
        return keyid;
    }

    /**
     * 主键，实时库用
     * @param keyid the keyid to set
     */
    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

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
     * 运行变压器标识
     * @return the zbbs
     */
    public String getZbbs() {
        return zbbs;
    }

    /**
     * 运行变压器标识
     * @param zbbs the zbbs to set
     */
    public void setZbbs(String zbbs) {
        this.zbbs = zbbs;
    }

    /**
     * 变压器名称
     * @return the byqmc
     */
    public String getByqmc() {
        return byqmc;
    }

    /**
     * 变压器名称
     * @param byqmc the byqmc to set
     */
    public void setByqmc(String byqmc) {
        this.byqmc = byqmc;
    }

    /**
     * 变压器资产编号
     * @return the byqzcbh
     */
    public String getByqzcbh() {
        return byqzcbh;
    }

    /**
     * 变压器资产编号
     * @param byqzcbh the byqzcbh to set
     */
    public void setByqzcbh(String byqzcbh) {
        this.byqzcbh = byqzcbh;
    }

    /**
     * 变电站编号
     * @return the bdzbh
     */
    public String getBdzbh() {
        return bdzbh;
    }

    /**
     * 变电站编号
     * @param bdzbh the bdzbh to set
     */
    public void setBdzbh(String bdzbh) {
        this.bdzbh = bdzbh;
    }

    /**
     * 变电站名称
     * @return the bdzbs
     */
    public String getBdzbs() {
        return bdzbs;
    }

    /**
     * 变电站名称
     * @param bdzbs the bdzbs to set
     */
    public void setBdzbs(String bdzbs) {
        this.bdzbs = bdzbs;
    }

    /**
     * 变电站地址
     * @return the bdzdz
     */
    public String getBdzdz() {
        return bdzdz;
    }

    /**
     * 变电站地址
     * @param bdzdz the bdzdz to set
     */
    public void setBdzdz(String bdzdz) {
        this.bdzdz = bdzdz;
    }

    /**
     * 计量点编号
     * @return the jldbh
     */
    public String getJldbh() {
        return jldbh;
    }

    /**
     * 计量点编号
     * @param jldbh the jldbh to set
     */
    public void setJldbh(String jldbh) {
        this.jldbh = jldbh;
    }

    /**
     * 计量点类型
     * @return the jldlx
     */
    public String getJldlx() {
        return jldlx;
    }

    /**
     * 计量点类型
     * @param jldlx the jldlx to set
     */
    public void setJldlx(String jldlx) {
        this.jldlx = jldlx;
    }

    /**
     * 计量点类型编码
     * @return the jldlxdm
     */
    public String getJldlxdm() {
        return jldlxdm;
    }

    /**
     * 计量点类型编码
     * @param jldlxdm the jldlxdm to set
     */
    public void setJldlxdm(String jldlxdm) {
        this.jldlxdm = jldlxdm;
    }

    /**
     * 计量点类型名称
     * @return the jldlxmc
     */
    public String getJldlxmc() {
        return jldlxmc;
    }

    /**
     * 计量点类型名称
     * @param jldlxmc the jldlxmc to set
     */
    public void setJldlxmc(String jldlxmc) {
        this.jldlxmc = jldlxmc;
    }

    /**
     * 运行状态代码
     * @return the yxztdm
     */
    public String getYxztdm() {
        return yxztdm;
    }

    /**
     * 运行状态代码
     * @param yxztdm the yxztdm to set
     */
    public void setYxztdm(String yxztdm) {
        this.yxztdm = yxztdm;
    }

    /**
     * 运行状态名称
     * @return the yxztdm_mc
     */
    public String getYxztdm_mc() {
        return yxztdm_mc;
    }

    /**
     * 运行状态名称
     * @param yxztdm_mc the yxztdm_mc to set
     */
    public void setYxztdm_mc(String yxztdm_mc) {
        this.yxztdm_mc = yxztdm_mc;
    }

    /**
     * 额定容量
     * @return the edrl
     */
    public String getEdrl() {
        return edrl;
    }

    /**
     * 额定容量
     * @param edrl the edrl to set
     */
    public void setEdrl(String edrl) {
        this.edrl = edrl;
    }

    /**
     * 数据创建时间
     * @return the cjsj
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 数据创建时间
     * @param cjsj the cjsj to set
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 数据变更时间
     * @return the czsj
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 数据变更时间
     * @param czsj the czsj to set
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 高压额定电压，详见：VW_DYDJDM
     * @return the gyeddy
     */
    public String getGyeddy() {
        return gyeddy;
    }

    /**
     * 高压额定电压，详见：VW_DYDJDM
     * @param gyeddy the gyeddy to set
     */
    public void setGyeddy(String gyeddy) {
        this.gyeddy = gyeddy;
    }

    /**
     * 高压额定电压名称
     * @return the gyeddy_mc
     */
    public String getGyeddy_mc() {
        return gyeddy_mc;
    }

    /**
     * 高压额定电压名称
     * @param gyeddy_mc the gyeddy_mc to set
     */
    public void setGyeddy_mc(String gyeddy_mc) {
        this.gyeddy_mc = gyeddy_mc;
    }

    /**
     * 中压额定电压，详见：VW_DYDJDM
     * @return the zyeddy
     */
    public String getZyeddy() {
        return zyeddy;
    }

    /**
     * 中压额定电压，详见：VW_DYDJDM
     * @param zyeddy the zyeddy to set
     */
    public void setZyeddy(String zyeddy) {
        this.zyeddy = zyeddy;
    }

    /**
     * 中压额定电压名称
     * @return the zyeddy_mc
     */
    public String getZyeddy_mc() {
        return zyeddy_mc;
    }

    /**
     * 中压额定电压名称
     * @param zyeddy_mc the zyeddy_mc to set
     */
    public void setZyeddy_mc(String zyeddy_mc) {
        this.zyeddy_mc = zyeddy_mc;
    }

    /**
     * 低压额定电压，详见：VW_DYDJDM
     * @return the dyeddy
     */
    public String getDyeddy() {
        return dyeddy;
    }

    /**
     * 低压额定电压，详见：VW_DYDJDM
     * @param dyeddy the dyeddy to set
     */
    public void setDyeddy(String dyeddy) {
        this.dyeddy = dyeddy;
    }

    /**
     * 低压额定电压名称
     * @return the dyeddy_mc
     */
    public String getDyeddy_mc() {
        return dyeddy_mc;
    }

    /**
     * 低压额定电压名称
     * @param dyeddy_mc the dyeddy_mc to set
     */
    public void setDyeddy_mc(String dyeddy_mc) {
        this.dyeddy_mc = dyeddy_mc;
    }

    /**
     * 生产厂家
     * @return the cjmc
     */
    public String getCjmc() {
        return cjmc;
    }

    /**
     * 生产厂家
     * @param cjmc the cjmc to set
     */
    public void setCjmc(String cjmc) {
        this.cjmc = cjmc;
    }

    /**
     * 设备型号代码
     * @return the sbxhdm
     */
    public String getSbxhdm() {
        return sbxhdm;
    }

    /**
     * 设备型号代码
     * @param sbxhdm the sbxhdm to set
     */
    public void setSbxhdm(String sbxhdm) {
        this.sbxhdm = sbxhdm;
    }

    /**
     * 设备型号名称
     * @return the sbxhdm_mc
     */
    public String getSbxhdm_mc() {
        return sbxhdm_mc;
    }

    /**
     * 设备型号名称
     * @param sbxhdm_mc the sbxhdm_mc to set
     */
    public void setSbxhdm_mc(String sbxhdm_mc) {
        this.sbxhdm_mc = sbxhdm_mc;
    }

    /**
     * 运行变压器标识
     * @return the yxbyqbs
     */
    public String getYxbyqbs() {
        return yxbyqbs;
    }

    /**
     * 运行变压器标识
     * @param yxbyqbs the yxbyqbs to set
     */
    public void setYxbyqbs(String yxbyqbs) {
        this.yxbyqbs = yxbyqbs;
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
     * 台区标识
     * @return the tqbs
     */
    public String getTqbs() {
        return tqbs;
    }

    /**
     * 台区标识
     * @param tqbs the tqbs to set
     */
    public void setTqbs(String tqbs) {
        this.tqbs = tqbs;
    }

    /**
     * 设备类型代码，详见：VW_BYQLX
     * @return the sblxdm
     */
    public String getSblxdm() {
        return sblxdm;
    }

    /**
     * 设备类型代码，详见：VW_BYQLX
     * @param sblxdm the sblxdm to set
     */
    public void setSblxdm(String sblxdm) {
        this.sblxdm = sblxdm;
    }

    /**
     * 设备类型名称
     * @return the sblxdm_mc
     */
    public String getSblxdm_mc() {
        return sblxdm_mc;
    }

    /**
     * 设备类型名称
     * @param sblxdm_mc the sblxdm_mc to set
     */
    public void setSblxdm_mc(String sblxdm_mc) {
        this.sblxdm_mc = sblxdm_mc;
    }

    /**
     * 公变专变标志，详见：VW_GBZBBZ
     * @return the gbzbbz
     */
    public String getGbzbbz() {
        return gbzbbz;
    }

    /**
     * 公变专变标志，详见：VW_GBZBBZ
     * @param gbzbbz the gbzbbz to set
     */
    public void setGbzbbz(String gbzbbz) {
        this.gbzbbz = gbzbbz;
    }

    /**
     * 公变专变标志名称
     * @return the gbzbbz_mc
     */
    public String getGbzbbz_mc() {
        return gbzbbz_mc;
    }

    /**
     * 公变专变标志名称
     * @param gbzbbz_mc the gbzbbz_mc to set
     */
    public void setGbzbbz_mc(String gbzbbz_mc) {
        this.gbzbbz_mc = gbzbbz_mc;
    }

    /**
     * GIS系统标识，在GIS系统中的ID号，用来与GIS系统集成
     * @return the gisid
     */
    public String getGisid() {
        return gisid;
    }

    /**
     * GIS系统标识，在GIS系统中的ID号，用来与GIS系统集成
     * @param gisid the gisid to set
     */
    public void setGisid(String gisid) {
        this.gisid = gisid;
    }

    /**
     * GPS纬度
     * @return the gpswd
     */
    public String getGpswd() {
        return gpswd;
    }

    /**
     * GPS纬度
     * @param gpswd the gpswd to set
     */
    public void setGpswd(String gpswd) {
        this.gpswd = gpswd;
    }

    /**
     * GPS经度
     * @return the gpsjd
     */
    public String getGpsjd() {
        return gpsjd;
    }

    /**
     * GPS经度
     * @param gpsjd the gpsjd to set
     */
    public void setGpsjd(String gpsjd) {
        this.gpsjd = gpsjd;
    }

    /**
     * 设备的出厂编号
     * @return the ccbh
     */
    public String getCcbh() {
        return ccbh;
    }

    /**
     * 设备的出厂编号
     * @param ccbh the ccbh to set
     */
    public void setCcbh(String ccbh) {
        this.ccbh = ccbh;
    }

    /**
     * 电源组号
     * @return the dyzh
     */
    public String getDyzh() {
        return dyzh;
    }

    /**
     * 电源组号
     * @param dyzh the dyzh to set
     */
    public void setDyzh(String dyzh) {
        this.dyzh = dyzh;
    }

    /**
     * 冷却方式代码
     * @return the lqfsdm
     */
    public String getLqfsdm() {
        return lqfsdm;
    }

    /**
     * 冷却方式代码
     * @param lqfsdm the lqfsdm to set
     */
    public void setLqfsdm(String lqfsdm) {
        this.lqfsdm = lqfsdm;
    }

    /**
     * 冷却方式名称
     * @return the lqfsdm_mc
     */
    public String getLqfsdm_mc() {
        return lqfsdm_mc;
    }

    /**
     * 冷却方式名称
     * @param lqfsdm_mc the lqfsdm_mc to set
     */
    public void setLqfsdm_mc(String lqfsdm_mc) {
        this.lqfsdm_mc = lqfsdm_mc;
    }

    /**
     * 电气主接线方式
     * @return the dqzjxfs
     */
    public String getDqzjxfs() {
        return dqzjxfs;
    }

    /**
     * 电气主接线方式
     * @param dqzjxfs the dqzjxfs to set
     */
    public void setDqzjxfs(String dqzjxfs) {
        this.dqzjxfs = dqzjxfs;
    }
}
