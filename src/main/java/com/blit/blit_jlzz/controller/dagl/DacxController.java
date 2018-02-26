/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.controller.dagl;

import com.blit.blit_jlzz.service.dagl.YhcxService;
import com.blit.lp.jf.config.LPController;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;

/**
 * 档案查询控制器
 *
 * @author caibenxiang
 */
@LPController(controllerkey = "/api/v1/dagl/dacx")
public class DacxController extends Controller {

    private final YhcxService dacx_api_service = Duang.duang(YhcxService.class);
    
    /**
     * 用于查询用户档案列表
     *
     * @param nodeValue 单位编码
     * @param yhmcValue 用户名称
     * @param yhbhValue 用电户号
     * @param yhlxType 用户类型编码
     * @param yhztType 用户状态编码
     * @param dydjType 电压等级编码
     * @param start 查询起始页码
     * @param limit 每页的行数
     */
    public void listYHDA(
            @Para("nodeValue") String nodeValue,
            @Para("yhmcValue") String yhmcValue,
            @Para("yhbhValue") String yhbhValue,
            @Para("yhlxType") String yhlxType,
            @Para("yhztType") String yhztType,
            @Para("dydjType") String dydjType,
            @Para("start") int start,
            @Para("limit") int limit
    ) {
        Kv kv = Kv.create()
                .set("nodeValue", nodeValue)
                .set("yhmcValue", yhmcValue)
                .set("yhbhValue", yhbhValue)
                .set("yhlxType", yhlxType)
                .set("yhztType", yhztType)
                .set("dydjType", dydjType);
        renderJson(dacx_api_service.listYHDA(kv, start, limit));
    }
}
