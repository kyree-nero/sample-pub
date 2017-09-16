package sample.services.domain;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ValidationDto{
	
	private List<ObjectError> errors;
	
		
	public List<ObjectError> getErrors() {
		return errors;
	}


	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}
	
	
}
