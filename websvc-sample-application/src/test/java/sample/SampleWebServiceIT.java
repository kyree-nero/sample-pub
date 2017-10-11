package sample;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.soap.SOAPHeaderElement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
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
    
    
    public  Document loadXMLFrom(String xml) throws TransformerException {
    	
        Source source = new StreamSource(new StringReader(xml));
        DOMResult result = new DOMResult();
        TransformerFactory.newInstance().newTransformer().transform(source , result);
        return (Document) result.getNode();
    }   
    
	@Test public void findSamples() throws Exception {
		//make request
		FindSampleRequest findSampleRequest = new FindSampleRequest();
		findSampleRequest.setId(0);
		StringResult requestString = new StringResult();
		marshaller.marshal(findSampleRequest, requestString);

		System.out.println(requestString);
		
		Document content = loadXMLFrom(requestString.toString());
		
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		soapMessage.getSOAPBody().addDocument(content);

		SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
		String securityNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
		envelope.addNamespaceDeclaration("wsse", securityNamespace);
	
		QName securityQName = soapMessage.getSOAPHeader().createQName("Security", "wsse");
		SOAPHeaderElement securityElement = soapMessage.getSOAPHeader().addHeaderElement(securityQName);
		QName userNameTokenQName = soapMessage.getSOAPHeader().createQName("UsernameToken", "wsse");
		SOAPElement userNameTokenElement = securityElement.addChildElement(userNameTokenQName);
		QName userNameQName = soapMessage.getSOAPHeader().createQName("Username", "wsse");
		SOAPElement userNameElement = userNameTokenElement.addChildElement(userNameQName);
		userNameElement.addTextNode("joe");
		QName passwordTokenQName = soapMessage.getSOAPHeader().createQName("Password", "wsse");
		SOAPElement passwordTokenElement = userNameTokenElement.addChildElement(passwordTokenQName);
		passwordTokenElement.addTextNode("blow");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		soapMessage.writeTo(outputStream);
		String output = new String(outputStream.toByteArray());
		
		System.out.println(output);
		Source requestPayload = new StringSource(output);
		
		
		//make response
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
		
		//do test
		mockClient
	        .sendRequest(RequestCreators.withSoapEnvelope(requestPayload))
	        .andExpect(ResponseMatchers.noFault())
	        .andExpect(ResponseMatchers.payload(responsePayload))
	        .andExpect(ResponseMatchers.validPayload(xsdSchema));
	}
}



