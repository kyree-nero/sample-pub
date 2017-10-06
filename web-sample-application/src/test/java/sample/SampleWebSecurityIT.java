package sample;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class SampleWebSecurityIT extends AbstractWebSecurityIT{
	
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
				MockMvcRequestBuilders.get("/sample/0", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	

	@WithMockUser(value="user", roles="USERS")
	@Test public void securedRequestLogout() throws Exception{
		MockHttpSession session = new MockHttpSession();
		Assert.assertFalse(session.isInvalid());
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/logout", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.session(session)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/loggedOut.html"))
		.andReturn();
		Assert.assertTrue(session.isInvalid());
		
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
