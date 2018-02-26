/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 组织实体类
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgSpec {

    /**
     * 组织编号
     */
    @JsonProperty("org_no") 
    private String orgNo;

    /**
     * 组织上级编号
     */
    @JsonProperty("p_org_no") 
    private String pOrgNo;

    /**
     * 组织类型
     */
    @JsonProperty("org_type") 
    private String orgType;

    /**
     * 组织名称
     */
    @JsonProperty("org_name") 
    private String orgName;

    /**
     * 组织编号
     *
     * @return the orgNo
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * 组织编号
     *
     * @param orgNo the orgNo to set
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 组织上级编号
     *
     * @return the pOrgNo
     */
    public String getpOrgNo() {
        return pOrgNo;
    }

    /**
     * 组织上级编号
     *
     * @param pOrgNo the pOrgNo to set
     */
    public void setpOrgNo(String pOrgNo) {
        this.pOrgNo = pOrgNo;
    }

    /**
     * 组织类型
     *
     * @return the orgType
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * 组织类型
     *
     * @param orgType the orgType to set
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * 组织名称
     *
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 组织名称
     *
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
