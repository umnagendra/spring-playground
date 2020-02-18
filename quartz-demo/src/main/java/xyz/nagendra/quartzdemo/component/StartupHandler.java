package xyz.nagendra.quartzdemo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.nagendra.quartzdemo.entity.JobInfo;
import xyz.nagendra.quartzdemo.service.ConfigService;

@Component
public class StartupHandler implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupHandler.class);

    private final ConfigService configService;

    @Autowired
    public StartupHandler(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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

        for (int i = 0; i < 100; i++) {
            configService.save(createJobInfo("TEST-001-" + System.nanoTime()))
                    .onFailure(throwable -> LOGGER.error("Failed to save job!", throwable));
        }
    }

    private JobInfo createJobInfo(String name) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setName(name);
        jobInfo.setGroup("demo group");

        jobInfo.setCron(true);
        jobInfo.setCronExpression("*/1 * * * *");
        jobInfo.setJobClass("com.example.com.ExampleJob");

        return jobInfo;
    }
}