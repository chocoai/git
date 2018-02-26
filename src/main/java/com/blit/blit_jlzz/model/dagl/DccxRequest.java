/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

/**
 * 电厂查询查询请求类
 *
 * @author caibenxiang
 */
public class DccxRequest {

    /**
     * 供电单位编码
     */
    private String gddwbm;

    /**
     * 电压等级代码
     */
    private String dydjdm;

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
}
