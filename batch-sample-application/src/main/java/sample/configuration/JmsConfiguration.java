package sample.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class JmsConfiguration {

	public @Bean JndiObjectFactoryBean connectionFactory() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("jboss/DefaultJMSConnectionFactory");
		bean.setExpectedType(ConnectionFactory.class);
		return bean;
	}
	
	public @Bean JndiObjectFactoryBean batchQueueDestination() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("jms/queue/batchQueue");
		bean.setExpectedType(Destination.class);
		return bean;
	}
	
}
