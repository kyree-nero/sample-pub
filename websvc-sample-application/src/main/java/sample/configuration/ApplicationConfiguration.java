package sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	ServicesConfiguration.class, 
	WebServiceConfiguration.class, 
	WebServiceSecurityConfiguration.class})

public class ApplicationConfiguration {

}


