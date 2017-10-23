package sample;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sample.persistence.dao.SampleDao;


public class SampleJdbcIT extends AbstractWebMvcIT {
	
	@Autowired SampleDao sampleDao;
	
	
	
	
	
	@Test public void test() throws Exception {
		
		Assert.assertNotNull(sampleDao.findSampleCount());
		
	}
	
}
