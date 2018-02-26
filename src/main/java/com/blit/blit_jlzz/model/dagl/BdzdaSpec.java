/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 变电站档案查询主体对象.
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BdzdaSpec {

    /**
     * 主键，实时库用
     */
    private String keyid;
    
    /**
     * 供电单位编码。
     */
    private String gddwbm;
    
    /**
     * 供电单位编码名称
     */
    private String gddwbm_mc;
    
    /**
     * 管理单位编码
     */
    private String gldwbm;
    
    /**
     * 管理单位编码名称
     */
    private String gldwbm_mc;

    /**
     * 变电站名称。
     */
    private String bdzmc;

    /**
     * 变电站编号。
     */
    private String bdzbh;

    /**
     * 变电站类型代码
     */
    private String bdzlxdm;
    
    /**
     * 变电站类型代码名称
     */
    private String bdzlxdm_mc;

    /**
     * 变电站标识。
     */
    private String bdzbs;

    /**
     * 变电站地址。
     */
    private String bdzdz;

    /**
     * 电压等级代码。
     */
    private String dydjdm;
    
    /**
     * 电压等级代码名称
     */
    private String dydjdm_mc;

    /**
     * 运行状态代码。
     */
    private String bdzyxztdm;
    
    /**
     * 运行状态代码名称。
     */
    private String bdzyxztdm_mc;

    /**
     * 产权归属代码。
     */
    private String cqgsdm;
    
    /**
     * 产权归属代码名称
     */
    private String cqgsdm_mc;

    /**
     * 创建时间。
     */
    private String cjsj;

    /**
     * 操作时间。
     */
    private String czsj;
    
    /**
     * GIS系统标识，在GIS系统中的ID号，用来与GIS系统集成
     */
    private String gisid;
    
    /**
     * 线路线段标识
     */
    private String xlxdbs;

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
     * 供电单位编码。
     * @return the gddwbm
     */
    public String getGddwbm() {
        return gddwbm;
    }

    /**
     * 供电单位编码。
     * @param gddwbm the gddwbm to set
     */
    public void setGddwbm(String gddwbm) {
        this.gddwbm = gddwbm;
    }

    /**
     * 供电单位编码名称
     * @return the gddwbm_mc
     */
    public String getGddwbm_mc() {
        return gddwbm_mc;
    }

    /**
     * 供电单位编码名称
     * @param gddwbm_mc the gddwbm_mc to set
     */
    public void setGddwbm_mc(String gddwbm_mc) {
        this.gddwbm_mc = gddwbm_mc;
    }

    /**
     * 管理单位编码
     * @return the gldwbm
     */
    public String getGldwbm() {
        return gldwbm;
    }

    /**
     * 管理单位编码
     * @param gldwbm the gldwbm to set
     */
    public void setGldwbm(String gldwbm) {
        this.gldwbm = gldwbm;
    }

    /**
     * 管理单位编码名称
     * @return the gldwbm_mc
     */
    public String getGldwbm_mc() {
        return gldwbm_mc;
    }

    /**
     * 管理单位编码名称
     * @param gldwbm_mc the gldwbm_mc to set
     */
    public void setGldwbm_mc(String gldwbm_mc) {
        this.gldwbm_mc = gldwbm_mc;
    }

    /**
     * 变电站名称。
     * @return the bdzmc
     */
    public String getBdzmc() {
        return bdzmc;
    }

    /**
     * 变电站名称。
     * @param bdzmc the bdzmc to set
     */
    public void setBdzmc(String bdzmc) {
        this.bdzmc = bdzmc;
    }

    /**
     * 变电站编号。
     * @return the bdzbh
     */
    public String getBdzbh() {
        return bdzbh;
    }

    /**
     * 变电站编号。
     * @param bdzbh the bdzbh to set
     */
    public void setBdzbh(String bdzbh) {
        this.bdzbh = bdzbh;
    }

    /**
     * 变电站类型代码
     * @return the bdzlxdm
     */
    public String getBdzlxdm() {
        return bdzlxdm;
    }

    /**
     * 变电站类型代码
     * @param bdzlxdm the bdzlxdm to set
     */
    public void setBdzlxdm(String bdzlxdm) {
        this.bdzlxdm = bdzlxdm;
    }

    /**
     * 变电站类型代码名称
     * @return the bdzlxdm_mc
     */
    public String getBdzlxdm_mc() {
        return bdzlxdm_mc;
    }

    /**
     * 变电站类型代码名称
     * @param bdzlxdm_mc the bdzlxdm_mc to set
     */
    public void setBdzlxdm_mc(String bdzlxdm_mc) {
        this.bdzlxdm_mc = bdzlxdm_mc;
    }

    /**
     * 变电站标识。
     * @return the bdzbs
     */
    public String getBdzbs() {
        return bdzbs;
    }

    /**
     * 变电站标识。
     * @param bdzbs the bdzbs to set
     */
    public void setBdzbs(String bdzbs) {
        this.bdzbs = bdzbs;
    }

    /**
     * 变电站地址。
     * @return the bdzdz
     */
    public String getBdzdz() {
        return bdzdz;
    }

    /**
     * 变电站地址。
     * @param bdzdz the bdzdz to set
     */
    public void setBdzdz(String bdzdz) {
        this.bdzdz = bdzdz;
    }

    /**
     * 电压等级代码。
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级代码。
     * @param dydjdm the dydjdm to set
     */
    public void setDydjdm(String dydjdm) {
        this.dydjdm = dydjdm;
    }

    /**
     * 电压等级代码名称
     * @return the dydjdm_mc
     */
    public String getDydjdm_mc() {
        return dydjdm_mc;
    }

    /**
     * 电压等级代码名称
     * @param dydjdm_mc the dydjdm_mc to set
     */
    public void setDydjdm_mc(String dydjdm_mc) {
        this.dydjdm_mc = dydjdm_mc;
    }

    /**
     * 运行状态代码。
     * @return the bdzyxztdm
     */
    public String getBdzyxztdm() {
        return bdzyxztdm;
    }

    /**
     * 运行状态代码。
     * @param bdzyxztdm the bdzyxztdm to set
     */
    public void setBdzyxztdm(String bdzyxztdm) {
        this.bdzyxztdm = bdzyxztdm;
    }

    /**
     * 运行状态代码名称。
     * @return the bdzyxztdm_mc
     */
    public String getBdzyxztdm_mc() {
        return bdzyxztdm_mc;
    }

    /**
     * 运行状态代码名称。
     * @param bdzyxztdm_mc the bdzyxztdm_mc to set
     */
    public void setBdzyxztdm_mc(String bdzyxztdm_mc) {
        this.bdzyxztdm_mc = bdzyxztdm_mc;
    }

    /**
     * 产权归属代码。
     * @return the cqgsdm
     */
    public String getCqgsdm() {
        return cqgsdm;
    }

    /**
     * 产权归属代码。
     * @param cqgsdm the cqgsdm to set
     */
    public void setCqgsdm(String cqgsdm) {
        this.cqgsdm = cqgsdm;
    }

    /**
     * 产权归属代码名称
     * @return the cqgsdm_mc
     */
    public String getCqgsdm_mc() {
        return cqgsdm_mc;
    }

    /**
     * 产权归属代码名称
     * @param cqgsdm_mc the cqgsdm_mc to set
     */
    public void setCqgsdm_mc(String cqgsdm_mc) {
        this.cqgsdm_mc = cqgsdm_mc;
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
     * 操作时间。
     * @return the czsj
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 操作时间。
     * @param czsj the czsj to set
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 线路线段标识
     * @return the xlxdbs
     */
    public String getXlxdbs() {
        return xlxdbs;
    }

    /**
     * 线路线段标识
     * @param xlxdbs the xlxdbs to set
     */
    public void setXlxdbs(String xlxdbs) {
        this.xlxdbs = xlxdbs;
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
}
