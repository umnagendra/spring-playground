package xyz.nagendra.quartzdemo.component;

import org.quartz.SchedulerException;
import org.quartz.spi.InstanceIdGenerator;

import java.util.UUID;

public class CustomQuartzInstanceIdGenerator implements InstanceIdGenerator {
    @Override
    public String generateInstanceId() throws SchedulerException {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            throw new SchedulerException("Failed to generate instance ID", e);
        }
    }
}
