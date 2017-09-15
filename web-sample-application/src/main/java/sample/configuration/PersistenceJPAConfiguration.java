package sample.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories("sample.persistence.repositories")
public class PersistenceJPAConfiguration {
	 @Bean(name="entityManagerFactory") @Profile("test") @DependsOn("transactionManager") public LocalContainerEntityManagerFactoryBean testEntityManagerFactory(DataSource dataSource) {
	 	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	 	        entityManagerFactoryBean.setDataSource(dataSource);
	 	        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	 	        entityManagerFactoryBean.setPackagesToScan("sample.persistence.entities");
	 	        entityManagerFactoryBean.setJpaProperties(testHibProperties());
	 	       entityManagerFactoryBean.setPersistenceUnitName("samplePersistenceUnit");
	 	        return entityManagerFactoryBean;
	 
	 }
	 
	 
	 @Bean @Profile("default")  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setDataSource(dataSource);
	        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	        entityManagerFactoryBean.setPackagesToScan("sample.persistence.entities");
	        entityManagerFactoryBean.setJpaProperties(hibProperties());
	        entityManagerFactoryBean.setPersistenceUnitName("samplePersistenceUnit");
	        return entityManagerFactoryBean;

}
	 
	 	 
	 
	 @Bean @Profile("test") public Properties testHibProperties() {
	 
	 	        Properties properties = new Properties();
	 
	 	        properties.put("hibernate.dialect", H2Dialect.class.getName());
	 
	 	        properties.put("hibernate.show_sql", true);
	 
	 	        return properties;
	 
	 }
	 
	 @Bean @Profile("default") public Properties hibProperties() {
		 
	        Properties properties = new Properties();

	        properties.put("hibernate.dialect", MySQLDialect.class.getName());

	        properties.put("hibernate.show_sql", true);

	        return properties;

}
}
