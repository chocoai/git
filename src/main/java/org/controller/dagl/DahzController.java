/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller.dagl;

import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

/**
 *
 * @author Administrator
 */
@LPController(controllerkey="/Dahz")
public class DahzController extends Controller{
    
    public void index(){
        String id = getPara("id");
        String type = getPara("type");
        setAttr("id", id);
        setAttr("type", type);
       renderTemplate("/org/list/YeQian.html");
    }
    
    
}
