/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 终端集中器类型枚举类
 *
 * @author caibenxiang
 */
public enum EConcentratorType {
    
    /**
     * I型集中器
     */
    TYPE_1(1, "I型集中器"),
    /**
     * II型集中器
     */
    TYPE_2(2, "II型集中器");

    private int index;
    private String desc;

    private EConcentratorType(int index, String desc) {
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
