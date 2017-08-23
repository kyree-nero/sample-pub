package sample.configuration.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import sample.configuration.application.ApplicationConfiguration;

public class SpringLoadingWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ApplicationConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		WebApplicationContext context = super.createRootApplicationContext();
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
				((AnnotationConfigWebApplicationContext)context).getEnvironment().setActiveProfiles(springProfiles);
			}
			
		}
		return context;
		
	}

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
	
}
