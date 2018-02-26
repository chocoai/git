/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 变压器类型枚举类
 *
 * @author caibenxiang
 */
public enum ESubType {

    /**
     * 公变台区
     */
    GBTQ("1", "公变台区"),
    /**
     * 专变
     */
    ZHUAN_B("2", "专变"),
    /**
     * 接地变
     */
    JDB("5", "接地变"),
    /**
     * 站用变
     */
    ZYB("4", "站用变"),
    /**
     * 主变
     */
    ZHU_B("3", "主变");

    private String index;
    private String desc;

    private ESubType(String index, String desc) {
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
