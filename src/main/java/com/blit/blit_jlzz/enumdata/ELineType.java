/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 线路类型枚举
 *
 * @author caibenxiang
 */
public enum ELineType {

    /**
     * 馈线
     */
    FEEDER_LINE("1", "馈线"),
    /**
     * 联络线
     */
    LINK_LINE("2", "联络线"),
    /**
     * 母线
     */
    BUS_LINE("3", "母线");

    private String index;
    private String desc;

    private ELineType(String index, String desc) {
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
