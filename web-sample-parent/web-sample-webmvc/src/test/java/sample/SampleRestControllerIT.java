package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
  

public class SampleRestControllerIT extends WebMvcAbstractIT{
	
	
	@Test public void test() throws Exception {
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
}
