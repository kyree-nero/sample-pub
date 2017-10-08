package sample.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import sample.persistence.dao.SampleDao;


@Configuration
@Import({
	PersistenceBaseConfiguration.class, 
	TransactionManagementConfiguration.class, 
	PersistenceJPAConfiguration.class
})
@ComponentScan(basePackageClasses=SampleDao.class)

public class PersistenceConfiguration {
	
}
