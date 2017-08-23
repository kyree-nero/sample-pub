package sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import sample.configuration.services.ServicesConfiguration;
import sample.configuration.web.WebConfiguration;

@Configuration
@Import({ServicesConfiguration.class, WebConfiguration.class})
public class TestConfiguration {

}
