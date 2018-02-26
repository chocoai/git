/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 终端类型枚举类
 *
 * @author caibenxiang
 */
public enum ETmnlTypeCode {

    /**
     * 负荷管理终端
     */
    LOAD_MANAGE_TMNL("01", "负荷管理终端"),
    /**
     * 售电管理终端
     */
    SELL_MANAGE_TMNL("02", "售电管理终端"),
    /**
     * 厂站终端
     */
    STATION_TMNL("03", "厂站终端"),
    /**
     * 配变终端
     */
    SUB_TMNL("04", "配变终端"),
    /**
     * 低压抄表集中器
     */
    CONCENTRATOR("05", "低压抄表集中器");

    private String index;
    private String desc;

    private ETmnlTypeCode(String index, String desc) {
        setIndex(index);
        setDesc(desc);
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return this.index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
