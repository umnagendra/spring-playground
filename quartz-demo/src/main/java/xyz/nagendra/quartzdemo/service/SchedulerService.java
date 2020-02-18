package xyz.nagendra.quartzdemo.service;

import io.vavr.control.Try;
import xyz.nagendra.quartzdemo.entity.JobInfo;

public interface SchedulerService {
    Try<Void> scheduleAllJobs();
    Try<Void> scheduleNewJob(JobInfo jobInfo);
    Try<Void> updateScheduledJob(JobInfo jobInfo);
    Try<Void> unscheduleJob(String jobName);
    Try<Void> deleteJob(String jobName);
    Try<Void> pauseJob(String jobName);
    Try<Void> resumeJob(String jobName);
    Try<Void> startJobNow(String jobName);
}
