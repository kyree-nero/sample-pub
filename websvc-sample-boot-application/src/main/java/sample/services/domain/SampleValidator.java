package sample.services.domain;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component	
public class SampleValidator implements Validator{

	@Autowired MessageSource messageSource;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Sample.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sample sample = (Sample)target;
		
		ValidationUtils.rejectIfEmpty(errors, "content", "empty", messageSource.getMessage("empty", null, Locale.US));
	}

}
