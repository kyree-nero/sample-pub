package sample.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.SERVER, faultStringOrReason="Please contact your administrator")
public class SampleWebServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193237489948130448L;

	public SampleWebServiceException(String message) {
		super(message);
	}

	public SampleWebServiceException(Throwable cause) {
		super(cause);
	}

	
}
