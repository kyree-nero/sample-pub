package sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	BatchConfiguration.class, 
	WebConfiguration.class})

public class ApplicationConfiguration {

}
