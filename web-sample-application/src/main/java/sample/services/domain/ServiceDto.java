package sample.services.domain;

import javax.validation.Valid;

import org.springframework.validation.Errors;

public class ServiceDto<T> {
	@Valid private T t;
	private Errors errors;
	
	
}
