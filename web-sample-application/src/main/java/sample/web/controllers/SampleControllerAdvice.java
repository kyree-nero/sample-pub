package sample.web.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SampleControllerAdvice {
	 	
		@ResponseStatus(HttpStatus.BAD_REQUEST)  // 404
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public void handleMethodNotValid() {
	        // Nothing to do
	    }
	 	
	 	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	    @ExceptionHandler(Exception.class)
	    public void handleOthers() {
	        // Nothing to do
	    }
	 	
	 	
}
