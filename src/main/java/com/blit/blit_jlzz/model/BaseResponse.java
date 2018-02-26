/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model;

/**
 * API返回对象基类
 *
 * @author caibenxiang
 */
public class BaseResponse {

    public BaseResponse() {
    }

    public BaseResponse(String kind, String version) {
        this.kind = kind;
        this.version = version;
    }

    /**
     * 回复数据API分类
     */
    private String kind;

    /**
     * 回复数据API版本
     */
    private String version;

    /**
     * 回复数据元数据
     */
    private Metadata metadata;

    /**
     * 回复数据API分类
     *
     * @return the kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * 回复数据API分类
     *
     * @param kind the kind to set
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 回复数据API版本
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 回复数据API版本
     *
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 回复数据元数据
     *
     * @return the metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * 回复数据元数据
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
