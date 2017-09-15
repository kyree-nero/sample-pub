package sample;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import sample.configuration.PersistenceConfiguration;
import sample.persistence.dao.SampleDao;

@ContextConfiguration(loader=AnnotationConfigContextLoader.class, 
classes= {PersistenceConfiguration.class})
public class SampleJdbcIT extends AbstractPersistenceIT {
	
	@Autowired SampleDao sampleDao;
	
	
	
	
	
	@Test public void test() throws Exception {
		
		Assert.assertNotNull(sampleDao.findSampleCount());
		
	}
	
}
