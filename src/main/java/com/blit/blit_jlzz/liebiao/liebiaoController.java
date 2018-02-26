/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.liebiao;

import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;

/**
 *
 * @author Administrator
 */
@LPController(controllerkey="/liebiao")
public class liebiaoController extends Controller{
    
    public void index(){
        renderTemplate("/org/list/caijidian_list.html");
    }
}
