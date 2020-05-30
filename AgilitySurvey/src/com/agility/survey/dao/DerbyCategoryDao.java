package com.agility.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DerbyCategoryDao implements CategoryDao {

	private JdbcTemplate jdbc;
	private CategoryRowMapper rowMapper;
	
	@Autowired
	public DerbyCategoryDao(JdbcTemplate jdbc, CategoryRowMapper rowMapper) {
		this.jdbc = jdbc;
		this.rowMapper = rowMapper;
	}

	@Override
	public void create(long surveyId, String category) {
		jdbc.update("INSERT INTO CATEGORIES VALUES (DEFAULT, ?, ?)", new Object[] { surveyId, category });
	}

	@Override
	public List<String> select(long surveyId) {
		return jdbc.query("SELECT NAME FROM CATEGORIES WHERE SURVEYID = ?", rowMapper, new Object[] { surveyId });
	}

}
