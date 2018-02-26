package com.blit.lp.jf.controller.demo;

import java.util.ArrayList;
import java.util.List;

import com.blit.lp.bus.flow.FlowInstance;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.bus.flow.i.LPFlowActor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程自定义参数示例
 * @author dkomj
 *
 */
public class MyFlowActor extends LPFlowActor  {

	@Override
	public List<FlowUser> loadActors(FlowInstance flowInstance,
			Record flowNodeRow) {
		String flownodename = flowNodeRow.getStr("flownodename");
		List<FlowUser> list = new ArrayList<FlowUser>();
		List<Record> userList = Db.find("select * from lp_sys_user order by px,username");
		for (Record u : userList) {
			list.add(new FlowUser(u));
		}
		return list;
	}

}
