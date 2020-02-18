package xyz.nagendra.quartzdemo.service.impl;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.nagendra.quartzdemo.entity.JobInfo;
import xyz.nagendra.quartzdemo.repository.JobInfoRepository;
import xyz.nagendra.quartzdemo.service.ConfigService;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final JobInfoRepository jobInfoRepository;

    @Autowired
    public ConfigServiceImpl(JobInfoRepository jobInfoRepository) {
        this.jobInfoRepository = jobInfoRepository;
    }

    @Override
    public Try<JobInfo> save(JobInfo jobInfo) {
        return Try.of(() -> jobInfoRepository.saveAndFlush(jobInfo));
    }

    @Override
    public Try<List<JobInfo>> list() {
        return Try.of(jobInfoRepository::findAll);
    }

    @Override
    public Try<JobInfo> get(String id) {
        return Try.of(() -> jobInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND")));
    }

    @Override
    public Try<Void> delete(String id) {
        return Try.run(() -> jobInfoRepository.deleteById(id));
    }
}
