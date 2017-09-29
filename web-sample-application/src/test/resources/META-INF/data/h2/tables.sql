--CREATE SEQUENCE SEQ_SAMPLE;

CREATE TABLE SAMPLE (
	 --id INT not null DEFAULT SEQ_SAMPLE.nextval primary key,
	 id bigint auto_increment not null  primary key , 
	 content VARCHAR(100) not null,
	 version INT not null,
); 

CREATE TABLE AUTH_EXPR(
	resource 		VARCHAR(200) not null,
	policy_expr 	VARCHAR(500) not null
);


COMMIT;