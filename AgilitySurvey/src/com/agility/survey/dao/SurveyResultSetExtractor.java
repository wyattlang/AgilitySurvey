package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Survey;

@Component
public class SurveyResultSetExtractor implements ResultSetExtractor<Survey> {

	@Override
	public Survey extractData(ResultSet rs) throws SQLException, DataAccessException {
		if(rs.next() != true) throw new SQLException("Empty ResultSet in SurveyResultSetExtractor");
		Survey survey = new Survey();
		survey.setSurveyId(rs.getLong("SURVEYID"));
		survey.setName(rs.getString("NAME"));
		return survey;
	}

}
