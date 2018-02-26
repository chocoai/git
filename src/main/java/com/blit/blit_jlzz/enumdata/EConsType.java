/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 用户类别枚举类
 *
 * @author caibenxiang
 */
public enum EConsType {

    /**
     * 公线专变客户
     */
    GXZBKH("10", "公线专变客户"),
    /**
     * 窃电黑户
     */
    QDHH("91", "窃电黑户"),
    /**
     * 公变客户
     */
    GBKH("20", "公变客户"),
    /**
     * 趸售关口户
     */
    DSGKH("30", "趸售关口户"),
    /**
     * 统调电厂户
     */
    TDDCH("35", "统调电厂户"),
    /**
     * 地方电厂户
     */
    DFDCH("40", "地方电厂户"),
    /**
     * 非统调地方电厂户
     */
    FTDDFDCH("41", "非统调地方电厂户"),
    /**
     * 光伏发电客户
     */
    GFFDKH("45", "光伏发电客户"),
    /**
     * 线路考核表户
     */
    XLKHBH("50", "线路考核表户"),
    /**
     * 线路线损台区考核表虚户
     */
    XLXSTQKHBXH("55", "线路线损台区考核表虚户"),
    /**
     * 台区考核表户
     */
    TQKHBH("60", "台区考核表户"),
    /**
     * 省网关口表户
     */
    SWGKBH("70", "省网关口表户"),
    /**
     * 跨区跨省交易关口表户
     */
    KQKSJYGKBH("71", "跨区跨省交易关口表户"),
    /**
     * 跨国跨境交易关口表户
     */
    KGKJJYGKBH("72", "跨国跨境交易关口表户"),
    /**
     * 变电站考核表户
     */
    BDZKHBH("80", "变电站考核表户"),
    /**
     * 站用变考核表户
     */
    ZYBKHBH("90", "站用变考核表户"),
    /**
     * 专线专变客户
     */
    ZXZBKH("11", "专线专变客户");

    private String index;
    private String desc;

    private EConsType(String index, String desc) {
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
