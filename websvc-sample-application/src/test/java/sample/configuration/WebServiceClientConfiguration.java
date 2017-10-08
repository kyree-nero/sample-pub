package sample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class WebServiceClientConfiguration {
	@Bean Jaxb2Marshaller marshaller() { 
		Jaxb2Marshaller bean = new Jaxb2Marshaller();
		bean.setContextPaths("samplewkspc.sample_web_service");
		
		return bean;
	}
	
//	@Bean WebServiceTemplate sampleWebServiceTemplate(){
//		WebServiceTemplate bean = new WebServiceTemplate(marshaller());
//		bean.setMarshaller(marshaller());
//		//bean.setd
//		return bean;
//	}
}
