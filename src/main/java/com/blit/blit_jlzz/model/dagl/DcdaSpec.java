/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 电厂档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DcdaSpec {

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
     * 电厂名称
     */
    private String dcmc;

    /**
     * 电厂编号
     */
    private String dcbh;

    /**
     * 购电电源类型编码
     */
    private String gddylxdm;

    /**
     * 购电电源类型名称
     */
    private String gddylxdm_mc;

    /**
     * 电厂地址
     */
    private String dcdz;

    /**
     * 对应用电客户编号
     */
    private String dyydkhbh;

    /**
     * 调度关系代码
     */
    private String ddgxdm;

    /**
     * 调度关系名称
     */
    private String ddgxdm_mc;

    /**
     * 装机总容量
     */
    private String zjzrl;

    /**
     * 电压等级
     */
    private String dydjdm;

    /**
     * 电压等级名称
     */
    private String dydjdm_mc;

    /**
     * 运行状态
     */
    private String yxztdm;

    /**
     * 运行状态名称
     */
    private String yxztdm_mc;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 操作时间
     */
    private String czsj;

    /**
     * 投产时间
     */
    private String tcsj;

    /**
     * 是否为互抵电厂
     */
    private String hddcbz;

    /**
     * 结算关系
     */
    private String jsgx;

    /**
     * 管理单位编码
     */
    private String gldwbm;

    /**
     * 管理单位名称
     */
    private String gldwbm_mc;

    /**
     * 电厂性质
     */
    private String dcxz;

    /**
     * 机组台数
     */
    private String jzts;

    /**
     * 所属中心变电站编号
     */
    private String sszxbdzbh;

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
     * 供电单位名称
     *
     * @return the gddwbm_mc
     */
    public String getGddwbm_mc() {
        return gddwbm_mc;
    }

    /**
     * 供电单位名称
     *
     * @param gddwbm_mc the gddwbm_mc to set
     */
    public void setGddwbm_mc(String gddwbm_mc) {
        this.gddwbm_mc = gddwbm_mc;
    }

    /**
     * 电厂名称
     *
     * @return the dcmc
     */
    public String getDcmc() {
        return dcmc;
    }

    /**
     * 电厂名称
     *
     * @param dcmc the dcmc to set
     */
    public void setDcmc(String dcmc) {
        this.dcmc = dcmc;
    }

    /**
     * 电厂编号
     *
     * @return the dcbh
     */
    public String getDcbh() {
        return dcbh;
    }

    /**
     * 电厂编号
     *
     * @param dcbh the dcbh to set
     */
    public void setDcbh(String dcbh) {
        this.dcbh = dcbh;
    }

    /**
     * 购电电源类型编码
     *
     * @return the gddylxdm
     */
    public String getGddylxdm() {
        return gddylxdm;
    }

    /**
     * 购电电源类型编码
     *
     * @param gddylxdm the gddylxdm to set
     */
    public void setGddylxdm(String gddylxdm) {
        this.gddylxdm = gddylxdm;
    }

    /**
     * 购电电源类型名称
     *
     * @return the gddylxdm_mc
     */
    public String getGddylxdm_mc() {
        return gddylxdm_mc;
    }

    /**
     * 购电电源类型名称
     *
     * @param gddylxdm_mc the gddylxdm_mc to set
     */
    public void setGddylxdm_mc(String gddylxdm_mc) {
        this.gddylxdm_mc = gddylxdm_mc;
    }

    /**
     * 电厂地址
     *
     * @return the dcdz
     */
    public String getDcdz() {
        return dcdz;
    }

    /**
     * 电厂地址
     *
     * @param dcdz the dcdz to set
     */
    public void setDcdz(String dcdz) {
        this.dcdz = dcdz;
    }

    /**
     * 对应用电客户编号
     *
     * @return the dyydkhbh
     */
    public String getDyydkhbh() {
        return dyydkhbh;
    }

    /**
     * 对应用电客户编号
     *
     * @param dyydkhbh the dyydkhbh to set
     */
    public void setDyydkhbh(String dyydkhbh) {
        this.dyydkhbh = dyydkhbh;
    }

    /**
     * 调度关系代码
     *
     * @return the ddgxdm
     */
    public String getDdgxdm() {
        return ddgxdm;
    }

    /**
     * 调度关系代码
     *
     * @param ddgxdm the ddgxdm to set
     */
    public void setDdgxdm(String ddgxdm) {
        this.ddgxdm = ddgxdm;
    }

    /**
     * 调度关系名称
     *
     * @return the ddgxdm_mc
     */
    public String getDdgxdm_mc() {
        return ddgxdm_mc;
    }

    /**
     * 调度关系名称
     *
     * @param ddgxdm_mc the ddgxdm_mc to set
     */
    public void setDdgxdm_mc(String ddgxdm_mc) {
        this.ddgxdm_mc = ddgxdm_mc;
    }

    /**
     * 装机总容量
     *
     * @return the zjzrl
     */
    public String getZjzrl() {
        return zjzrl;
    }

    /**
     * 装机总容量
     *
     * @param zjzrl the zjzrl to set
     */
    public void setZjzrl(String zjzrl) {
        this.zjzrl = zjzrl;
    }

    /**
     * 电压等级
     *
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级
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
     * 运行状态
     *
     * @return the yxztdm
     */
    public String getYxztdm() {
        return yxztdm;
    }

    /**
     * 运行状态
     *
     * @param yxztdm the yxztdm to set
     */
    public void setYxztdm(String yxztdm) {
        this.yxztdm = yxztdm;
    }

    /**
     * 运行状态名称
     *
     * @return the yxztdm_mc
     */
    public String getYxztdm_mc() {
        return yxztdm_mc;
    }

    /**
     * 运行状态名称
     *
     * @param yxztdm_mc the yxztdm_mc to set
     */
    public void setYxztdm_mc(String yxztdm_mc) {
        this.yxztdm_mc = yxztdm_mc;
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
     * 投产时间
     *
     * @return the tcsj
     */
    public String getTcsj() {
        return tcsj;
    }

    /**
     * 投产时间
     *
     * @param tcsj the tcsj to set
     */
    public void setTcsj(String tcsj) {
        this.tcsj = tcsj;
    }

    /**
     * 是否为互抵电厂
     *
     * @return the hddcbz
     */
    public String getHddcbz() {
        return hddcbz;
    }

    /**
     * 是否为互抵电厂
     *
     * @param hddcbz the hddcbz to set
     */
    public void setHddcbz(String hddcbz) {
        this.hddcbz = hddcbz;
    }

    /**
     * 结算关系
     *
     * @return the jsgx
     */
    public String getJsgx() {
        return jsgx;
    }

    /**
     * 结算关系
     *
     * @param jsgx the jsgx to set
     */
    public void setJsgx(String jsgx) {
        this.jsgx = jsgx;
    }

    /**
     * 管理单位编码
     *
     * @return the gldwbm
     */
    public String getGldwbm() {
        return gldwbm;
    }

    /**
     * 管理单位编码
     *
     * @param gldwbm the gldwbm to set
     */
    public void setGldwbm(String gldwbm) {
        this.gldwbm = gldwbm;
    }

    /**
     * 管理单位名称
     *
     * @return the gldwbm_mc
     */
    public String getGldwbm_mc() {
        return gldwbm_mc;
    }

    /**
     * 管理单位名称
     *
     * @param gldwbm_mc the gldwbm_mc to set
     */
    public void setGldwbm_mc(String gldwbm_mc) {
        this.gldwbm_mc = gldwbm_mc;
    }

    /**
     * 电厂性质
     *
     * @return the dcxz
     */
    public String getDcxz() {
        return dcxz;
    }

    /**
     * 电厂性质
     *
     * @param dcxz the dcxz to set
     */
    public void setDcxz(String dcxz) {
        this.dcxz = dcxz;
    }

    /**
     * 机组台数
     *
     * @return the jzts
     */
    public String getJzts() {
        return jzts;
    }

    /**
     * 机组台数
     *
     * @param jzts the jzts to set
     */
    public void setJzts(String jzts) {
        this.jzts = jzts;
    }

    /**
     * 所属中心变电站编号
     *
     * @return the sszxbdzbh
     */
    public String getSszxbdzbh() {
        return sszxbdzbh;
    }

    /**
     * 所属中心变电站编号
     *
     * @param sszxbdzbh the sszxbdzbh to set
     */
    public void setSszxbdzbh(String sszxbdzbh) {
        this.sszxbdzbh = sszxbdzbh;
    }

    /**
     * 主键，实时库用
     *
     * @return the keyid
     */
    public String getKeyid() {
        return keyid;
    }

    /**
     * 主键，实时库用
     *
     * @param keyid the keyid to set
     */
    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

}
