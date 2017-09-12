package sample.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sample.services.SampleService;
import sample.web.domain.Sample;
import sample.web.domain.SampleJsonResponse;

@RestController
public class SampleRestController {

	@Autowired SampleService sampleService;
	
	 @RequestMapping(value="/sample", method = RequestMethod.GET) @ResponseBody
	public SampleJsonResponse<Sample> sampleGet() {
		 System.out.println("in service");
		 SampleJsonResponse<Sample> response = new SampleJsonResponse<Sample>();
		 Sample sample = new Sample();
		 sample.setContent("hi from sample.");
		 response.setData(sample);
		 
		 sampleService.doStuff();
		 sampleService.findCountInDb();
		 return response;
	 }
	 
	 
}
