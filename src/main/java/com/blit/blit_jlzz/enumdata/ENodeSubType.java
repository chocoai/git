/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 *
 * @author caibenxiang
 */
public enum ENodeSubType {

    /**
     * 第一层,挂载线路
     */
    FIRST("1", "第一层,挂载线路"),
    /**
     * 第二层,挂载计量点
     */
    SECOND("2", "第二层,挂载计量点");

    private String index;
    private String desc;

    private ENodeSubType(String index, String desc) {
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
