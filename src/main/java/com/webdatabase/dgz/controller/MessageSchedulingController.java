package com.webdatabase.dgz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Date;
import java.util.Optional;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.webdatabase.dgz.dto.MessageDto;
import com.webdatabase.dgz.job.MessageJob;
import com.webdatabase.dgz.model.Message;
import com.webdatabase.dgz.model.MsecDetail;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.model.SupplierMember;
import com.webdatabase.dgz.repository.MessageRepository;
@RestController
@RequestMapping(path = "/messages")
public class MessageSchedulingController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping(path = "/schedule-visibility")
    public @ResponseBody MessageDto scheduleMessageVisibility(@RequestBody MessageDto messageDto) {
        try {
            // save messages in table
            Message message = new Message();
            message.setContent(messageDto.getContent());
            message.setVisible(false);
            message.setMakeVisibleAt(messageDto.getMakeVisibleAt());

            message = messageRepository.save(message);

            // Creating JobDetail instance
            String id = String.valueOf(message.getId());
            JobDetail jobDetail = JobBuilder.newJob(MessageJob.class).withIdentity(id).build();

            // Adding JobDataMap to jobDetail
            jobDetail.getJobDataMap().put("messageId", id);

            // Scheduling time to run job
            //Date triggerJobAt = new Date(message.getMakeVisibleAt());

            /*SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(id)
                    .startAt(triggerJobAt).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();*/
            Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(id, "group1")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ? *").withMisfireHandlingInstructionFireAndProceed())
            //.forJob("myJob", "group1")
            .build();
            
            // Getting scheduler instance
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            
            

            messageDto.setStatus("SUCCESS");

        } catch (SchedulerException e) {
            // scheduling failed
            messageDto.setStatus("FAILED");
            e.printStackTrace();
        }
        return messageDto;
    }


    @DeleteMapping(path = "/{messageId}/unschedule-visibility")
    public @ResponseBody MessageDto unscheduleMessageVisibility(
            @PathVariable(name = "messageId") long messageId) {

        MessageDto messageDto = new MessageDto();

        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (!messageOpt.isPresent()) {
            messageDto.setStatus("Message Not Found");
            return messageDto;
        }

        Message message = messageOpt.get();
        message.setVisible(false);
        messageRepository.save(message);

        String id = String.valueOf(message.getId());

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            scheduler.deleteJob(new JobKey(id));
            TriggerKey triggerKey = new TriggerKey(id);
            scheduler.unscheduleJob(triggerKey);
            messageDto.setStatus("SUCCESS");

        } catch (SchedulerException e) {
            messageDto.setStatus("FAILED");
            e.printStackTrace();
        }
        return messageDto;
    }



	@GetMapping(path = "/list-job",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listJob() throws JSONException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JSONObject response = new JSONObject();
		response.put("message", "Job list");
		List<String> items = new ArrayList<String>();
		   try {
			for (String groupName : scheduler.getJobGroupNames()) {

			     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
			                
			      String jobName = jobKey.getName();
			      String jobGroup = jobKey.getGroup();
			                
			      //get job's trigger
			      List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
			      Date nextFireTime = triggers.get(0).getNextFireTime(); 

			      String item = "[jobName] : " + jobName + " [groupName] : "
				            + jobGroup + " - " + nextFireTime;
			        System.out.println(item);
			        items.add(item);
			      }
			    }
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.accumulate("list", items);
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
}
