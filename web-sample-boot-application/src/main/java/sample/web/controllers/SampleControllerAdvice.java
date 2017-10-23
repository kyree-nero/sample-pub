package sample.web.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sample.services.domain.ValidationDto;

@ControllerAdvice
public class SampleControllerAdvice {
	 	
		
		
		@ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ValidationDto processValidationError(MethodArgumentNotValidException ex) {
	        BindingResult result = ex.getBindingResult();
	        
	        ValidationDto dto = new ValidationDto();
	        dto.setErrors(result.getAllErrors());;
	        return dto;
	    }
	 	
	 	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	    @ExceptionHandler(Throwable.class)
	    public void handleOthers(Throwable throwable) {
	 		throwable.printStackTrace();
	    }
	 	
	 	
}
