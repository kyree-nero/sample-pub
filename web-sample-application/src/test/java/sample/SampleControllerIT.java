package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class SampleControllerIT extends AbstractWebMvcIT{
	@Test public void test() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.param("in", "a")
		
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name(Matchers.equalToIgnoringCase("show")))
		.andExpect(MockMvcResultMatchers.model().hasNoErrors())
		.andExpect(MockMvcResultMatchers.model().attribute("out", Matchers.anything()))
		.andReturn();
	}
	
	
	
}
