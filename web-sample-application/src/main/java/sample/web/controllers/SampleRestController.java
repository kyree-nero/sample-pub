package sample.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import sample.services.SampleService;
import sample.services.domain.Sample;
import sample.services.domain.SampleValidator;

@RestController
public class SampleRestController extends WebMvcConfigurerAdapter {

	@Autowired SampleService sampleService;
	@Autowired SampleValidator sampleValidator;
	
	@GetMapping(path = "/sample/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Sample getSample(@PathVariable Long id, Model model) {
		return sampleService.findSample(id);
	}
	
	
	@PostMapping(path = "/sample", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Sample add(@Valid @RequestBody Sample sample, Model model) {
		return sampleService.save(sample);
	 }


	@Override
	public Validator getValidator() {
		return sampleValidator;
	}
	 
	
}
