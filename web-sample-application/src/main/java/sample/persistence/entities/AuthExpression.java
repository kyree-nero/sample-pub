package sample.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH_EXPR")
public class AuthExpression {
	
	
	@Id @Column private String resource;
	@Column(name="policy_expr") private String policyExpression;
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getPolicyExpression() {
		return policyExpression;
	}
	public void setPolicyExpression(String policyExpression) {
		this.policyExpression = policyExpression;
	}
	
	
}
