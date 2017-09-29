package sample.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sample.web.domain.SampleForm;

@Controller
public class SampleController {

	@RequestMapping("/sample")
	public String read(@ModelAttribute("sampleform") SampleForm sampleForm) {
		if(sampleForm.getIn() != null) {
			sampleForm.setOut("y");
		}
		
		return "show";
	}
	
	@RequestMapping("/generateException")
	public String generateException(@ModelAttribute("sampleform") SampleForm sampleForm) {
		if(1==1) {
			throw new IllegalArgumentException("Sample exception");
		}
		return "show";
	}
}
