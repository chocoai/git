package com.blit.lp.bus.autocode;

import com.jfinal.kit.JMap;
import com.jfinal.template.Engine;

public interface IRender {
	String getName();
	void render(Engine engine,JMap data) throws Exception;
}
