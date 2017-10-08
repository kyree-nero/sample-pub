
CREATE TABLE SAMPLE (
	 --id INT not null DEFAULT SEQ_SAMPLE.nextval primary key,
	 id bigint auto_increment not null  primary key , 
	 content VARCHAR(100) not null,
	 version INT not null,
); 



COMMIT;