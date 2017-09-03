package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class SampleControllerIT extends WebMvcAbstractIT{
	@Test public void test() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/read", new Object[] {})
				.param("in", "a")
//				.accept(MediaType.APPLICATION_JSON)
//				.contentType(MediaType.APPLICATION_JSON)
		
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name(Matchers.equalToIgnoringCase("show")))
		.andExpect(MockMvcResultMatchers.model().hasNoErrors())
		.andExpect(MockMvcResultMatchers.model().attribute("out", Matchers.anything()))
		.andReturn();
	}
}
