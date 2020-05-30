package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SurveyIdResultSetExtractor implements ResultSetExtractor<Long> {

	@Override
	public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
		if(rs.next() != true) throw new SQLException("Empty ResultSet in SurveyIdResultSetExtractor");
		return rs.getLong("SURVEYID");
	}

}
