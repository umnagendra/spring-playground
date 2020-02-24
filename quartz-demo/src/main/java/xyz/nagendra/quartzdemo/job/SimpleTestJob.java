package xyz.nagendra.quartzdemo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.stream.IntStream;

public class SimpleTestJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("--- START ---");
        IntStream.range(0, 5).forEach(i -> {
            LOGGER.info("Counting - {} ...", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("Interrupted", e);
            }
        });
        LOGGER.info("--- COMPLETE ---");
    }
}
