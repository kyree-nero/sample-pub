package sample.web.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SampleControllerAdvice {
	 	@ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
	    @ExceptionHandler(AccessDeniedException.class)
	    public void handleAccessDenied() {
	        // Nothing to do
	    }
	 	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	    @ExceptionHandler(Exception.class)
	    public void handleOthers() {
	        // Nothing to do
	    }
	 	
	 	
	 	
}
