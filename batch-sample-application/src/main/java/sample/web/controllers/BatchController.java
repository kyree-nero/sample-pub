package sample.web.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Spring batch admin is deprecated.  Instead here is a small harness start jobs.
 */
@RestController
public class BatchController {
	@Autowired @Qualifier("simpleBatchJob") Job simpleBatchJob;
	@Autowired  @Qualifier("simplePartitionBatchJob") Job simplePartitionBatchJob;
	@Autowired  @Qualifier("simpleRemotePartitionBatchJob") Job simpleRemotePartitionBatchJob;
	@Autowired JobLauncher jobLauncher;
	@Autowired JobExplorer jobExplorer;
	
	
	@GetMapping(path = "batch/simplebatchjob/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public  Long startSimpleBatchJob() throws JobExecutionException {
		JobExecution jobExecution = jobLauncher.run(simpleBatchJob, new JobParameters());
		return jobExecution.getId();
	}
	
	@GetMapping(path = "batch/simplepartitioningbatchjob/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public  Long startSimplePartitionBatchJob() throws JobExecutionException {
		JobExecution jobExecution = jobLauncher.run(simplePartitionBatchJob, new JobParameters());
		return jobExecution.getId();
	}
	
	@GetMapping(path = "batch/simpleremotepartitioningbatchjob/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public  Long startSimpleRemotePartitionBatchJob() throws JobExecutionException {
		JobExecution jobExecution = jobLauncher.run(simpleRemotePartitionBatchJob, new JobParameters());
		return jobExecution.getId();
	}
	
	@GetMapping(path = "batch/status/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public  String findJobExecution(@PathVariable Long id) throws JobExecutionException {
		JobExecution jobExecution = jobExplorer.getJobExecution(id);
		return "{\"status\":\""+jobExecution.getStatus().name()+"\"}";
	
	}
}
