package sample.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.persistence.entities.AuthExpression;
import sample.persistence.repositories.AuthExpressionRepository;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{
	@Autowired private AuthExpressionRepository authExpressionRepository;
	
	@Transactional 
	public AuthExpression findExpression(String key) {
		return authExpressionRepository.findOne(key);
	}

	@Override
	public List<AuthExpression> findAll() {
		return authExpressionRepository.findAll();
	}
}
