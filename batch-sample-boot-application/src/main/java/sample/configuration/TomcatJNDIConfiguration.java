package sample.configuration;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class TomcatJNDIConfiguration {
	@Bean public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				super.postProcessContext(context);
				
				initializeDataSource(context);
				initializeJms(context);
			}
			
			
			private void initializeDataSource(Context context) {
				
				ContextResource dataSourceContextResource = new ContextResource();
				
				dataSourceContextResource.setName("jdbc/dataSource");
				dataSourceContextResource.setType("javax.sql.DataSource");
				dataSourceContextResource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
				dataSourceContextResource.setProperty("username", "root");
				dataSourceContextResource.setProperty("password", "x");
				dataSourceContextResource.setProperty("url", "jdbc:mysql://localhost:3306/sample");
				dataSourceContextResource.setProperty("factory","org.apache.tomcat.jdbc.pool.DataSourceFactory");
				
				context.getNamingResources().addResource(dataSourceContextResource);
			}

			private void initializeJms(Context context) {
				
				ContextResource jmsConnectionFactoryContextResource = new ContextResource();
				/*
				 * <Resource auth="Container"
          name="jms/ConnectionFactory"
          type="org.apache.activemq.ActiveMQConnectionFactory"
          description="JMS Connection Factory"
          factory="org.apache.activemq.jndi.JNDIReferenceFactory"
          brokerURL="vm://localhost?brokerConfig=xbean:activemq.xml"
          brokerName="MyActiveMQBroker"/>
				 */
				jmsConnectionFactoryContextResource.setName("jms/connectionFactory");
				jmsConnectionFactoryContextResource.setType("org.apache.activemq.ActiveMQConnectionFactory");
				jmsConnectionFactoryContextResource.setProperty("brokerURL", "vm://localhost");
				jmsConnectionFactoryContextResource.setProperty("description", "JMS Connection Factory");
				jmsConnectionFactoryContextResource.setProperty("brokerName", "activeMQBroker");
				jmsConnectionFactoryContextResource.setProperty("factory","org.apache.activemq.jndi.JNDIReferenceFactory");
				
				context.getNamingResources().addResource(jmsConnectionFactoryContextResource);
			
				ContextResource jmsBatchQueueContextResource = new ContextResource();
				/*
				 * name="jms/FooQueue"
          type="org.apache.activemq.command.ActiveMQQueue"
          description="JMS queue"
          factory="org.apache.activemq.jndi.JNDIReferenceFactory"
          physicalName="FOO.QUEUE"/>
				 */
				jmsBatchQueueContextResource.setName("jms/queue/batchQueue");
				jmsBatchQueueContextResource.setType("org.apache.activemq.command.ActiveMQQueue");
				jmsBatchQueueContextResource.setProperty("description", "JMS Batch Queue");
				jmsBatchQueueContextResource.setProperty("factory","org.apache.activemq.jndi.JNDIReferenceFactory");
				jmsBatchQueueContextResource.setProperty("description", "JMS Batch Queue");
				context.getNamingResources().addResource(jmsBatchQueueContextResource);
			}


			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}
			
		};
		
		
	}
}
