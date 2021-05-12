package com.webdatabase.dgz.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.webdatabase.dgz.service.GovServices;
import com.webdatabase.dgz.service.JobService;


public class CronJob extends QuartzJobBean implements InterruptableJob{
	
	private volatile boolean toStopFlag = true;

	@Autowired
	private GovServices _govServices; 
	@Autowired
	JobService jobService;

	static final String MSECData = "Обновление данных МТСР - МСЭК";
	static final String LICENSEData = "Обновление данных Госархстрой - Лицензии";
	static final String DEBTData = "Обновление данных ГНС - Задолженность";
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		System.out.println("Cron Job started with key :" + key.getName() + ", Group :"+key.getGroup() + " , Thread Name :"+Thread.currentThread().getName() + " ,Time now :"+new Date());
		
		System.out.println("======================================");
		System.out.println("Accessing annotation example: "+jobService.getAllJobs());
		List<Map<String, Object>> list = jobService.getAllJobs();
		System.out.println("Job list :"+list);
		System.out.println("======================================");
		
		//*********** For retrieving stored key-value pairs ***********/
		JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
		String myValue = dataMap.getString("myKey");
		System.out.println("Value:" + myValue);

		if(key.getName().equals(MSECData)) {
			System.out.println("Выполняю запрос в МСЭК...");
			int resCount =  _govServices.initMsecAll(null);
			System.out.println("Запрос в МСЭК обработал " + resCount + " записей.");	
		}
		else if(key.getName().equals(LICENSEData)) {
			System.out.println("Выполняю " + LICENSEData);
			//TODO: Call service
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Выполнено " + LICENSEData);
		}
		else if(key.getName().equals(DEBTData)) {
			System.out.println("Выполняю " + DEBTData);
			//TODO: Call service
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Выполнено " + DEBTData);	
		}
		else {
			System.out.println("Неизвестная задача - " + key.getName());
		}
		//System.out.println("Thread: "+ Thread.currentThread().getName() +" stopped.");
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Stopping thread... ");
		toStopFlag = false;
	}

}