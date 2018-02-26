/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.controller.demo;

import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 *
 * @author caibenxiang
 */
@LPController(controllerkey = "/demo")
public class DemoController extends Controller {
    
    public void index() {
        SqlPara sqlPara = Db.getSqlPara("system.demo");
        renderText(sqlPara.toString());
    }
}
