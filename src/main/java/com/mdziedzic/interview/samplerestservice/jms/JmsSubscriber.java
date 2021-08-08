package com.mdziedzic.interview.samplerestservice.jms;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class JmsSubscriber {

    private final Job job;
    private final JobLauncher jobLauncher;

    @JmsListener(destination = "${activemq.destination}")
    public void triggerListener()
        throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.debug("Received message from JMS");
        jobLauncher.run(job, new JobParameters());
    }
}
