package sample.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sample.services.SampleService;
import sample.services.domain.Sample;

@RestController
public class SampleRestController {

	@Autowired SampleService sampleService;
	
	
	@GetMapping(path = "/sample/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Sample getSample(@PathVariable Long id, Model model) {
		return sampleService.findSample(id);
	}
	
//	@RequestMapping(value="/sample/{id}", method = RequestMethod.GET) @ResponseBody
//	public SampleJsonResponse<Sample> sampleGet( @PathVariable("id") long id) {
//		 SampleJsonResponse<Sample> response = new SampleJsonResponse<Sample>();
//		 Sample sample = sampleService.findSample(id);
//		 response.setData(sample);
//		 return response;
//	 }
//	 
	
	@PostMapping(path = "/sample", consumes = "application/json")
	public void add(@RequestBody Sample sample, Model model) {
		 sampleService.save(sample);
	 }
	 
}
