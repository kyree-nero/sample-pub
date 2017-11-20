package sample.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfiguration {

//	@Value("${jndi.jms.connectionfactory}") private String jndiConnectionFactory;
//	
//	@Value("${jndi.jms.batchqueue}") private String jndiBatchQueue;
//	
//	
//	public @Bean JndiObjectFactoryBean connectionFactory() throws Exception {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		bean.setJndiName(jndiConnectionFactory);
//		System.out.println("...initialzing JMS Connectionfactory as " + jndiConnectionFactory);
//		bean.setExpectedType(ConnectionFactory.class);
//		
//		ConnectionFactory c = (ConnectionFactory) bean.getObject();
//		c.createConnection();
//		return bean;
//	}
//	
//	public @Bean JndiObjectFactoryBean batchQueueDestination() {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		bean.setJndiName(jndiBatchQueue);
//		bean.setExpectedType(Destination.class);
//		
//		return bean;
//	}
//	
	String BROKER_URL = "tcp://localhost:61616"; 
	String BROKER_USERNAME = "admin"; 
	String BROKER_PASSWORD = "admin";
	
	@Bean public ActiveMQConnectionFactory connectionFactory() throws JMSException{ 
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(); 
		connectionFactory.setBrokerURL(BROKER_URL); 
		connectionFactory.setPassword(BROKER_USERNAME); 
		connectionFactory.setUserName(BROKER_PASSWORD);
		connectionFactory.setTrustAllPackages(true);
//		ConnectionFactory c = (ConnectionFactory) connectionFactory;
//		c.createConnection();
		return connectionFactory; 
	
	}

	@Bean public Destination batchQueueDestination() {
		ActiveMQQueue queue = new ActiveMQQueue("batchQueue");
		return queue;
	}
}
	