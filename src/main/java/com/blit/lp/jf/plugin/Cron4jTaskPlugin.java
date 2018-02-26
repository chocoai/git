package com.blit.lp.jf.plugin;

import java.util.List;

import it.sauronsoftware.cron4j.InvalidPatternException;
import it.sauronsoftware.cron4j.Scheduler;

import com.blit.lp.tools.Reflect;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Cron4j 定时任务插件
 *
 */
public class Cron4jTaskPlugin implements IPlugin {

	private static List<Record> jobList = null;
	private static Scheduler  scheduler = null;
	
	public static synchronized void refreshJob(){
		jobList = Db.find("select * from lp_sys_task where status='1' order by id");
		if(scheduler != null){
			scheduler.stop();
		}
		scheduler = new Scheduler();
		for (Record row : jobList) {
			try {
				String exp = row.getStr("exp");
				String clsName = row.getStr("class");
				Class<Runnable> clazz = Reflect.on(clsName).get();
				scheduler.schedule(exp, clazz.newInstance());
				String info = String.format("启动定时任务：%s;%s;%s", row.getStr("taskname"), exp,clsName);
				System.out.println(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		scheduler.start();
	}
	
	@Override
	public boolean start() {
		refreshJob();
		return true;
	}
	
	@Override
	public boolean stop() {
		scheduler.stop();
		return true;
	}

}
