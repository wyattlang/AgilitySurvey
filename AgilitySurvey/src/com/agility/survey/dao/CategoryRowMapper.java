package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryRowMapper implements RowMapper<String> {

	private CategoryResultSetExtractor extractor;
	
	@Autowired
	public CategoryRowMapper(CategoryResultSetExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		return extractor.extractData(rs);
	}

}
