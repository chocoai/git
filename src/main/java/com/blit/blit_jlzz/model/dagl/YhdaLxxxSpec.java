/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户档案联系信息
 *
 * @author caibenxiang
 */
public class YhdaLxxxSpec extends Model<YhdaLxxxSpec> {

    /**
     * 用户编号
     */
    private String yhbh;
    
    /**
     * 联系人
     */
    private String lxr;
    
    /**
     * 移动电话
     */
    private String yddh;

    /**
     * 用户编号
     * @return the yhbh
     */
    public String getYhbh() {
        return yhbh;
    }

    /**
     * 用户编号
     * @param yhbh the yhbh to set
     */
    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    /**
     * 联系人
     * @return the lxr
     */
    public String getLxr() {
        return lxr;
    }

    /**
     * 联系人
     * @param lxr the lxr to set
     */
    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    /**
     * 移动电话
     * @return the yddh
     */
    public String getYddh() {
        return yddh;
    }

    /**
     * 移动电话
     * @param yddh the yddh to set
     */
    public void setYddh(String yddh) {
        this.yddh = yddh;
    }

}
