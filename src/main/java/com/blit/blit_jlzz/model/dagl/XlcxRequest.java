/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

/**
 * 线路查询请求类
 *
 * @author caibenxiang
 */
public class XlcxRequest {

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 线路类别编码
     */
    private String xllbdm;

    /**
     * 电压等级编码
     */
    private String dydjdm;

    /**
     * 变电站标识
     */
    private String bdzbs;

    /**
     * 节点类型
     *
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 节点类型
     *
     * @param nodeType the nodeType to set
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
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
     * 线路类别编码
     *
     * @return the xllbdm
     */
    public String getXllbdm() {
        return xllbdm;
    }

    /**
     * 线路类别编码
     *
     * @param xllbdm the xllbdm to set
     */
    public void setXllbdm(String xllbdm) {
        this.xllbdm = xllbdm;
    }

    /**
     * 电压等级编码
     *
     * @return the dydjdm
     */
    public String getDydjdm() {
        return dydjdm;
    }

    /**
     * 电压等级编码
     *
     * @param dydjdm the dydjdm to set
     */
    public void setDydjdm(String dydjdm) {
        this.dydjdm = dydjdm;
    }

    /**
     * 变电站标识
     *
     * @return the bdzbs
     */
    public String getBdzbs() {
        return bdzbs;
    }

    /**
     * 变电站标识
     *
     * @param bdzbs the bdzbs to set
     */
    public void setBdzbs(String bdzbs) {
        this.bdzbs = bdzbs;
    }
}
