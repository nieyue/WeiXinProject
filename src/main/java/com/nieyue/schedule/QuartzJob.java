package com.nieyue.schedule;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.nieyue.service.KfMessageService;
public class QuartzJob implements Job{
	@Autowired
	RedisLock redisLock;
	@Autowired
	KfMessageService kfMessageService;


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
			try {
				JobDataMap jdm = arg0.getJobDetail().getJobDataMap();
				int type=(int) jdm.get("type");
				Long jobId=(Long) jdm.get("jobId");
				//分布式锁
				if(redisLock.checkStatus(type,jobId)){
					//System.out.println("jobId"+jdm.get("jobId")+new Date().toLocaleString());
					if(type==1){
						//客服消息
						kfMessageService.sendKfMessage(jobId);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


}
