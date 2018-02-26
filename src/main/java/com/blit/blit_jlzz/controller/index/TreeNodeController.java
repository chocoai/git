/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.controller.index;

import com.blit.blit_jlzz.service.index.TreeNodeService;
import com.blit.lp.core.context.User;
import com.blit.lp.jf.config.LPController;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;

/**
 * 首页树节点控制器类
 *
 * @author caibenxiang
 */
@LPController(controllerkey = "/api/v1/index/tree")
public class TreeNodeController extends Controller {

    private final TreeNodeService tree_api_service = Duang.duang(TreeNodeService.class);

    /**
     * 实现配电网树形查询
     *
     * @param node
     */
    public void leftTree(@Para("node") String node) {
        renderJson(tree_api_service.leftTree(node, User.getCurrUserNum()));
    }

    /**
     * 实现输电网树形查询
     *
     * @param node
     */
    public void leftTreeTrans_leftTree(@Para("node") String node) {
        renderJson(tree_api_service.leftTreeTrans_leftTree(node, User.getCurrUserNum()));
    }

    /**
     * 实现特殊定制格式的电网结构树
     *
     * @param node
     */
    public void leftTreeCustom(@Para("node") String node) {
        //renderJson(tree_api_service.leftTreeCustom(node, User.getCurrUserNum()));
    }
}
