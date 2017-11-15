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
public class JNDIConfiguration {
	@Bean public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				super.postProcessContext(context);
				
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

			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}
			
		};
		
		
	}
}
