package com.webdatabase.dgz.job;

import java.util.Optional;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.webdatabase.dgz.model.Message;
import com.webdatabase.dgz.repository.MessageRepository;

public class MessageJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(MessageJob.class);

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        /* Get message id recorded by scheduler during scheduling */
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String messageId = dataMap.getString("messageId");

        log.info("Executing job for message id {}", messageId);

        /* Get message from database by id */
        long id = Long.parseLong(messageId);
        Optional<Message> messageOpt = messageRepository.findById(id);

        /* update message visible in database */
        Message message = messageOpt.get();
        message.setVisible(true);
        messageRepository.save(message);

        /* unschedule or delete after job gets executed */

        /*try {
            context.getScheduler().deleteJob(new JobKey(messageId));

            TriggerKey triggerKey = new TriggerKey(messageId);

            context.getScheduler().unscheduleJob(triggerKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
    }

}