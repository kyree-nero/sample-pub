package sample.configuration.persistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import sample.persistence.dao.SampleDao;

@Configuration
@Import({
	BasePersistenceConfiguration.class, 
	TransactionManagementConfiguration.class, 
	JPAConfiguration.class
})
@ComponentScan(basePackageClasses=SampleDao.class)

public class PersistenceConfiguration {
	
}
