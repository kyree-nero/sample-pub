package sample;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;

import samplewkspc.sample_web_service.FindSampleRequest;
import samplewkspc.sample_web_service.FindSampleResponse;
import samplewkspc.sample_web_service.Sample;

public class SampleWebServiceIT extends AbstractWebServiceIT {
	
    
    @Autowired private Marshaller marshaller;
    @Autowired MockMvc mockMvc;
    
//    @Test public void findWsdl() throws Exception{
//    	mockMvc.perform(MockMvcRequestBuilders.get("samples.wsdl")).andDo(MockMvcResultHandlers.print());
//    }
//    
   
	@Test public void findSampleByIdThatIsMissing() throws Exception {
		//make request
		FindSampleRequest findSampleRequest = new FindSampleRequest();
		findSampleRequest.setId(-1);
		StringResult requestString = new StringResult();
		marshaller.marshal(findSampleRequest, requestString);

		Document content = loadXMLFrom(requestString.toString());
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		soapMessage.getSOAPBody().addDocument(content);
		addUsernameTokenSecurityHeader(soapMessage);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		soapMessage.writeTo(outputStream);
		String input = new String(outputStream.toByteArray());
		
		System.out.println(prettyPrint(input));
		Source requestPayload = new StringSource(input);
		
		
		
		//do test
		mockClient
	        .sendRequest(RequestCreators.withSoapEnvelope(requestPayload))
	        .andExpect(ResponseMatchers.serverOrReceiverFault("Please contact your administrator"));
	}
	
	@Test public void findSampleById() throws Exception {
		//make request
		FindSampleRequest findSampleRequest = new FindSampleRequest();
		findSampleRequest.setId(0);
		StringResult requestString = new StringResult();
		marshaller.marshal(findSampleRequest, requestString);

		Document content = loadXMLFrom(requestString.toString());
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		soapMessage.getSOAPBody().addDocument(content);
		addUsernameTokenSecurityHeader(soapMessage);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		soapMessage.writeTo(outputStream);
		String input = new String(outputStream.toByteArray());
		
		System.out.println(prettyPrint(input));
		Source requestPayload = new StringSource(input);
		
		
		//make response
		FindSampleResponse findSampleResponse = new FindSampleResponse();
		Sample sample = new Sample();
		sample.setId(0);
		sample.setContent("hi im a sample");
		sample.setVersion(0);
		findSampleResponse.setSample(sample);
		
		StringResult responseString = new StringResult();
		marshaller.marshal(findSampleResponse, responseString);

		System.out.println(prettyPrint(responseString.toString()));
		Source responsePayload = new StringSource(responseString.toString());
		
		//do test
		mockClient
	        .sendRequest(RequestCreators.withSoapEnvelope(requestPayload))
	        .andExpect(ResponseMatchers.noFault())
	        .andExpect(ResponseMatchers.payload(responsePayload))
	        .andExpect(ResponseMatchers.validPayload(xsdSchema));
	}
	
	
	@Test public void findSampleByIdWithoutAuthentication() throws Exception {
		//make request
		FindSampleRequest findSampleRequest = new FindSampleRequest();
		findSampleRequest.setId(0);
		StringResult requestString = new StringResult();
		marshaller.marshal(findSampleRequest, requestString);

		Document content = loadXMLFrom(requestString.toString());
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		soapMessage.getSOAPBody().addDocument(content);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		soapMessage.writeTo(outputStream);
		String input = new String(outputStream.toByteArray());
		
		System.out.println(prettyPrint(input));
		Source requestPayload = new StringSource(input);
		
		//do test
		mockClient
	        .sendRequest(RequestCreators.withSoapEnvelope(requestPayload))
	        .andExpect(ResponseMatchers.clientOrSenderFault());
	}
}



