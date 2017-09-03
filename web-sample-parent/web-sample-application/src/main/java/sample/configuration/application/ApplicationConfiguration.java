package sample.configuration.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import sample.configuration.persistence.PersistenceConfiguration;
import sample.configuration.services.ServicesConfiguration;
import sample.configuration.web.WebConfiguration;
import sample.configuration.web.security.WebSecurityConfiguration;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	ServicesConfiguration.class, 
	WebConfiguration.class, 
	WebSecurityConfiguration.class})

public class ApplicationConfiguration {

}
