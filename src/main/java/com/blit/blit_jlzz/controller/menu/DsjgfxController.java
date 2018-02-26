/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.controller.menu;

import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;

/**
 *
 * @author admin
 */
@LPController(controllerkey="/Dsjgfx")
public class DsjgfxController extends Controller {
    public void index() {
		renderTemplate("/sys/menu/Dsjgfx.html");
	}
}
