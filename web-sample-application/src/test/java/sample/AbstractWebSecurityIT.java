package sample;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sample.configuration.PersistenceConfiguration;
import sample.configuration.ServicesConfiguration;
import sample.configuration.WebConfiguration;
import sample.configuration.WebSecurityConfiguration;

@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, 
		classes= {
				PersistenceConfiguration.class, 
				ServicesConfiguration.class, 
				WebConfiguration.class, 
				WebSecurityConfiguration.class
		}
)
@WebAppConfiguration 
public abstract class AbstractWebSecurityIT extends AbstractPersistenceIT{
	protected MockMvc mockMvc;
	@Autowired WebApplicationContext webApplicationContext;
	
	
	@Before public void before() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(SecurityMockMvcConfigurers.springSecurity()) 
			.build();
	}
	
	
}
