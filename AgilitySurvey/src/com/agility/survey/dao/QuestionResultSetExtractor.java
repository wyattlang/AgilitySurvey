package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Question;

@Component
public class QuestionResultSetExtractor implements ResultSetExtractor<Question> {
	
	@Override
	public Question extractData(ResultSet rs) throws SQLException, DataAccessException {
		Question question = new Question();
		question.setQuestionId(rs.getLong("QUESTIONID"));
		question.setCategory(rs.getString("CATEGORY"));
		question.setPrompt(rs.getString("PROMPT"));
		question.setGradeThresholds(parseGradeThresholds(rs.getString("GRADETHRESHOLDS")));
		return question;
	}
	
	private int[] parseGradeThresholds(String toParse) {
		String[] number = toParse.split(",");
		return new int[] { Integer.parseInt(number[0]), Integer.parseInt(number[1]) };
	}

}
