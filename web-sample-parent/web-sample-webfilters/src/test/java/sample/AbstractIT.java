package sample;

import javax.servlet.Filter;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sample.configuration.persistence.PersistenceConfiguration;
import sample.configuration.services.ServicesConfiguration;
import sample.configuration.web.WebConfiguration;
import sample.configuration.web.security.WebSecurityConfiguration;

@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, 
		classes= {
				PersistenceConfiguration.class, 
				ServicesConfiguration.class, 
				WebConfiguration.class, 
				WebSecurityConfiguration.class
		}
)
@WebAppConfiguration 
public abstract class AbstractIT extends PersistenceAbstractIT{
	protected MockMvc mockMvc;
	@Autowired WebApplicationContext webApplicationContext;
	@Autowired private Filter springSecurityFilterChain;
	
	
	@Before public void before() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.addFilters(springSecurityFilterChain)
			.build();
	}
	
	
}
