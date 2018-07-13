package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.ScheduleJob;

public interface ScheduleJobService extends BaseService<ScheduleJob, Long>{
	/** 新增定时任务*/	
	public boolean addScheduleJob(ScheduleJob scheduleJob) ;	
	/** 删除定时任务 */	
	public boolean delScheduleJob(Long scheduleJobId) ;
	/** 更新定时任务*/	
	public boolean updateScheduleJob(ScheduleJob scheduleJob);
	/** 装载定时任务 */	
	public ScheduleJob loadScheduleJob(Long scheduleJobId);	
	/** 定时任务总共数目 */	
	public int countAll(
			Long jobId,
			Integer type
			);	
	/** 分页定时任务信息 */
	public List<ScheduleJob> browsePagingScheduleJob(
			Long jobId,
			Integer type,
			int pageNum,int pageSize,String orderName,String orderWay) ;		


}
