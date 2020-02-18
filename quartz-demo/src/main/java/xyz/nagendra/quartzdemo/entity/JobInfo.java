package xyz.nagendra.quartzdemo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(catalog = "quartz_demo_db", name = "config_job_info")
public class JobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "job_name", unique = true)
    private String name;

    @Column(name = "job_group")
    private String group;

    @Column(name = "job_class")
    private String jobClass;

    @Column(name = "repeat_time")
    private Long repeatTimes;

    @Column(name = "is_cron")
    private Boolean isCron;

    @Column(name = "cron_expression")
    private String cronExpression;

    public JobInfo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public Long getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Long repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public Boolean getCron() {
        return isCron;
    }

    public void setCron(Boolean cron) {
        isCron = cron;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
