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
	
	@WithMockUser(value="user", roles="USERS")
	@Test public void securedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample/1", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("data", Matchers.notNullValue()))
//		.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.nullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	@WithMockUser(value="userNotInRole", roles="NONAUTHROLE")
	@Test public void unAuthorizedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn();
	}
	
	
	@Test public void unAuthenticatedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn();
	}
	
	
}
