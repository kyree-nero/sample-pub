package sample.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

public class SpringLoadingWebAppInitializer implements WebApplicationInitializer{

	

	public Properties readProperties (String propertiesFileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();;
		InputStream inputStream = classLoader.getResourceAsStream(propertiesFileName);
		System.out.println("Reading properties  ("+propertiesFileName+")");
		
		try {
			if(inputStream == null) {
				System.out.println("Could not find properties ("+propertiesFileName+")");
				return null;
			}else {
				Properties properties = new Properties();
				properties.load(inputStream);
				return properties;
			}
		}catch(IOException ioException) {
			throw new RuntimeException("Could not load properties ("+propertiesFileName+") ");
		}
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.setConfigLocation(ApplicationConfiguration.class.getName());

        
        Properties properties = readProperties("servlet.properties");
		
		if(properties != null) {
			String springProfilesPropertyValue = properties.getProperty("spring.profiles.active");
			if(springProfilesPropertyValue == null) {
				System.out.println("No value found for spring profiles property");
			}else {
				String[] springProfiles = springProfilesPropertyValue.split(",");
				for(String springProfile:springProfiles) {
					System.out.println("setting spring profile " + springProfile);
					
				}
				context.getEnvironment().setActiveProfiles(springProfiles);
			}
			
		}
		
        // use MessageDispatcherServlet instead of standard DispatcherServlet for SOAP messages
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setContextClass(AnnotationConfigWebApplicationContext.class);
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        // register MessageDispatcherServlet as Web Service entry point
        final ServletRegistration.Dynamic dispatcher = servletContext.addServlet("MessageDispatcherServlet",
                servlet);

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
		
	}
	
}
