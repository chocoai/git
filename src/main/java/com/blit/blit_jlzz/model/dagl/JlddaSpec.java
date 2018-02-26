/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JlddaSpec {
    
    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 供电单位
     */
    private String gddwbm_mc;

    /**
     * 用户名称
     */
    private String yhmc;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 计量点编号
     */
    private String jldbh;

    /**
     * 计量点名称
     */
    private String jldmc;

    /**
     * 计量点类型
     */
    private String jldlxdm;
    
    /**
     * 计量点类型名称
     */
    private String jldlxdm_mc;

    /**
     * 变压器资产编号
     */
    private String byqzcbh;

    /**
     * 电表资产编号
     */
    private String zcbh;

    /**
     * 变电站名称
     */
    private String bdzmc;

    /**
     * 变电站编号
     */
    private String bdzbh;

    /**
     * 线路名称
     */
    private String xlmc;

    /**
     * 线路编号
     */
    private String xlbh;

    /**
     * 电压等级代码
     */
    private String dydjdm;
    
    /**
     * 电压等级名称
     */
    private String dydjdm_mc;

    /**
     * 电源组号
     */
    private String dyzh;

    /**
     * 电源编号
     */
    private String dybh;

    /**
     * 开关编号
     */
    private String kgbh;

    /**
     * 出厂编号
     */
    private String ccbh;

    /**
     * 接线方向
     */
    private String jxfzmc;

    /**
     * 计量装置分类
     */
    private String jlzzfldm;
    
    /**
     * 计量装置分类名称
     */
    private String jlzzfldm_mc;

    /**
     * 用电类别编码
     */
    private String ydlbdm;
    
    /**
     * 用电类别名称
     */
    private String ydlbdm_mc;

    /**
     * 计量点状态代码
     */
    private String jldztdm;
    
    /**
     * 计量点状态代码名称
     */
    private String jldztdm_mc;

    /**
     * PT变比代码
     */
    private String ptbbdm;
    
    /**
     * PT变比
     */
    private String pt;

    /**
     * CT变比代码
     */
    private String ctbbdm;
    
    /**
     * CT变比
     */
    private String ct;

    /**
     * 主副表
     */
    private String zfb;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 变更时间
     */
    private String czsj;

    /**
     * 生产厂家标识
     */
    private String sccjbs;
    
    /**
     * 接线方式名称
     */
    private String jxfsmc;
    
    /**
     * 通信方式名称
     */
    private String txfsmc;
    
    /**
     * 设备型号名称
     */
    private String sbxhmc;
    
    /**
     * 计量方式代码
     */
    private String jlfsdm;
    
    /**
     * 计量方式名称
     */
    private String jlfsdm_mc;
   
    /**
     * 行业分类代码，详见：VW_HYFLDM
     */
    private String hyfldm;
    
    /**
     * 行业分类名称
     */
    private String hyfldm_mc;
    
    /**
     * 分时计费标志，{0否；1是},是否执行峰谷标志：是、否，当此标志为否时，即使用户安装分时表，执行的电价也是分时电价，计费时仍按照基准时段的单价计费
     */
    private String fsjfbz;
    
    /**
     * 计量点类别代码，详见：VW_JLDLBDM
     */
    private String jldlbdm;
    
    /**
     * 计量点类别名称
     */
    private String jldlbdm_mc;
    
    /**
     * 计量点用途代码
     */
    private String jldytdm;
    
    /**
     * 计量点用途名称
     */
    private String jldytdm_mc;
    
    /**
     * 测量点号
     */
    private String cldh;

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
     * 供电单位
     * @return the gddwbm_mc
     */
    public String getGddwbm_mc() {
        return gddwbm_mc;
    }

    /**
     * 供电单位
     * @param gddwbm_mc the gddwbm_mc to set
     */
    public void setGddwbm_mc(String gddwbm_mc) {
        this.gddwbm_mc = gddwbm_mc;
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
     * 计量点名称
     * @return the jldmc
     */
    public String getJldmc() {
        return jldmc;
    }

    /**
     * 计量点名称
     * @param jldmc the jldmc to set
     */
    public void setJldmc(String jldmc) {
        this.jldmc = jldmc;
    }

    /**
     * 计量点类型
     * @return the jldlxdm
     */
    public String getJldlxdm() {
        return jldlxdm;
    }

    /**
     * 计量点类型
     * @param jldlxdm the jldlxdm to set
     */
    public void setJldlxdm(String jldlxdm) {
        this.jldlxdm = jldlxdm;
    }

    /**
     * 计量点类型名称
     * @return the jldlxdm_mc
     */
    public String getJldlxdm_mc() {
        return jldlxdm_mc;
    }

    /**
     * 计量点类型名称
     * @param jldlxdm_mc the jldlxdm_mc to set
     */
    public void setJldlxdm_mc(String jldlxdm_mc) {
        this.jldlxdm_mc = jldlxdm_mc;
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
     * 电表资产编号
     * @return the zcbh
     */
    public String getZcbh() {
        return zcbh;
    }

    /**
     * 电表资产编号
     * @param zcbh the zcbh to set
     */
    public void setZcbh(String zcbh) {
        this.zcbh = zcbh;
    }

    /**
     * 变电站名称
     * @return the bdzmc
     */
    public String getBdzmc() {
        return bdzmc;
    }

    /**
     * 变电站名称
     * @param bdzmc the bdzmc to set
     */
    public void setBdzmc(String bdzmc) {
        this.bdzmc = bdzmc;
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
     * 线路名称
     * @return the xlmc
     */
    public String getXlmc() {
        return xlmc;
    }

    /**
     * 线路名称
     * @param xlmc the xlmc to set
     */
    public void setXlmc(String xlmc) {
        this.xlmc = xlmc;
    }

    /**
     * 线路编号
     * @return the xlbh
     */
    public String getXlbh() {
        return xlbh;
    }

    /**
     * 线路编号
     * @param xlbh the xlbh to set
     */
    public void setXlbh(String xlbh) {
        this.xlbh = xlbh;
    }

    /**
     * 电压等级代码
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级代码
     * @param dydjdm the dydjdm to set
     */
    public void setDydjdm(String dydjdm) {
        this.dydjdm = dydjdm;
    }

    /**
     * 电压等级名称
     * @return the dydjdm_mc
     */
    public String getDydjdm_mc() {
        return dydjdm_mc;
    }

    /**
     * 电压等级名称
     * @param dydjdm_mc the dydjdm_mc to set
     */
    public void setDydjdm_mc(String dydjdm_mc) {
        this.dydjdm_mc = dydjdm_mc;
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
     * 电源编号
     * @return the dybh
     */
    public String getDybh() {
        return dybh;
    }

    /**
     * 电源编号
     * @param dybh the dybh to set
     */
    public void setDybh(String dybh) {
        this.dybh = dybh;
    }

    /**
     * 开关编号
     * @return the kgbh
     */
    public String getKgbh() {
        return kgbh;
    }

    /**
     * 开关编号
     * @param kgbh the kgbh to set
     */
    public void setKgbh(String kgbh) {
        this.kgbh = kgbh;
    }

    /**
     * 出厂编号
     * @return the ccbh
     */
    public String getCcbh() {
        return ccbh;
    }

    /**
     * 出厂编号
     * @param ccbh the ccbh to set
     */
    public void setCcbh(String ccbh) {
        this.ccbh = ccbh;
    }

    /**
     * 接线方向
     * @return the jxfzmc
     */
    public String getJxfzmc() {
        return jxfzmc;
    }

    /**
     * 接线方向
     * @param jxfzmc the jxfzmc to set
     */
    public void setJxfzmc(String jxfzmc) {
        this.jxfzmc = jxfzmc;
    }

    /**
     * 计量装置分类
     * @return the jlzzfldm
     */
    public String getJlzzfldm() {
        return jlzzfldm;
    }

    /**
     * 计量装置分类
     * @param jlzzfldm the jlzzfldm to set
     */
    public void setJlzzfldm(String jlzzfldm) {
        this.jlzzfldm = jlzzfldm;
    }

    /**
     * 计量装置分类名称
     * @return the jlzzfldm_mc
     */
    public String getJlzzfldm_mc() {
        return jlzzfldm_mc;
    }

    /**
     * 计量装置分类名称
     * @param jlzzfldm_mc the jlzzfldm_mc to set
     */
    public void setJlzzfldm_mc(String jlzzfldm_mc) {
        this.jlzzfldm_mc = jlzzfldm_mc;
    }

    /**
     * 用电类别编码
     * @return the ydlbdm
     */
    public String getYdlbdm() {
        return ydlbdm;
    }

    /**
     * 用电类别编码
     * @param ydlbdm the ydlbdm to set
     */
    public void setYdlbdm(String ydlbdm) {
        this.ydlbdm = ydlbdm;
    }

    /**
     * 用电类别名称
     * @return the ydlbdm_mc
     */
    public String getYdlbdm_mc() {
        return ydlbdm_mc;
    }

    /**
     * 用电类别名称
     * @param ydlbdm_mc the ydlbdm_mc to set
     */
    public void setYdlbdm_mc(String ydlbdm_mc) {
        this.ydlbdm_mc = ydlbdm_mc;
    }

    /**
     * 计量点状态代码
     * @return the jldztdm
     */
    public String getJldztdm() {
        return jldztdm;
    }

    /**
     * 计量点状态代码
     * @param jldztdm the jldztdm to set
     */
    public void setJldztdm(String jldztdm) {
        this.jldztdm = jldztdm;
    }

    /**
     * 计量点状态代码名称
     * @return the jldztdm_mc
     */
    public String getJldztdm_mc() {
        return jldztdm_mc;
    }

    /**
     * 计量点状态代码名称
     * @param jldztdm_mc the jldztdm_mc to set
     */
    public void setJldztdm_mc(String jldztdm_mc) {
        this.jldztdm_mc = jldztdm_mc;
    }

    /**
     * PT变比代码
     * @return the ptbbdm
     */
    public String getPtbbdm() {
        return ptbbdm;
    }

    /**
     * PT变比代码
     * @param ptbbdm the ptbbdm to set
     */
    public void setPtbbdm(String ptbbdm) {
        this.ptbbdm = ptbbdm;
    }

    /**
     * PT变比
     * @return the pt
     */
    public String getPt() {
        return pt;
    }

    /**
     * PT变比
     * @param pt the pt to set
     */
    public void setPt(String pt) {
        this.pt = pt;
    }

    /**
     * CT变比代码
     * @return the ctbbdm
     */
    public String getCtbbdm() {
        return ctbbdm;
    }

    /**
     * CT变比代码
     * @param ctbbdm the ctbbdm to set
     */
    public void setCtbbdm(String ctbbdm) {
        this.ctbbdm = ctbbdm;
    }

    /**
     * CT变比
     * @return the ct
     */
    public String getCt() {
        return ct;
    }

    /**
     * CT变比
     * @param ct the ct to set
     */
    public void setCt(String ct) {
        this.ct = ct;
    }

    /**
     * 主副表
     * @return the zfb
     */
    public String getZfb() {
        return zfb;
    }

    /**
     * 主副表
     * @param zfb the zfb to set
     */
    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    /**
     * 创建时间
     * @return the cjsj
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 创建时间
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
     * 生产厂家标识
     * @return the sccjbs
     */
    public String getSccjbs() {
        return sccjbs;
    }

    /**
     * 生产厂家标识
     * @param sccjbs the sccjbs to set
     */
    public void setSccjbs(String sccjbs) {
        this.sccjbs = sccjbs;
    }

    /**
     * 接线方式名称
     * @return the jxfsmc
     */
    public String getJxfsmc() {
        return jxfsmc;
    }

    /**
     * 接线方式名称
     * @param jxfsmc the jxfsmc to set
     */
    public void setJxfsmc(String jxfsmc) {
        this.jxfsmc = jxfsmc;
    }

    /**
     * 通信方式名称
     * @return the txfsmc
     */
    public String getTxfsmc() {
        return txfsmc;
    }

    /**
     * 通信方式名称
     * @param txfsmc the txfsmc to set
     */
    public void setTxfsmc(String txfsmc) {
        this.txfsmc = txfsmc;
    }

    /**
     * 设备型号名称
     * @return the sbxhmc
     */
    public String getSbxhmc() {
        return sbxhmc;
    }

    /**
     * 设备型号名称
     * @param sbxhmc the sbxhmc to set
     */
    public void setSbxhmc(String sbxhmc) {
        this.sbxhmc = sbxhmc;
    }

    /**
     * 计量方式代码
     * @return the jlfsdm
     */
    public String getJlfsdm() {
        return jlfsdm;
    }

    /**
     * 计量方式代码
     * @param jlfsdm the jlfsdm to set
     */
    public void setJlfsdm(String jlfsdm) {
        this.jlfsdm = jlfsdm;
    }

    /**
     * 计量方式名称
     * @return the jlfsdm_mc
     */
    public String getJlfsdm_mc() {
        return jlfsdm_mc;
    }

    /**
     * 计量方式名称
     * @param jlfsdm_mc the jlfsdm_mc to set
     */
    public void setJlfsdm_mc(String jlfsdm_mc) {
        this.jlfsdm_mc = jlfsdm_mc;
    }

    /**
     * 行业分类代码，详见：VW_HYFLDM
     * @return the hyfldm
     */
    public String getHyfldm() {
        return hyfldm;
    }

    /**
     * 行业分类代码，详见：VW_HYFLDM
     * @param hyfldm the hyfldm to set
     */
    public void setHyfldm(String hyfldm) {
        this.hyfldm = hyfldm;
    }

    /**
     * 行业分类名称
     * @return the hyfldm_mc
     */
    public String getHyfldm_mc() {
        return hyfldm_mc;
    }

    /**
     * 行业分类名称
     * @param hyfldm_mc the hyfldm_mc to set
     */
    public void setHyfldm_mc(String hyfldm_mc) {
        this.hyfldm_mc = hyfldm_mc;
    }

    /**
     * 分时计费标志，{0否；1是},是否执行峰谷标志：是、否，当此标志为否时，即使用户安装分时表，执行的电价也是分时电价，计费时仍按照基准时段的单价计费
     * @return the fsjfbz
     */
    public String getFsjfbz() {
        return fsjfbz;
    }

    /**
     * 分时计费标志，{0否；1是},是否执行峰谷标志：是、否，当此标志为否时，即使用户安装分时表，执行的电价也是分时电价，计费时仍按照基准时段的单价计费
     * @param fsjfbz the fsjfbz to set
     */
    public void setFsjfbz(String fsjfbz) {
        this.fsjfbz = fsjfbz;
    }

    /**
     * 计量点类别代码，详见：VW_JLDLBDM
     * @return the jldlbdm
     */
    public String getJldlbdm() {
        return jldlbdm;
    }

    /**
     * 计量点类别代码，详见：VW_JLDLBDM
     * @param jldlbdm the jldlbdm to set
     */
    public void setJldlbdm(String jldlbdm) {
        this.jldlbdm = jldlbdm;
    }

    /**
     * 计量点类别名称
     * @return the jldlbdm_mc
     */
    public String getJldlbdm_mc() {
        return jldlbdm_mc;
    }

    /**
     * 计量点类别名称
     * @param jldlbdm_mc the jldlbdm_mc to set
     */
    public void setJldlbdm_mc(String jldlbdm_mc) {
        this.jldlbdm_mc = jldlbdm_mc;
    }

    /**
     * 计量点用途代码
     * @return the jldytdm
     */
    public String getJldytdm() {
        return jldytdm;
    }

    /**
     * 计量点用途代码
     * @param jldytdm the jldytdm to set
     */
    public void setJldytdm(String jldytdm) {
        this.jldytdm = jldytdm;
    }

    /**
     * 计量点用途名称
     * @return the jldytdm_mc
     */
    public String getJldytdm_mc() {
        return jldytdm_mc;
    }

    /**
     * 计量点用途名称
     * @param jldytdm_mc the jldytdm_mc to set
     */
    public void setJldytdm_mc(String jldytdm_mc) {
        this.jldytdm_mc = jldytdm_mc;
    }

    /**
     * 测量点号
     * @return the cldh
     */
    public String getCldh() {
        return cldh;
    }

    /**
     * 测量点号
     * @param cldh the cldh to set
     */
    public void setCldh(String cldh) {
        this.cldh = cldh;
    }
}
