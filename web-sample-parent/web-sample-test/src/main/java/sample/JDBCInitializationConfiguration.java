package sample;

import java.io.InputStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class JDBCInitializationConfiguration {
	
	@Autowired JdbcOperations jdbcOperations;
	
	@Bean public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer bean = new DataSourceInitializer();
		bean.setDataSource(dataSource);
		bean.setDatabasePopulator(databasePopulator());
		return bean;
	}
	
	@Bean 
	public ResourceDatabasePopulator databasePopulator() {
		ResourceDatabasePopulator bean = new ResourceDatabasePopulator();
		String[] locations = {
				"/META-INF/data/h2/drops.sql",
				"/META-INF/data/h2/tables.sql",
				"/META-INF/data/h2/inserts.sql"
				
		};
		for(String location : locations) {
			System.out.println("loading JDBC ddl " + location);
			InputStream inputStream = getClass().getResourceAsStream(location);
			bean.addScript(new InputStreamResource(inputStream));
		}
		return bean;
	}
}
