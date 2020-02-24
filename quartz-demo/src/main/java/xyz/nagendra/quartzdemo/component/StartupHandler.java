package xyz.nagendra.quartzdemo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.nagendra.quartzdemo.entity.JobInfo;
import xyz.nagendra.quartzdemo.service.ConfigService;
import xyz.nagendra.quartzdemo.service.SchedulerService;

@Component
public class StartupHandler implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupHandler.class);

    private final ConfigService configService;
    private final SchedulerService schedulerService;

    @Autowired
    public StartupHandler(ConfigService configService, SchedulerService schedulerService) {
        this.configService = configService;
        this.schedulerService = schedulerService;
    }

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("--- START ---");

        LOGGER.info("TODOs:" +
                "------" +
                "1. Containerize app" +
                "2. Multiple instances - clustering" +
                "3. REST APIs for creating+managing jobs" +
                "4. Improve DB schema" +
                "5. Fix + add unit tests for REST API flows" +
                "6. API auth - basic" +
                "7. API auth - OAuth (cisco webex / anything open)" +
                "8. Swagger documentation for all APIs" +
                "------");

        LOGGER.info("Deleting all stored jobs in DB ...");
        configService.deleteAll()
                .onSuccess(aVoid -> {
                    LOGGER.info("Successfully deleted all jobs in DB");
                    configService.save(createJobInfo("TEST-SIMPLE-JOB"));
                })
                .onFailure(throwable -> LOGGER.info("Failed to delete all jobs in DB.", throwable));

        configService.list()
                .onSuccess(jobInfos -> LOGGER.info("There are {} job(s) in the DB.", jobInfos.size()))
                .onFailure(throwable -> LOGGER.error("Failed to access jobs from DB.", throwable));

        schedulerService.deleteJob("TEST-SIMPLE-JOB");
        schedulerService.scheduleAllJobs();
    }

    private JobInfo createJobInfo(String name) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setName(name);
        jobInfo.setGroup("demo group");

        jobInfo.setCron(false);
        jobInfo.setRepeatTimes(60000L);
        jobInfo.setJobClass("xyz.nagendra.quartzdemo.job.SimpleTestJob");

        return jobInfo;
    }
}
