package com.agility.survey.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DerbySupportingSentencesDao implements SupportingSentencesDao {

	private SupportingSentencesResultSetExtractor supportingSentencesResultSetExtractor;
	private JdbcTemplate jdbc;

	@Autowired
	public DerbySupportingSentencesDao(JdbcTemplate jdbc, SupportingSentencesResultSetExtractor supportingSentencesresultSetExtractor) {
		super();
		this.jdbc = jdbc;
		this.supportingSentencesResultSetExtractor = supportingSentencesresultSetExtractor;
	}

	@Override
	public void create(long questionId, String low, String medium, String high) {
		jdbc.update("INSERT INTO SUPPORTINGSENTENCES VALUES (DEFAULT, ?, ?, ?, ?)", new Object[] { questionId, low, medium, high });
	}

	@Override
	public String[] select(long questionId) {
		return jdbc.query("SELECT LOW, MEDIUM, HIGH FROM SUPPORTINGSENTENCES WHERE QUESTIONID = ?", supportingSentencesResultSetExtractor, new Object[] { questionId });
	}
	
}
