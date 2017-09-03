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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;

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
			.addFilterBefore(authenticatingFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests().anyRequest().authenticated()
			
		;
		
		
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
	
	@Bean J2eePreAuthenticatedProcessingFilter authenticatingFilter() throws Exception{
		J2eePreAuthenticatedProcessingFilter bean = new J2eePreAuthenticatedProcessingFilter();
		bean.setAuthenticationManager(authenticationManagerBean());
		bean.setAuthenticationDetailsSource(authenticationDetailsSource());
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
