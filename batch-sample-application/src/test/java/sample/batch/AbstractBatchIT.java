package sample.batch;

import org.junit.Test;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBatchIT extends AbstractJNDIResourceIT {
	@Autowired JobLauncher jobLauncher;
	
	
}
