package xyz.nagendra.quartzdemo.service;

import io.vavr.control.Try;
import xyz.nagendra.quartzdemo.entity.JobInfo;

import java.util.List;

public interface ConfigService {
    Try<JobInfo> save(JobInfo jobInfo);
    Try<List<JobInfo>> list();
    Try<JobInfo> get(String name);
    Try<Void> delete(String name);
}
