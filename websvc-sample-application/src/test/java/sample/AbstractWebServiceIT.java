package sample;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ws.test.server.MockWebServiceClient;

import sample.configuration.PersistenceConfiguration;
import sample.configuration.ServicesConfiguration;
import sample.configuration.WebServiceClientConfiguration;
import sample.configuration.WebServiceConfiguration;

@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, 
	classes= {
			PersistenceConfiguration.class, 
			ServicesConfiguration.class, 
			WebServiceConfiguration.class, 
			WebServiceClientConfiguration.class
	}
)
@WebAppConfiguration 
public abstract class AbstractWebServiceIT extends AbstractPersistenceIT{
	protected MockWebServiceClient mockClient;
    protected Resource xsdSchema = new ClassPathResource("xsd/sample.xsd");

    @Autowired private ApplicationContext applicationContext;
    
    @Before
    public void before(){
    
    	mockClient = MockWebServiceClient.createClient(applicationContext);
    }
}
