package com.blit.lp.bus.flow.actor;

import java.util.ArrayList;
import java.util.List;

import com.blit.lp.bus.flow.FlowUser;

/**
 * 流程启动者参与者
 * @author dkomj
 *
 */
public class FlowActorCreater extends FlowActor {

	@Override
	public List<FlowUser> getActors() {
		List<FlowUser> list = new ArrayList<FlowUser>();
		FlowUser u = this.flowInstance.getCreateUser();
		list.add(u);
		return list;
	}

}
