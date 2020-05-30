package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Question;

@Component
public class QuestionRowMapper implements RowMapper<Question> {

	private QuestionResultSetExtractor extractor;
	
	@Autowired
	public QuestionRowMapper(QuestionResultSetExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		return extractor.extractData(rs);
	}

	
}
