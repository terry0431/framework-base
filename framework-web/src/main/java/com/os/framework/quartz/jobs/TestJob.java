package com.os.framework.quartz.jobs;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author wangbo
 * @date 2017-4-20 10:46:32
 */
public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
	}
}
