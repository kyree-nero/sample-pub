package sample;

import java.util.Properties;
import java.util.UUID;

import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class JdbcJndiTestSupport {
	public static SimpleNamingContextBuilder initializeJNDI() throws Exception{
		SimpleNamingContextBuilder context = SimpleNamingContextBuilder.getCurrentContextBuilder();
		
		if(context == null) {
			context = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		}
		
//		JdbcDataSource dataSource = new JdbcDataSource();
//		dataSource.setURL("jdbc:h2:mem:example;"
//				+ "DB_CLOSE_ON_EXIT=FALSE;"
//				+ "DB_CLOSE_DELAY=-1;"
//				+ "AUTOCOMMIT=FALSE;"
//				+ "LOCK_MODE=0;"
//				+ "REFERENTIAL_INTEGRITY=TRUE");
		PoolingDataSource dataSource = new PoolingDataSource();
		dataSource.setClassName("org.h2.jdbcx.JdbcDataSource");
		dataSource.setUniqueName(UUID.randomUUID().toString());
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(15);
		dataSource.setAllowLocalTransactions(true);
		Properties properties = new Properties();
		properties.put("URL", "jdbc:h2:mem:example;"
				+ "DB_CLOSE_ON_EXIT=FALSE;"
				+ "DB_CLOSE_DELAY=-1;"
				+ "AUTOCOMMIT=FALSE;"
				+ "LOCK_MODE=0;"
				+ "REFERENTIAL_INTEGRITY=TRUE");
		dataSource.setDriverProperties(properties);
		
		context.bind("sampleDS", dataSource);
		
		AnnotationConfigContextLoader preLoadContextLoader = new AnnotationConfigContextLoader();
		MergedContextConfiguration preloadContext = new MergedContextConfiguration(
				JdbcJndiTestSupport.class, 
				new String[] {}, 
				new Class<?>[] {
					BasePersistenceConfiguration.class, 
					JDBCInitializationConfiguration.class
				},
				new String[] {}, preLoadContextLoader); 
		preLoadContextLoader.loadContext(preloadContext);
		return context;
	}
}
