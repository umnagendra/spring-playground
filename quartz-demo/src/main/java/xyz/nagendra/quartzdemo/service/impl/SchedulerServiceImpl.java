package xyz.nagendra.quartzdemo.service.impl;

import io.vavr.control.Try;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import xyz.nagendra.quartzdemo.entity.JobInfo;
import xyz.nagendra.quartzdemo.service.ConfigService;
import xyz.nagendra.quartzdemo.service.SchedulerService;
import xyz.nagendra.quartzdemo.util.ScheduleCreator;

import java.util.Date;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    private final ConfigService configService;
    private final SchedulerFactoryBean schedulerFactoryBean;
    private final ApplicationContext context;

    @Autowired
    public SchedulerServiceImpl(ConfigService configService, SchedulerFactoryBean schedulerFactoryBean, ApplicationContext context) {
        this.configService = configService;
        this.schedulerFactoryBean = schedulerFactoryBean;
        this.context = context;
    }

    @Override
    public Try<Void> scheduleAllJobs() {
        return configService.list().map(jobInfos -> {
            jobInfos.forEach(this::scheduleJob);
            return null;
        });
    }

    @Override
    public Try<Void> scheduleNewJob(JobInfo jobInfo) {
        return scheduleJob(jobInfo)
                .onSuccess(aVoid -> configService.save(jobInfo));
    }

    @Override
    public Try<Void> updateScheduledJob(JobInfo jobInfo) {
        return Try.run(() -> {
            Trigger newTrigger = createTrigger(jobInfo).get();
            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getName()), newTrigger);
        }).onSuccess(aVoid -> configService.save(jobInfo));
    }

    @Override
    public Try<Void> unscheduleJob(String jobName) {
        return Try.run(() -> schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName)));
    }

    @Override
    public Try<Void> deleteJob(String jobName) {
        return Try.run(() -> schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobName)));
    }

    @Override
    public Try<Void> pauseJob(String jobName) {
        return Try.run(() -> schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobName)));
    }

    @Override
    public Try<Void> resumeJob(String jobName) {
        return Try.run(() -> schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobName)));
    }

    @Override
    public Try<Void> startJobNow(String jobName) {
        return Try.run(() -> schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobName)));
    }

    private Try<Void> scheduleJob(JobInfo jobInfo) {
        return Try.run(() -> {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobInfo.getJobClass()))
                    .withIdentity(jobInfo.getName(), jobInfo.getGroup()).build();

            if (!scheduler.checkExists(jobDetail.getKey())) {
                Trigger trigger = createTrigger(jobInfo).get();
                jobDetail = ScheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, jobInfo.getName(), jobInfo.getGroup(), context).get();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                LOGGER.info("Job {} already exists", jobInfo);
            }
        })
                .onSuccess(x -> LOGGER.info("Successfully scheduled job {}", jobInfo))
                .onFailure(throwable -> LOGGER.error("Failed to schedule job {}", jobInfo, throwable));
    }

    private Try<? extends Trigger> createTrigger(JobInfo jobInfo) {
        Try<? extends Trigger> triggerOrErr;
        if (Boolean.TRUE.equals(jobInfo.getCron())) {
            triggerOrErr = ScheduleCreator.createCronTrigger(jobInfo.getName(), new Date(), jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
            triggerOrErr = ScheduleCreator.createSimpleTrigger(jobInfo.getName(), new Date(), jobInfo.getRepeatTimes(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        return triggerOrErr;
    }
}
