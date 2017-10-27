package sample.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.persistence.entities.AuthExpression;

public interface AuthExpressionRepository  extends JpaRepository<AuthExpression, String>{

}
