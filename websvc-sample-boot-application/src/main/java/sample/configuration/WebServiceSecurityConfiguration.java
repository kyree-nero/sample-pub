package sample.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;

@Configuration
public class WebServiceSecurityConfiguration {
	

	
	@Bean SimplePasswordValidationCallbackHandler passwordValidationCallbackHandler() {
		SimplePasswordValidationCallbackHandler bean = new SimplePasswordValidationCallbackHandler();
		Map<String, String> users = new HashMap<String, String>();
		users.put("joe", "blow");
		bean.setUsersMap(users);
		return bean;
		
	}
	
	
	@Bean 
	public Wss4jSecurityInterceptor wsSecurityInterceptor() {
		Wss4jSecurityInterceptor bean = new Wss4jSecurityInterceptor();
		bean.setValidationActions(WSHandlerConstants.USERNAME_TOKEN);
		bean.setValidationCallbackHandler(passwordValidationCallbackHandler());
		
		return bean;
	}
}
