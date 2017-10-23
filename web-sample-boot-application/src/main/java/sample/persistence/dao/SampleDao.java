package sample.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class SampleDao {
	static final Logger LOG = LoggerFactory.getLogger(SampleDao.class);
	
	@Autowired NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	public long findSampleCount() {
		LOG.info("my log statement");
		return namedParameterJdbcOperations.queryForObject(
				"SELECT count(*) FROM SAMPLE", 
				new MapSqlParameterSource(), 
				Long.class);
	}
	
}
