package sample;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import sample.persistence.repositories.SampleEntryRepository;
import sample.services.domain.Sample;
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SampleRestControllerIT extends AbstractWebMvcIT{
	@Autowired SampleEntryRepository sampleEntryRepository;
//	@Test public void test() {}
	@Test public void testFindAll() throws Exception {
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
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
		
		long count = sampleEntryRepository.count();
		
		
		Sample requestSample = new Sample();
		requestSample.setContent("some text");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put("/sample", new Object[] {})
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertTrue(posttCount != count);
	}
	

	
	@Test public void testUpdate() throws Exception {
		
		long count = sampleEntryRepository.count();
		
		Sample requestSample = new Sample();
		requestSample.setId(0L);
		requestSample.setContent("update text");
		requestSample.setVersion(0L);
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
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertTrue(posttCount == count);
	}
	
	
	@Test public void testRemove() throws Exception {
		long count = sampleEntryRepository.count();
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.delete("/sample/5000", new Object[] {})
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertFalse(posttCount == count);
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
	
	
}
