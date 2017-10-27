package sample;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Document;

@WebAppConfiguration 
@AutoConfigureMockMvc
public abstract class AbstractWebServiceIT extends AbstractBaseIT{
	
	protected MockWebServiceClient mockClient;
    protected Resource xsdSchema = new ClassPathResource("xsd/sample.xsd");

    @Autowired private ApplicationContext applicationContext;
    
    @Before
    public void before(){
    
    	mockClient = MockWebServiceClient.createClient(applicationContext);
    }
    
    protected void addUsernameTokenSecurityHeader(SOAPMessage soapMessage) throws SOAPException{
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
    }
    
    public  Document loadXMLFrom(String xml) throws TransformerException {
    	
        Source source = new StreamSource(new StringReader(xml));
        DOMResult result = new DOMResult();
        TransformerFactory.newInstance().newTransformer().transform(source , result);
        return (Document) result.getNode();
    }   
    
    public String prettyPrint(String unformattedXMLString)  throws Exception{
    	Document doc = loadXMLFrom(unformattedXMLString);
    	
    	return printDocument(doc);
    	
    	
    }
    
    public String printDocument(Document doc) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StringResult result = new StringResult();
        transformer.transform(new DOMSource(doc), 
             result);
        return result.toString();
    }
}
