package sample.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sample.services.SampleService;
import sample.services.domain.Sample;
import sample.web.domain.SampleJsonResponse;

@RestController
public class SampleRestController {

	@Autowired SampleService sampleService;
	
	@RequestMapping(value="/sample/{id}", method = RequestMethod.GET) @ResponseBody
	public SampleJsonResponse<Sample> sampleGet( @PathVariable("id") long id) {
		 System.out.println("in service");
		 SampleJsonResponse<Sample> response = new SampleJsonResponse<Sample>();
		 Sample sample = sampleService.findSample(id);
		 response.setData(sample);
		 return response;
	 }
	 
	 
	 
}
