package sample;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract  class AbstractSecurityWebMvcIT extends AbstractWebMvcIT{
	@Autowired MockMvc mockMvc;
	@Autowired WebApplicationContext webApplicationContext;
	
	
	@Before public void before() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(SecurityMockMvcConfigurers.springSecurity()) 
			.build();
	}
	
}
