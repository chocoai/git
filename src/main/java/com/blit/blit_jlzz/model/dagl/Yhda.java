/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.model.dagl;

import com.blit.blit_jlzz.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author caibenxiang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Yhda extends BaseResponse {

    /**
     * 表示服务返回。
     */
    private List<YhdaSpec> response;

    /**
     * 表示服务返回。
     *
     * @return the response
     */
    public List<YhdaSpec> getResponse() {
        return response;
    }

    /**
     * 表示服务返回。
     *
     * @param response the response to set
     */
    public void setResponse(List<YhdaSpec> response) {
        this.response = response;
    }

}
