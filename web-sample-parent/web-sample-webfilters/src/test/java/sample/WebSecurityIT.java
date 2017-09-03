package sample;

import org.junit.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class WebSecurityIT extends AbstractIT{
	@Test public void testCSRF()throws Exception {
		mockMvc
	    .perform(
	    		MockMvcRequestBuilders.post("/")
	    			.with(SecurityMockMvcRequestPostProcessors.csrf()));
	}
	
	
}
