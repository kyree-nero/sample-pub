package sample.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class SampleDao {
	@Autowired NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	public long findSampleCount() {
		return namedParameterJdbcOperations.queryForObject(
				"SELECT count(*) FROM SAMPLE", 
				new MapSqlParameterSource(), 
				Long.class);
	}
	
}
