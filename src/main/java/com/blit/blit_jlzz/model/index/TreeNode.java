/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 树节点对象类
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {

    public TreeNode() {
    }

    public TreeNode(String id, String name, String type, String iconCls, Boolean expanded) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.iconCls = iconCls;
        this.expanded = expanded;
    }
    
    public TreeNode(String id, String name, String type, String iconCls, Boolean expanded, Boolean isLeaf) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.iconCls = iconCls;
        this.expanded = expanded;
        this.isLeaf = isLeaf;
    }

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点父ID
     */
    private String pid;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点是否默认展开
     */
    private Boolean expanded = false;

    /**
     * 节点图标
     */
    private String iconCls;

    /**
     * 节点类型
     */
    private String type;

    /**
     * 是否叶节点
     */
    private Boolean isLeaf;

    /**
     * 节点悬浮框显示内容
     */
    private String qtip;

    /**
     * 节点明细信息
     */
    private Object attributes;

    /**
     * 节点ID
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 节点ID
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 节点父ID
     *
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * 节点父ID
     *
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 节点名称
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 节点名称
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 节点是否默认展开
     *
     * @return the expanded
     */
    public Boolean getExpanded() {
        return expanded;
    }

    /**
     * 节点是否默认展开
     *
     * @param expanded the expanded to set
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * 节点图标
     *
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * 节点图标
     *
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * 节点类型
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * 节点类型
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 是否叶节点
     *
     * @return the isLeaf
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * 是否叶节点
     *
     * @param isLeaf the isLeaf to set
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 节点悬浮框显示内容
     *
     * @return the qtip
     */
    public String getQtip() {
        return qtip;
    }

    /**
     * 节点悬浮框显示内容
     *
     * @param qtip the qtip to set
     */
    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    /**
     * 节点明细信息
     *
     * @return the attributes
     */
    public Object getAttributes() {
        return attributes;
    }

    /**
     * 节点明细信息
     *
     * @param attributes the attributes to set
     */
    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

}
