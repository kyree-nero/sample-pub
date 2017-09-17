package sample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import sample.services.domain.Sample;
  

public class SampleRestControllerIT extends AbstractWebMvcIT{
	
	
	@Test public void testFindAll() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	@Test public void testFindById() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample/0", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	
	@Test public void testSave() throws Exception {
		Sample requestSample = new Sample();
		requestSample.setContent("some text");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/sample", new Object[] {})
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	

	
	@Test public void testUpdate() throws Exception {
		Sample requestSample = new Sample();
		requestSample.setId(1L);
		requestSample.setContent("update text");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/sample", new Object[] {})
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	@Test public void testSaveWithValidationException() throws Exception {
		Sample requestSample = new Sample();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/sample", new Object[] {})
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.not(Matchers.empty())))
		.andReturn();
	}
	
	
	@Test public void testApplicationException() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/generateException", new Object[] {}))
						
		
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		
		.andReturn();
	}
}
