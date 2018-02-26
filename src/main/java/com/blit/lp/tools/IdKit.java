package com.blit.lp.tools;

import com.jfinal.kit.PropKit;

public class IdKit {
	public static SnowflakeIdWorker idWorker = null;
	static{
		int workerId=PropKit.use("global.properties").getInt("snowflake.workerid", 0);
		int datacenterId=PropKit.use("global.properties").getInt("snowflake.datacenterid", 0);
		idWorker = new SnowflakeIdWorker(workerId, datacenterId);
	}
	
	public static long nextId(){
		return idWorker.nextId();
	}
}
