package sample.services;

import java.util.List;

import javax.transaction.Transactional;

import sample.persistence.entities.AuthExpression;

@Transactional 
public interface AuthorizationService {
	
	
	public AuthExpression findExpression(String key);
	public List<AuthExpression> findAll();
}
