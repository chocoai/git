package com.blit.lp.bus.flow.actor;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blit.lp.bus.flow.FlowUser;
import com.blit.lp.core.context.User;
import com.blit.lp.jf.interceptor.accesscontrol.LPDeptAccess;
import com.blit.lp.tools.DSqlKit;
import com.blit.lp.tools.DbEx;
import com.blit.lp.tools.TreeSqlKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.DbSqlBuiler;

/**
 * 流程列表()参与者
 * @author dkomj
 *
 */
public class FlowActorList extends FlowActor {

	@Override
	public List<FlowUser> getActors() {
		List<FlowUser> list = new ArrayList<FlowUser>();
		JSONArray actorAr = this.jsonObj.getJSONArray("data");
		String filter = this.jsonObj.containsKey("filter")
				 ? this.jsonObj.getString("filter") : "";
		DSqlKit.init("");
		boolean isInit = false;
		for (int i=0; i < actorAr.size(); i++) {
			JSONObject o = actorAr.getJSONObject(i);
			String id = o.getString("id");
			String name = o.getString("name");
			String actortype = o.getString("actortype");
			if(isInit){
				DSqlKit.append(" union ");
			}
			isInit = true;
			
			if(actortype.equals("人员")){
				DSqlKit.append(" select u.* from lp_sys_user u where id=?", id);
			}
			else if(actortype.equals("角色")){
				DSqlKit.append(" select u.* from lp_sys_user u where id in (select userid from lp_sys_userrole where roleid=?)", id);
			}
			else if(actortype.equals("机构")){
				String sql = TreeSqlKit.GetChildSql("select id"
						, "lp_sys_dept"
						, "id='" +id.replaceAll("'", "''")+ "'"
						, "id", "pid", 5);
				
				DSqlKit.append(" select u.* from lp_sys_user u where deptid in (" +sql+ ")");
			}
		}
		
		User currUser =  User.getCurrUser();
		String filterWhere = "1=1";
		if(currUser != null && StrKit.notBlank(filter)){
			String dwid = currUser.get("dwid");
			String bmid = currUser.get("bmid");
			String deptid = currUser.get("deptid");
			if(filter.equals("本单位")){
				if(StrKit.notBlank(dwid)){
					DSqlKit.append("", dwid);
					filterWhere = "dwid=?";
				}
				else{
					filterWhere = "1<>1";
				}
			}
			else if(filter.equals("本部门")){
				if(StrKit.notBlank(bmid)){
					DSqlKit.append("", bmid);
					filterWhere = "bmid=?";
				}
				else{
					filterWhere = "1<>1";
				}
			}
			else if(filter.equals("本机构")){
				DSqlKit.append("", deptid);
				filterWhere = "deptid=?";
			}
		}
		String sql = "select * from (" +DSqlKit.getSql()+ ") t where "+filterWhere+" order by px,username";
		List<Record> rows = Db.find(sql,DSqlKit.getParamList());
		for (Record u : rows) {
			list.add(new FlowUser(u));
		}
		return list;
	}

}
