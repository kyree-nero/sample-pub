package sample.web.domain;

import org.springframework.validation.Errors;

public class SampleJsonResponse<T> {
	private Errors errors;
	
	private T data;

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
