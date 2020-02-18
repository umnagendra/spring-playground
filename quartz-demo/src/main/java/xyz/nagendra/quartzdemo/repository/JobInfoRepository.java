package xyz.nagendra.quartzdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.nagendra.quartzdemo.entity.JobInfo;

@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo, String> {

}
