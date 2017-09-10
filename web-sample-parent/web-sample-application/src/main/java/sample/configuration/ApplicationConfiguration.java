package sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import sample.configuration.persistence.PersistenceConfiguration;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	ServicesConfiguration.class, 
	WebConfiguration.class, 
	WebSecurityConfiguration.class})

public class ApplicationConfiguration {

}
