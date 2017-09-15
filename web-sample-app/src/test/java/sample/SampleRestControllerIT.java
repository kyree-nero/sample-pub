package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
  

public class SampleRestControllerIT extends AbstractWebMvcIT{
	
	
	@Test public void test() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample/1", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		//.andExpect(MockMvcResultMatchers.jsonPath("data", Matchers.notNullValue()))
		//.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.nullValue()))
		
		.andReturn();
	}
	
	@Test public void testApplicationException() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/generateException", new Object[] {}))
						
		
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		
		.andReturn();
	}
}
