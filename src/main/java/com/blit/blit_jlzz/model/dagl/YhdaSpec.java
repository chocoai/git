/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 用户档案查询主体对象
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YhdaSpec {

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 用户名称
     */
    private String yhmc;

    /**
     * 用电地址
     */
    private String yddz;

    /**
     * 用电类别编码
     */
    private String ydlbdm;

    /**
     * 用户类别名称
     */
    private String yhlbdm_mc;

    /**
     * 电压等级编码
     */
    private String dydjdm;
    
    /**
     * 电压等级名称
     */
    private String dydjdm_mc;

    /**
     * 行业分类编码
     */
    private String hyfldm;

    /**
     * 行业分类名称
     */
    private String hyfldm_mc;

    /**
     * 主计量方式
     */
    private String jlfsdm;

    /**
     * 用户类别
     */
    private String yhlbdm;

    /**
     * 供电单位
     */
    private String gddwbm;

    /**
     * 负荷性质分类
     */
    private String fhxzdm;

    /**
     * 高耗能行业划分
     */
    private String ghnhylbdm;

    /**
     * 销户信息归档日期
     */
    private String xhrq;

    /**
     * 用户状态
     */
    private String yhztdm;

    /**
     * 预付费类型
     */
    private String yfflxdm;

    /**
     * 费控模式
     */
    private String fkmsdm_mc;

    /**
     * 付费模式
     */
    private String ffmsdm;

    /**
     * 客户分群标志
     */
    private String khfqbzdm;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 最近变更时间
     */
    private String czsj;

    /**
     * 抄表区段
     */
    private String cbqdbh;

    /**
     * 合同约定容量
     */
    private String htrl;

    /**
     * 使用合同容量
     */
    private String yxrl;

    /**
     * 联系信息对象
     */
    private List<YhdaLxxxSpec> lxxx;

    /**
     * 联系人
     */
    private String lxr;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 线路线段标识
     */
    private String xlxdbs;

    /**
     * 节点类型，开发匹配用
     */
    @JsonProperty("nodetype") 
    private String nodeType;

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
     * 用电地址
     * @return the yddz
     */
    public String getYddz() {
        return yddz;
    }

    /**
     * 用电地址
     * @param yddz the yddz to set
     */
    public void setYddz(String yddz) {
        this.yddz = yddz;
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
     * 用户类别名称
     * @return the yhlbdm_mc
     */
    public String getYhlbdm_mc() {
        return yhlbdm_mc;
    }

    /**
     * 用户类别名称
     * @param yhlbdm_mc the yhlbdm_mc to set
     */
    public void setYhlbdm_mc(String yhlbdm_mc) {
        this.yhlbdm_mc = yhlbdm_mc;
    }

    /**
     * 电压等级编码
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级编码
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
     * 行业分类编码
     * @return the hyfldm
     */
    public String getHyfldm() {
        return hyfldm;
    }

    /**
     * 行业分类编码
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
     * 主计量方式
     * @return the jlfsdm
     */
    public String getJlfsdm() {
        return jlfsdm;
    }

    /**
     * 主计量方式
     * @param jlfsdm the jlfsdm to set
     */
    public void setJlfsdm(String jlfsdm) {
        this.jlfsdm = jlfsdm;
    }

    /**
     * 用户类别
     * @return the yhlbdm
     */
    public String getYhlbdm() {
        return yhlbdm;
    }

    /**
     * 用户类别
     * @param yhlbdm the yhlbdm to set
     */
    public void setYhlbdm(String yhlbdm) {
        this.yhlbdm = yhlbdm;
    }

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
     * 负荷性质分类
     * @return the fhxzdm
     */
    public String getFhxzdm() {
        return fhxzdm;
    }

    /**
     * 负荷性质分类
     * @param fhxzdm the fhxzdm to set
     */
    public void setFhxzdm(String fhxzdm) {
        this.fhxzdm = fhxzdm;
    }

    /**
     * 高耗能行业划分
     * @return the ghnhylbdm
     */
    public String getGhnhylbdm() {
        return ghnhylbdm;
    }

    /**
     * 高耗能行业划分
     * @param ghnhylbdm the ghnhylbdm to set
     */
    public void setGhnhylbdm(String ghnhylbdm) {
        this.ghnhylbdm = ghnhylbdm;
    }

    /**
     * 销户信息归档日期
     * @return the xhrq
     */
    public String getXhrq() {
        return xhrq;
    }

    /**
     * 销户信息归档日期
     * @param xhrq the xhrq to set
     */
    public void setXhrq(String xhrq) {
        this.xhrq = xhrq;
    }

    /**
     * 用户状态
     * @return the yhztdm
     */
    public String getYhztdm() {
        return yhztdm;
    }

    /**
     * 用户状态
     * @param yhztdm the yhztdm to set
     */
    public void setYhztdm(String yhztdm) {
        this.yhztdm = yhztdm;
    }

    /**
     * 预付费类型
     * @return the yfflxdm
     */
    public String getYfflxdm() {
        return yfflxdm;
    }

    /**
     * 预付费类型
     * @param yfflxdm the yfflxdm to set
     */
    public void setYfflxdm(String yfflxdm) {
        this.yfflxdm = yfflxdm;
    }

    /**
     * 费控模式
     * @return the fkmsdm_mc
     */
    public String getFkmsdm_mc() {
        return fkmsdm_mc;
    }

    /**
     * 费控模式
     * @param fkmsdm_mc the fkmsdm_mc to set
     */
    public void setFkmsdm_mc(String fkmsdm_mc) {
        this.fkmsdm_mc = fkmsdm_mc;
    }

    /**
     * 付费模式
     * @return the ffmsdm
     */
    public String getFfmsdm() {
        return ffmsdm;
    }

    /**
     * 付费模式
     * @param ffmsdm the ffmsdm to set
     */
    public void setFfmsdm(String ffmsdm) {
        this.ffmsdm = ffmsdm;
    }

    /**
     * 客户分群标志
     * @return the khfqbzdm
     */
    public String getKhfqbzdm() {
        return khfqbzdm;
    }

    /**
     * 客户分群标志
     * @param khfqbzdm the khfqbzdm to set
     */
    public void setKhfqbzdm(String khfqbzdm) {
        this.khfqbzdm = khfqbzdm;
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
     * 最近变更时间
     * @return the czsj
     */
    public String getCzsj() {
        return czsj;
    }

    /**
     * 最近变更时间
     * @param czsj the czsj to set
     */
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    /**
     * 抄表区段
     * @return the cbqdbh
     */
    public String getCbqdbh() {
        return cbqdbh;
    }

    /**
     * 抄表区段
     * @param cbqdbh the cbqdbh to set
     */
    public void setCbqdbh(String cbqdbh) {
        this.cbqdbh = cbqdbh;
    }

    /**
     * 合同约定容量
     * @return the htrl
     */
    public String getHtrl() {
        return htrl;
    }

    /**
     * 合同约定容量
     * @param htrl the htrl to set
     */
    public void setHtrl(String htrl) {
        this.htrl = htrl;
    }

    /**
     * 使用合同容量
     * @return the yxrl
     */
    public String getYxrl() {
        return yxrl;
    }

    /**
     * 使用合同容量
     * @param yxrl the yxrl to set
     */
    public void setYxrl(String yxrl) {
        this.yxrl = yxrl;
    }

    /**
     * 联系信息对象
     * @return the lxxx
     */
    public List<YhdaLxxxSpec> getLxxx() {
        return lxxx;
    }

    /**
     * 联系信息对象
     * @param lxxx the lxxx to set
     */
    public void setLxxx(List<YhdaLxxxSpec> lxxx) {
        this.lxxx = lxxx;
    }

    /**
     * 联系人
     * @return the lxr
     */
    public String getLxr() {
        return lxr;
    }

    /**
     * 联系人
     * @param lxr the lxr to set
     */
    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    /**
     * 联系电话
     * @return the lxdh
     */
    public String getLxdh() {
        return lxdh;
    }

    /**
     * 联系电话
     * @param lxdh the lxdh to set
     */
    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
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
     * 线路节点类型，开发匹配用
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 线路节点类型，开发匹配用
     * @param nodeType the nodeType to set
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    
}
