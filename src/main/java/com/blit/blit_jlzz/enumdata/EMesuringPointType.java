/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 测量点类型枚举类
 *
 * @author caibenxiang
 */
public enum EMesuringPointType {
    /**
     * 终端
     */
    TERMINAL(0, "终端"),
    /**
     * 电表
     */
    METER(1, "电表"),
    /**
     * 其它
     */
    OTHER(9, "其它");

    private int index;
    private String desc;

    private EMesuringPointType(int index, String desc) {
        setIndex(index);
        setDesc(desc);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
