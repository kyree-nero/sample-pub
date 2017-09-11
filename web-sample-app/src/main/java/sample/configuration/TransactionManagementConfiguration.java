package sample.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@Import(TransactionManagementConfigurationExtender.class)
public class TransactionManagementConfiguration {
	
	@Bean @Profile("default") public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager bean = new DataSourceTransactionManager();
		bean.setDataSource(dataSource);
		return bean;
	}
	
	
	
	
}
