CREATE TABLE SAMPLE (
	 id INT not null, 
	 content VARCHAR(100) not null
); 

CREATE TABLE AUTH_EXPR(
	resource 		VARCHAR(200) not null,
	policy_expr 	VARCHAR(500) not null
);


COMMIT;