package com.blit.blit_jlzz.controller.yccb;

import com.blit.lp.jf.config.LPController;
import com.jfinal.core.Controller;

/**
 * 
 * @File:   YccbController.java
 * @Author: daiqing
 * @Date:   2018年1月31日
 * @Com:    鼎信
 * @Des:
 */
@LPController(controllerkey="/yccb/sgzc")
public class SgzcController extends Controller{

	/**
	 * 去到抄表数据项页面
	 */
	public void toZcSjx() {
		renderTemplate("/yccb/sgzc/zcsjx.html");
	}
}
