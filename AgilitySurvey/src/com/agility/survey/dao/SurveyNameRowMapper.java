package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SurveyNameRowMapper implements RowMapper<String> {

	private SurveyNameResultSetExtractor extractor;
	
	@Autowired
	public SurveyNameRowMapper(SurveyNameResultSetExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		return extractor.extractData(rs);
	}

}
