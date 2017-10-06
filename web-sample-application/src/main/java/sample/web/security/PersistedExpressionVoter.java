package sample.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import sample.persistence.entities.AuthExpression;
import sample.services.AuthorizationService;

public class PersistedExpressionVoter implements AccessDecisionVoter<FilterInvocation>{
	
	private AuthorizationService authorizationService;
	
	public PersistedExpressionVoter(AuthorizationService authorizationService) {
		super();
		this.authorizationService = authorizationService;
	}

	private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if(clazz.equals(FilterInvocation.class)) {
			return true;
		}
		return false;
	}

	@Override
	public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
		
			FilterInvocation filterInvocation = (FilterInvocation)object;
			
			AuthExpression authExpression =  findMatchingRequestUrlKey(filterInvocation.getRequestUrl());
			if(authExpression == null) {
				System.err.println("Access Denied: no auth expression found for " + filterInvocation.getRequestUrl());
				return ACCESS_DENIED;
			}
			String expressionAsString = authExpression.getPolicyExpression();
			EvaluationContext expressionEvalContext = expressionHandler.createEvaluationContext(authentication, filterInvocation);
			Expression expression = expressionHandler.getExpressionParser().parseExpression(expressionAsString);
			Boolean hasAccess =  expression.getValue(expressionEvalContext, Boolean.class);
			if(hasAccess) {
				return ACCESS_GRANTED;
			}else {
				System.err.println("Access Denied: auth expression denial " + filterInvocation.getRequestUrl());
				return ACCESS_DENIED;
			}
		
	}
	
	private AuthExpression findMatchingRequestUrlKey(String url) {
		
		List<AuthExpression> authExpressions = authorizationService.findAll();
		for(AuthExpression authExpression : authExpressions) {
//			System.out.println("------");
//			System.out.println("url=" + url);
//			System.out.println("resource="+authExpression.getResource());
			if(url.matches(authExpression.getResource())) {
//				System.out.println("found");
				return authExpression;
			}
		}
		return null;
	}
}
