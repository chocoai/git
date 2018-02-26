/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 树节点类型枚举类
 *
 * @author caibenxiang
 */
public enum ENode {

    /**
     * 用户群组
     */
    UGP("ugp", "用户群组"),
    /**
     * 控制群组
     */
    CGP("cgp", "控制群组"),
    /**
     * 根节点
     */
    ROOT("root", "根节点"),
    /**
     * 供电单位
     */
    ORG("org", "供电单位"),
    /**
     * 用电客户
     */
    CONS("cons", "用电客户"),
    /**
     * 线路
     */
    LINE("line", "线路"),
    /**
     * 变电站
     */
    SUB("sub", "变电站"),
    /**
     * 台区
     */
    TQ("tq", "台区"),
    /**
     * 终端
     */
    TMNL("tmnl", "终端"),
    /**
     * 计量点
     */
    METER_POINT("mp", "计量点"),
    /**
     * 采集器
     */
    COLLECTOR("coll", "采集器"),
    /**
     * 电表
     */
    METER("meter", "电表"),
    /**
     * 电厂
     */
    POWER_PLANT("pp", "电厂"),
    /**
     * 变压器
     */
    TRANSFORMER("tran", "变压器"),
    /**
     * 中心变电站
     */
    CENTER_SUB("centersub", "中心变电站"),
    /**
     * 变电站管理机构
     */
    SUB_MANAGE_ORG("submanageorg", "变电站管理机构");

    private String index;
    private String desc;

    private ENode(String index, String desc) {
        setIndex(index);
        setDesc(desc);
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}
