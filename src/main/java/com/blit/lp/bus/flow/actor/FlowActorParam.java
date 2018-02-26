package com.blit.lp.bus.flow.actor;

import java.util.ArrayList;
import java.util.List;

import com.blit.lp.bus.flow.FlowUser;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程参数参与者
 * @author dkomj
 *
 */
public class FlowActorParam extends FlowActor {

	@Override
	public List<FlowUser> getActors() {
		List<FlowUser> list = new ArrayList<FlowUser>();
		String parambm = this.jsonObj.getString("data");
		String val = this.flowInstance.getParam(parambm);
		Record userRow = Db.findFirst("select * from lp_sys_user where id=?", val);
		list.add(new FlowUser(userRow));
		return list;
	}

}
