package sample;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public class PersistenceAbstractIT extends BaseIT{
protected static SimpleNamingContextBuilder context = null;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		context = JdbcJndiTestSupport.initializeJNDI();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		SimpleNamingContextBuilder.emptyActivatedContextBuilder();
	}
}
