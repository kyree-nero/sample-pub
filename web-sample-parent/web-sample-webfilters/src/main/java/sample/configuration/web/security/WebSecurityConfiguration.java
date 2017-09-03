package sample.configuration.web.security;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleMappableAttributesRetriever;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	  auth.inMemoryAuthentication().withUser("user").password("123456").roles("USER");
//	  auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//	  auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		
			.formLogin().disable()
			.authenticationProvider(authenticationProvider())
			.addFilter(preAuthenticatedProcessingFilter())
		
			.authorizeRequests().anyRequest().authenticated()
			
		;
		
		
		http
			.headers()
					/*
					 * https://spring.io/blog/2013/08/23/spring-security-3-2-0-rc1-highlights-security-headers
					 * 
					 * Content sniffing can be disabled by adding the following header to our response:
					 */
				.contentTypeOptions()
				.and()
				.frameOptions()
					/*
					 * https://spring.io/blog/2013/08/23/spring-security-3-2-0-rc1-highlights-security-headers
					 * 
					 * Allowing your website to be added to a frame can be a security issue. For example, using 
					 * clever CSS styling users could be tricked into clicking on something that they were not 
					 * intending (video demo). For example, a user that is logged into their bank might click a 
					 * button that grants access to other users. This sort of attack is known as Clickjacking.
					 */
					.deny()  
					/*
					 * https://spring.io/blog/2013/08/23/spring-security-3-2-0-rc1-highlights-security-headers
					 * 
					 * Some browsers have built in support for filtering out reflected XSS attacks. This is by no means full proof, but does assist in XSS protection.

						The filtering is typically enabled by default, so adding the header typically just ensures 
						it is enabled and instructs the browser what to do when a XSS attack is detected. For example, 4
						the filter might try to change the content in the least invasive way to still render everything. 
						At times, this type of replacement can become a XSS vulnerability in itself. Instead, it 
						is best to block the content rather than attempt to fix it. To do this we can add the following header:
					 */
					.xssProtection() 
					;
	}
	
	
	
	@Bean J2eePreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception{
		J2eePreAuthenticatedProcessingFilter bean = new J2eePreAuthenticatedProcessingFilter();
		bean.setAuthenticationManager(authenticationManagerBean());
		bean.setAuthenticationDetailsSource(authenticationDetailsSource());
		return bean;
	}

	@Bean PreAuthenticatedAuthenticationProvider authenticationProvider() {
		PreAuthenticatedAuthenticationProvider bean = new PreAuthenticatedAuthenticationProvider();
		bean.setPreAuthenticatedUserDetailsService((AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>)userDetailSvc());
		return bean;
		
	}
	
	@Bean PreAuthenticatedGrantedAuthoritiesUserDetailsService userDetailSvc() {
		PreAuthenticatedGrantedAuthoritiesUserDetailsService bean = new PreAuthenticatedGrantedAuthoritiesUserDetailsService();
		return bean;
	}

	
	@Bean AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> authenticationDetailsSource(){
		J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource bean = new J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource();
		bean.setMappableRolesRetriever(mappableRolesRetriever());
		bean.setUserRoles2GrantedAuthoritiesMapper(userRoles2GrantedAuthoritiesMapper());
		return bean;
	}
	
	@Bean SimpleAttributes2GrantedAuthoritiesMapper userRoles2GrantedAuthoritiesMapper() {
		return new SimpleAttributes2GrantedAuthoritiesMapper();
	}
	
	@Bean SimpleMappableAttributesRetriever mappableRolesRetriever() {
		SimpleMappableAttributesRetriever bean = new SimpleMappableAttributesRetriever();
		HashSet<String> roles = new HashSet<String>();
		roles.add("USERS_ROLE");
		bean.setMappableAttributes(roles);
		return bean;
	}
}
