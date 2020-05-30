package com.agility.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Choice;

@Component
public class DerbyChoiceDao implements ChoiceDao {

	private JdbcTemplate jdbc;
	private ChoiceRowMapper rowMapper;
	
	@Autowired
	public DerbyChoiceDao(JdbcTemplate jdbc, ChoiceRowMapper rowMapper) {
		this.jdbc = jdbc;
		this.rowMapper = rowMapper;
	}
	
	@Override
	public void create(long questionId, String text, int weight) {
		jdbc.update("INSERT INTO CHOICES VALUES (DEFAULT, ?, ?, ?)", new Object[] { questionId, text, weight });
	}

	@Override
	public List<Choice> select(long questionId) {
		return jdbc.query("SELECT CHOICETEXT, WEIGHT FROM CHOICES WHERE QUESTIONID = ?", new Object[] { questionId }, rowMapper);
	}

}
