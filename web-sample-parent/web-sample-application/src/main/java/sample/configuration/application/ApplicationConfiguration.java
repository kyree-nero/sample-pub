package sample.configuration.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import sample.configuration.persistence.PersistenceConfiguration;
import sample.configuration.services.ServicesConfiguration;
import sample.configuration.web.WebConfiguration;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	ServicesConfiguration.class, 
	WebConfiguration.class})

public class ApplicationConfiguration {

}
