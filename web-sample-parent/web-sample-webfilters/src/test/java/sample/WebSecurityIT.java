package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestExecutionListeners(
		{
			DependencyInjectionTestExecutionListener.class, 
			WithSecurityContextTestExecutionListener.class, 
			ServletTestExecutionListener.class
		}
)
public class WebSecurityIT extends WebSecAbstractIT{
	@Test public void testCSRF()throws Exception {
		mockMvc
	    .perform(
	    		MockMvcRequestBuilders.post("/")
	    			.with(SecurityMockMvcRequestPostProcessors.csrf()));
	}
	@Test public void testClickJacking() throws Exception {
		mockMvc
	    .perform(
	    		MockMvcRequestBuilders.post("/")
	    			.with(SecurityMockMvcRequestPostProcessors.csrf())
	    			
	    			
	    )
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-Content-Type-Options", "nosniff"))
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-Frame-Options", "DENY"))
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-XSS-Protection", "1; mode=block"));
	}
	
	@WithMockUser("user")
	@Test public void securedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/restget", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("data", Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.nullValue()))
		.andReturn();
	}
	
	
	@Test public void unsecuredRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/restget", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn();
	}
	
	@WithMockUser("user")
	@Test public void testApplicationException() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/generateException", new Object[] {}))
						
		
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		
		.andReturn();
	}
}
