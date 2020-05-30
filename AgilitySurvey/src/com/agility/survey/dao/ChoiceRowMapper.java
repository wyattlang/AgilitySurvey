package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Choice;

@Component
public class ChoiceRowMapper implements RowMapper<Choice> {

	private ChoiceResultSetExtractor extractor;
	
	@Autowired
	public ChoiceRowMapper(ChoiceResultSetExtractor extractor) {
		this.extractor = extractor;
	}
	
	@Override
	public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
		return extractor.extractData(rs);
	}

}
