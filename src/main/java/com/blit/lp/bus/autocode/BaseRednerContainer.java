package com.blit.lp.bus.autocode;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.params.Params;

import com.jfinal.kit.JMap;
import com.jfinal.template.Engine;

public class BaseRednerContainer implements IRender {
	List<IRender> list = new ArrayList<IRender>();
	
	public void add(IRender render){
		list.add(render);
	}
	
	private String name;
	public BaseRednerContainer(String name,IRender... params){
		this.name = name;
		if(params != null){
			for(int i=0; i < params.length; i++){
				list.add(params[i]);
			}
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void render(Engine engine, JMap data) throws Exception{
		for (IRender render : list) {
			render.render(engine, data);
		}
	}

}
