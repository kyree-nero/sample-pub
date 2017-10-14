package sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PersistenceConfiguration.class, 
	BatchConfiguration.class})

public class ApplicationConfiguration {

}
