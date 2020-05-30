package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Choice;

@Component
public class ChoiceResultSetExtractor implements ResultSetExtractor<Choice> {

	@Override
	public Choice extractData(ResultSet rs) throws SQLException, DataAccessException {
		Choice choice = new Choice();
		choice.setText(rs.getString("CHOICETEXT"));
		choice.setWeight(rs.getInt("WEIGHT"));
		return choice;
	}

}
