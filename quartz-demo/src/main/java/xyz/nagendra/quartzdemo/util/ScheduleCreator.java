package xyz.nagendra.quartzdemo.util;

import io.vavr.control.Try;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Date;

public class ScheduleCreator {

    private ScheduleCreator() {
    }

    public static Try<JobDetail> createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, String jobName, String jobGroup, ApplicationContext context) {
        return Try.of(() -> {
            JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
            jobDetailFactoryBean.setJobClass(jobClass);
            jobDetailFactoryBean.setApplicationContext(context);
            jobDetailFactoryBean.setName(jobName);
            jobDetailFactoryBean.setGroup(jobGroup);
            jobDetailFactoryBean.setDurability(isDurable);

            // set job data map
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAll(System.getenv());
            jobDetailFactoryBean.setJobDataMap(jobDataMap);

            jobDetailFactoryBean.afterPropertiesSet();
            return jobDetailFactoryBean.getObject();
        });
    }

    public static Try<CronTrigger> createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction) {
        return Try.of(() -> {
            CronExpression.validateExpression(cronExpression);

            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setName(triggerName);
            cronTriggerFactoryBean.setMisfireInstruction(misFireInstruction);
            cronTriggerFactoryBean.setStartTime(startTime);
            cronTriggerFactoryBean.setCronExpression(cronExpression);

            cronTriggerFactoryBean.afterPropertiesSet();
            return cronTriggerFactoryBean.getObject();
        });
    }

    public static Try<SimpleTrigger> createSimpleTrigger(String triggerName, Date startTime, long repeatIntervalMs, int misFireInstruction) {
        return Try.of(() -> {
            SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
            simpleTriggerFactoryBean.setName(triggerName);
            simpleTriggerFactoryBean.setMisfireInstruction(misFireInstruction);
            simpleTriggerFactoryBean.setStartTime(startTime);
            simpleTriggerFactoryBean.setRepeatInterval(repeatIntervalMs);
            simpleTriggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);

            simpleTriggerFactoryBean.afterPropertiesSet();
            return simpleTriggerFactoryBean.getObject();
        });
    }
}
