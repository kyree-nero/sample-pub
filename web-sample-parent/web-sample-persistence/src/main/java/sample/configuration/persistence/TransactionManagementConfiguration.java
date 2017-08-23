package sample.configuration.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionManagementConfiguration {
//	@Bean public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//	DataSourceTransactionManager bean = new DataSourceTransactionManager();
//	bean.setDataSource(dataSource);
//	return bean;
//}
}
