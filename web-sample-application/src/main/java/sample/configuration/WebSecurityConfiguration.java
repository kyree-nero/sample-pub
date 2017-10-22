package sample.configuration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleMappableAttributesRetriever;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

import sample.services.AuthorizationService;
import sample.web.security.CsrfTokenResponseFilter;
import sample.web.security.PersistedExpressionVoter;
import sample.web.security.PreAuthenticatedLocalTestFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {
	@Autowired AuthorizationService authorizationService;
	@Autowired Environment environment;
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring()
		.antMatchers("/resources/js/**")
		.antMatchers("/resources/images/**")
		;
	}
	
	private boolean hasProfile(String[] activeProfiles, String search) {

		for(String activeProfile:activeProfiles) {
			if(search.equals(activeProfile)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			
			.addFilter(preAuthenticatedProcessingFilter())
			.addFilterAfter(csrfTokenResponseFilter(), CsrfFilter.class)
			.formLogin()
				/*
				 * Since we are using basic authentication to simulate 
				 * a preauthenticated setup, we can not use full login/logoff features
				 * as basic authentication is sticky
				 */
				.disable()
				//.defaultSuccessUrl("/index.html")
			
			.logout()
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/loggedOut.html")
				
			.and()
				.authenticationProvider(authenticationProvider())
				.authorizeRequests().anyRequest().authenticated().accessDecisionManager(accessDecisionManager())
			.and()
		      .csrf();
		;
		
		if(!hasProfile(environment.getActiveProfiles(), "test")){
			http.addFilterBefore(preAuthenticatedLocalTestFilter(), J2eePreAuthenticatedProcessingFilter.class );
		}
		
		http.exceptionHandling().authenticationEntryPoint(authentionEntryPoint());
		
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
	
	@Bean public CsrfTokenResponseFilter csrfTokenResponseFilter() {
		return new CsrfTokenResponseFilter();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
	    List<AccessDecisionVoter<? extends Object>> decisionVoters 
	      = Arrays.asList(
//	        new WebExpressionVoter(),
//	        new RoleVoter(),
//	        new AuthenticatedVoter(),
	        new PersistedExpressionVoter(authorizationService));
	    UnanimousBased bean = new UnanimousBased(decisionVoters);
	    bean.setAllowIfAllAbstainDecisions(true);
	    return bean;
	}
	
	@Bean AuthenticationEntryPoint authentionEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
	
	@Bean PreAuthenticatedLocalTestFilter preAuthenticatedLocalTestFilter() throws Exception{
		PreAuthenticatedLocalTestFilter bean = new PreAuthenticatedLocalTestFilter();
		return bean;
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
		roles.add("USERS");
		bean.setMappableAttributes(roles);
		return bean;
	}
}
