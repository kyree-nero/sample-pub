package sample.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sample.web.domain.SampleForm;

@Controller
public class SampleController {

	@RequestMapping("/read")
	public String read(@ModelAttribute("sampleform") SampleForm sampleForm) {
		if(sampleForm.getIn() != null) {
			sampleForm.setOut("y");
		}
		
		return "show";
	}
}
