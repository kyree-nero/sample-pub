package sample;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import samplewkspc.sample_web_service.FindSampleRequest;
import samplewkspc.sample_web_service.FindSampleResponse;
import samplewkspc.sample_web_service.Sample;

public class SampleWebServiceIT extends AbstractWebServiceIT {
	
   
    @Autowired private Marshaller marshaller;
    
    
	
    
	@Test public void findSamples() throws Exception {
		
		FindSampleRequest findSampleRequest = new FindSampleRequest();
		findSampleRequest.setId(0);
		StringResult requestString = new StringResult();
		marshaller.marshal(findSampleRequest, requestString);

		System.out.println(requestString);
		Source requestPayload = new StringSource(requestString.toString());
		
		
		FindSampleResponse findSampleResponse = new FindSampleResponse();
		Sample sample = new Sample();
		sample.setId(0);
		sample.setContent("hi im a sample");
		sample.setVersion(0);
		findSampleResponse.setSample(sample);
		
		StringResult responseString = new StringResult();
		marshaller.marshal(findSampleResponse, responseString);

		System.out.println(responseString);
		Source responsePayload = new StringSource(responseString.toString());
		
		
		mockClient
	        .sendRequest(RequestCreators.withPayload(requestPayload))
	        .andExpect(ResponseMatchers.noFault())
	        .andExpect(ResponseMatchers.payload(responsePayload))
	        .andExpect(ResponseMatchers.validPayload(xsdSchema));
	}
}
