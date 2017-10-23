package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WithMockUser(value="user", roles="USERS")
public class SampleControllerIT extends AbstractSecurityWebMvcIT{
	@Autowired MockMvc mockMvc;
	
	@Test public void test() throws Exception {
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				
				.param("in", "a")
				
		
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name(Matchers.equalToIgnoringCase("show")))
		.andExpect(MockMvcResultMatchers.model().hasNoErrors())
		.andExpect(MockMvcResultMatchers.model().attribute("out", Matchers.anything()))
		.andReturn();
	}
	
	
	
}
