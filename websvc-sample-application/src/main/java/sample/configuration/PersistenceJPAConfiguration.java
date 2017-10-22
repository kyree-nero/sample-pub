package sample.configuration;

import java.util.Properties;

import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.engine.transaction.jta.platform.internal.JBossAppServerJtaPlatform;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

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
	 	       
	 	        entityManagerFactoryBean.setPersistenceUnitPostProcessors(
	 	        		new PersistenceUnitPostProcessor() {
				 
			 	        	
							@Override
							public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo persistenceUnitInfo) {
								persistenceUnitInfo.setTransactionType(PersistenceUnitTransactionType.JTA);
									persistenceUnitInfo.setJtaDataSource(persistenceUnitInfo.getNonJtaDataSource());
									persistenceUnitInfo.setNonJtaDataSource(null);
									
								
							}
							
							
						}
	 	        );
	 	        return entityManagerFactoryBean;
	 
	 }
	 
	 
	 @Bean @Profile({"!test"})  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	        entityManagerFactoryBean.setPackagesToScan("sample.persistence.entities");
	        entityManagerFactoryBean.setJpaProperties(hibProperties());
	        entityManagerFactoryBean.setPersistenceUnitName("samplePersistenceUnit");
	        entityManagerFactoryBean.setJtaDataSource(dataSource);
	      
	       return entityManagerFactoryBean;

	 }
	 
	
//	 @Bean @Profile("default")  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//	        entityManagerFactoryBean.setDataSource(dataSource);
//	        entityManagerFactoryBean.setPackagesToScan("sample.persistence.entities");
//	        entityManagerFactoryBean.setJpaVendorAdapter(defaultJpaVendorAdapter());
//	        return entityManagerFactoryBean;
//
//	 }
	 
	 
//	@Bean
//	public JpaVendorAdapter defaultJpaVendorAdapter()
//	{
//			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//			adapter.setShowSql(true);
//			adapter.setGenerateDdl(false);
//			adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");		
//			
//			return adapter;
//	}
	
	 @Bean @Profile("test") public Properties testHibProperties() {
	 
	 	        Properties properties = new Properties();
	 
	 	        properties.put("hibernate.dialect", H2Dialect.class.getName());
	 
	 	        properties.put("hibernate.show_sql", true);
	 
	 	        return properties;
	 
	 }
	 
	 @Bean @Profile({"!test"}) public Properties hibProperties() {
		 
	        Properties properties = new Properties();

	        properties.put("hibernate.dialect", MySQLDialect.class.getName());

	        properties.put("hibernate.show_sql", true);
	        properties.put("hibernate.transaction.jta.platform", JBossAppServerJtaPlatform.class.getName());
	        properties.put("hibernate.id.new_generator_mappings", false);
	        return properties;

}
}
