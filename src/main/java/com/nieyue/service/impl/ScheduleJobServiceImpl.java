package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.ScheduleJob;
import com.nieyue.dao.ScheduleJobDao;
import com.nieyue.schedule.QuartzEventService;
import com.nieyue.service.ScheduleJobService;
/**
 * 定时任务实现类
 * @author 聂跃
 * @date 2017年7月29日
 */
@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob,Long> implements ScheduleJobService {
	@Resource
	ScheduleJobDao scheduleJobDao;
	@Resource
	QuartzEventService quartzEventService;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addScheduleJob(ScheduleJob scheduleJob) {
		scheduleJob.setCreateDate(new Date());
		scheduleJob.setUpdateDate(new Date());
		scheduleJob.setJobName("com.nieyue.schedule.QuartzJob");
		scheduleJob.setJobGroup(UUID.randomUUID().toString());
		scheduleJob.setMethodName("execute");
		
		boolean b = scheduleJobDao.addScheduleJob(scheduleJob);
		quartzEventService.addScheduleJob(scheduleJob);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delScheduleJob(Long scheduleJobId) {
		ScheduleJob sj = scheduleJobDao.loadScheduleJob(scheduleJobId);
		boolean b = scheduleJobDao.delScheduleJob(scheduleJobId);
		quartzEventService.delScheduleJob(sj.getJobName(), sj.getJobGroup());
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateScheduleJob(ScheduleJob scheduleJob) {
		scheduleJob.setUpdateDate(new Date());
		boolean b = scheduleJobDao.updateScheduleJob(scheduleJob);
		quartzEventService.delScheduleJob(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		quartzEventService.addScheduleJob(scheduleJob);
		return b;
	}
	@Override
	public ScheduleJob loadScheduleJob(Long scheduleJobId) {
		ScheduleJob sj = scheduleJobDao.loadScheduleJob(scheduleJobId);
		return sj;
	}
	@Override
	public int countAll(Long jobId,
			Integer type) {
		int c = scheduleJobDao.countAll(jobId, type);
		return c;
	}
	@Override
	public List<ScheduleJob> browsePagingScheduleJob(
			Long jobId,
			Integer type,
			int pageNum, int pageSize, String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<ScheduleJob> l = scheduleJobDao.browsePagingScheduleJob(
				 jobId,
				 type,
				pageNum-1, pageSize, orderName, orderWay);
		return l;
	}


}
