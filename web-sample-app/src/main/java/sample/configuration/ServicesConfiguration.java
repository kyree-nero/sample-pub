package sample.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import sample.services.SampleServiceImpl;

@Configuration
@ComponentScan(basePackageClasses= {SampleServiceImpl.class})

public class ServicesConfiguration {
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}
