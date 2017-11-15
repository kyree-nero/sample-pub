package sample;


import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
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
	public final static String BROKER_URL ="vm://transport";
	public final static String JNDI_DS = "java:jdbc/dataSource";
	public final static String JNDI_JMS_CONNFACTORY = "java:jms/connectionFactory";
	public final static String JNDI_JMS_QUEUE = "java:jms/batchQueue";
			
	@BeforeClass public static void beforeClass() throws Exception{
		System.out.println("..initialize JNDI");
		context = SimpleNamingContextBuilder.getCurrentContextBuilder();
		
		if(context == null) {
			context = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
			
		}
		
		initializeDataSource();
		
	}
	
	public static void initializeDataSource() throws Exception {
		System.out.println("..initialize JNDI DataSource");
		

		boolean foundObject  = false;
		try {
			new InitialContext().lookup(JNDI_DS);
			foundObject  = true;
		}catch(NamingException namingException) {
			foundObject  = false;
		}
		
		if(foundObject) {
			return;
		}
		
		
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:sample;"
				+ "DB_CLOSE_DELAY=-1;"
				+ "DB_CLOSE_ON_EXIT=FALSE;"
				+ "AUTOCOMMIT=FALSE;"
				+ "LOCK_MODE=0;"
				+ "REFERENTIAL_INTEGRITY=TRUE");
		context.bind(JNDI_DS, dataSource);
		
	}
	
	public static void initializeJms() throws Exception {
		System.out.println("..initialize JMS DataSource");
		
		boolean foundObject  = false;
		try {
			new InitialContext().lookup(JNDI_JMS_CONNFACTORY);
			foundObject  = true;
		}catch(NamingException namingException) {
			foundObject  = false;
		}
		
		if(foundObject) {
			return;
		}
		
	
		BrokerService brokerService = new BrokerService();
		brokerService.setBrokerName("localhost");
		brokerService.setUseJmx(false);
		brokerService.setUseShutdownHook(false);
		brokerService.setPersistent(false);
		brokerService.setTransportConnectorURIs(new String[] {BROKER_URL});
		brokerService.start();
		
		ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory();
		jmsConnectionFactory.setBrokerURL(BROKER_URL);
		jmsConnectionFactory.afterPropertiesSet();
		
		context.bind(JNDI_JMS_CONNFACTORY, jmsConnectionFactory);
		
		ActiveMQQueue batchQueue = new ActiveMQQueue("batchQueue");
		context.bind(JNDI_JMS_QUEUE, batchQueue);
	}
	
	
}
