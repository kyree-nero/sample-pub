package sample.services.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SampleValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Sample.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sample sample = (Sample)target;
		
		ValidationUtils.rejectIfEmpty(errors, "content", "empty");
	}

}
