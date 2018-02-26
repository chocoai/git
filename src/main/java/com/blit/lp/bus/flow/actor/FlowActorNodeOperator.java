package com.blit.lp.bus.flow.actor;

import java.util.ArrayList;
import java.util.List;

import com.blit.lp.bus.flow.FlowUser;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程节点处理人参与者
 * @author dkomj
 *
 */
public class FlowActorNodeOperator extends FlowActor {

	@Override
	public List<FlowUser> getActors() {
		List<FlowUser> list = new ArrayList<FlowUser>();
		Record flowInstanceRow = this.flowInstance.getFlowInstanceRow();
		String nodeid = this.jsonObj.getString("data");
		List<Record> userList = Db.find("select * from lp_sys_user where id in (select o.operatorid from lp_wfi_operator o join lp_wfi_flownode ni on o.flownodeinstanceid=ni.id where ni.flowinstanceid=? and ni.flownodeid=?) order by px,username"
				, flowInstanceRow.getStr("id"), nodeid);
		for (Record u : userList) {
			list.add(new FlowUser(u));
		}
		return list;
	}

}
