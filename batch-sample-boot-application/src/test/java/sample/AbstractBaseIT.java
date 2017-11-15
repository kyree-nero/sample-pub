package sample;


import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@Sql({
	"/META-INF/data/h2/drops.sql", 
	"/META-INF/data/h2/tables.sql", 
	"/META-INF/data/h2/inserts.sql"
})
public abstract class AbstractBaseIT {
	protected static SimpleNamingContextBuilder context = null;
	
	@BeforeClass public static void beforeClass() throws Exception{
		System.out.println("..initialize JNDI");
		context = SimpleNamingContextBuilder.getCurrentContextBuilder();
		
		if(context == null) {
			context = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
			
		}
		
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:sample;"
				+ "DB_CLOSE_DELAY=-1;"
				+ "DB_CLOSE_ON_EXIT=FALSE;"
				+ "AUTOCOMMIT=FALSE;"
				+ "LOCK_MODE=0;"
				+ "REFERENTIAL_INTEGRITY=TRUE");
		context.bind("java:jdbc/dataSource", dataSource);
		
	}
	
	
}
