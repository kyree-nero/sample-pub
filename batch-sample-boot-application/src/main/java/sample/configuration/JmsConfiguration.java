package sample.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class JmsConfiguration {

	@Value("${jndi.jms.connectionfactory}") private String jndiConnectionFactory;
	
	@Value("${jndi.jms.batchqueue}") private String jndiBatchQueue;
	
	
	public @Bean JndiObjectFactoryBean connectionFactory() throws Exception {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(jndiConnectionFactory);
		bean.setExpectedType(ConnectionFactory.class);
		return bean;
	}
	
	public @Bean JndiObjectFactoryBean batchQueueDestination() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(jndiBatchQueue);
		bean.setExpectedType(Destination.class);
		return bean;
	}
	
}
