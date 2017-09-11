package sample.services;

import javax.transaction.Transactional;

import sample.persistence.entities.AuthExpression;

public interface AuthorizationService {
	
	@Transactional 
	public AuthExpression findExpression(String key);
}
