package sample.websvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import sample.exception.SampleWebServiceException;
import sample.services.SampleService;
import samplewkspc.sample_web_service.FindSampleRequest;
import samplewkspc.sample_web_service.FindSampleResponse;

@Endpoint
public class SampleWebServiceEndpoint {
	private static final String NAMESPACE_URI = "http://samplewkspc/sample-web-service";
	
	@Autowired SampleService sampleService;
	@Autowired WebSvcSampleConverter sampleConverter;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findSampleRequest")
	@ResponsePayload
	public FindSampleResponse findSample(@RequestPayload FindSampleRequest request) {
		try {
			FindSampleResponse response = new FindSampleResponse();
			response.setSample(sampleConverter.convert(sampleService.findSample(request.getId())));
			return response;
		}catch(RuntimeException exception) {
			throw new SampleWebServiceException(exception);
		}
	}

}
