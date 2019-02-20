package com.os.framework.quartz;

import com.os.framework.db.dao.MainDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangbo
 * @date 2017-4-20 9:53:53
 */
public class ScheduleUtil {
	Logger log = LoggerFactory.getLogger(ScheduleUtil.class);
	/**
	 * 添加任务到任务队列
	 *
	 * @param job
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public ScheduleJob addJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return null;
		}
		SchedulerFactory sf = new StdSchedulerFactory();

		Scheduler scheduler = sf.getScheduler();
		//log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个  
		if (null == trigger) {
			Class clazz = Class.forName(job.getBeanClass());

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置  
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger  
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行  
			scheduler.rescheduleJob(triggerKey, trigger);
		}

		scheduler.start();

		// 得到任务下一次的计划执行时间  
		Date nextProcessTime = trigger.getNextFireTime();
		job.setNextProcessTime(nextProcessTime);
		return job;
	}

	/**
	 * 服务器启动，加载job_status=1的任务到任务队列中
	 *
	 */
	@PostConstruct
	public void init()  {

		try {
			System.out.println("======== schedule init");
			log.info("schedule init ========");

			ScheduleJob job = new ScheduleJob();
			//			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.ShebeiJiankong");
			//			job.setJobStatus("1");
			//			job.setJobName("job1");
			//			job.setJobGroup("group1");
			//			job.setCronExpression("0 0/5 * * * ?");
			//                        addJob(job);
			//                        
			//                        job = new ScheduleJob();
			//			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.ShebeiJiankong_ycz");
			//			job.setJobStatus("1");
			//			job.setJobName("job2");
			//			job.setJobGroup("group1");
			//			job.setCronExpression("0 0/5 * * * ?");
			//			addJob(job);
			//
			//微信token定时更新
			job.setBeanClass("com.os.framework.quartz.jobs.WxTokenJob");
			job.setJobStatus("1");
			job.setJobName("job1");
			job.setJobGroup("group1");
			job.setCronExpression("0 0/15 * * * ?");

			MainDao dao = new MainDao();
			// 水质采集器多层水开关设置
			String sql  = "select distinct s_time from zhyy_shuiceng_shezhi order by s_time ";
			List<Map<String,Object>> list = dao.queryForList(sql, null);
			String timestr = "";
			for(Map m : list){
				if(!timestr.equals("")){
					timestr += ",";
				}
				timestr += m.get("s_time");
			}
			System.out.println("corn : " + timestr);
			job = new ScheduleJob();
			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.ShuiCengKongzhi");
			job.setJobStatus("1");
			job.setJobName("job3");
			job.setJobGroup("group1");
			job.setCronExpression("0 "+timestr+" * * * ?");
			addJob(job);

			// 养水机自动开关机控制
			sql  = "select distinct y_time from zhyy_yangshuiji_shezhi order by y_time ";
			list = dao.queryForList(sql, null);
			timestr = "";
			for(Map m : list){
				if(!timestr.equals("")){
					timestr += ",";
				}
				timestr += m.get("y_time");
			}
			System.out.println("corn : " + timestr);
			job = new ScheduleJob();
			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.YangshuijiKongzhi");
			job.setJobStatus("1");
			job.setJobName("job4");
			job.setJobGroup("group1");
			job.setCronExpression(timestr + " 0 * * * ?");
			addJob(job);
			//获取设备状态 饵料池输水设备
			job = new ScheduleJob();
			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.EContorlJob");
			job.setJobStatus("1");
			job.setJobName("job5");
			job.setJobGroup("group1");
			job.setCronExpression("0 * * * * ?");

			//                        job = new ScheduleJob();
			//			job.setBeanClass("com.os.framework.quartz.jobs.bundle.zhyy.DataBuilder");
			//			job.setJobStatus("1");
			//			job.setJobName("job3");
			//			job.setJobGroup("group1");
			//			job.setCronExpression("0 * * * * ?");
			//			addJob(job);

			//Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 这里获取任务信息数据  
			//List<ScheduleJob> jobList = ScheduleUtil.getAllJobs();

			//for (ScheduleJob job : jobList) {
			//	addJob(job);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	/**
	//	 * 获取所有计划中的任务列表
	//	 *
	//	 * @return
	//	 * @throws SchedulerException
	//	 */
	//	@Deprecated
	//	public List<ScheduleJob> getAllJob() throws SchedulerException {
	//		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	//		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
	//		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
	//		List<ScheduleJob> jobList = new ArrayList();
	//		for (JobKey jobKey : jobKeys) {
	//			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
	//			for (Trigger trigger : triggers) {
	//				ScheduleJob job = new ScheduleJob();
	//				job.setJobName(jobKey.getName());
	//				job.setJobGroup(jobKey.getGroup());
	//				job.setDescription("触发器:" + trigger.getKey());
	//				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
	//				job.setJobStatus(triggerState.name());
	//				if (trigger instanceof CronTrigger) {
	//					CronTrigger cronTrigger = (CronTrigger) trigger;
	//					String cronExpression = cronTrigger.getCronExpression();
	//					job.setCronExpression(cronExpression);
	//				}
	//				jobList.add(job);
	//			}
	//		}
	//		return jobList;
	//	}

	/**
	 * 获取所有正在运行的job
	 *
	 * @return
	 * @throws SchedulerException
	 */
	@Deprecated
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 *
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Deprecated
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 *
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Deprecated
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 *
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行job
	 *
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 *
	 * @param scheduleJob
	 * @return 
	 * @throws SchedulerException
	 */
	public ScheduleJob updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
		// 得到任务下一次的计划执行时间  
		Date nextProcessTime = trigger.getNextFireTime();
		scheduleJob.setNextProcessTime(nextProcessTime);

		return scheduleJob;
	}
}
