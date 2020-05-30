package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SupportingSentencesResultSetExtractor implements ResultSetExtractor<String[]> {

	@Override
	public String[] extractData(ResultSet rs) throws SQLException, DataAccessException {
		String[] supportingSentences = new String[3];
		if(rs.next()) {
			supportingSentences[0] = rs.getString("LOW");
			supportingSentences[1] = rs.getString("MEDIUM");
			supportingSentences[2] = rs.getString("HIGH");
		}
		return supportingSentences;
	}

}
