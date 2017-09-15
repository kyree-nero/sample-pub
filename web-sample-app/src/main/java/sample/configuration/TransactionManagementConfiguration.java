package sample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@Import(TransactionManagementConfigurationExtender.class)
public class TransactionManagementConfiguration {
	

	@Bean @Profile("default") public JtaTransactionManager transactionManager() {
		return new JtaTransactionManager();
	}
	
	
	
	
}
