package com.agility.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Choice;
import com.agility.survey.pojo.Question;

@Component
public class DerbyQuestionDao implements QuestionDao {

	private JdbcTemplate jdbc;
	private QuestionRowMapper rowMapper;
	private QuestionIdResultSetExtractor IdExtractor;
	private ChoiceDao choiceDao;
	private SupportingSentencesDao supportingSentencesDao;
	
	@Autowired
	public DerbyQuestionDao(JdbcTemplate jdbc, QuestionRowMapper rowMapper, 
			                QuestionIdResultSetExtractor IdExtractor, ChoiceDao choiceDao, 
			                SupportingSentencesDao supportingSentencesDao) {
		this.jdbc = jdbc;
		this.rowMapper = rowMapper;
		this.IdExtractor = IdExtractor;
		this.choiceDao = choiceDao;
		this.supportingSentencesDao = supportingSentencesDao;
	}

	@Override
	public void create(long surveyId, String prompt, Choice[] choices, String[] supportingSentences,
					   int[] gradeThresholdsAsInts, String category) {
		String gradeThresholdsAsString = toStringGradeThresholds(gradeThresholdsAsInts);
		Object[] args = new Object[] { surveyId, prompt, gradeThresholdsAsString, category };
		jdbc.update("INSERT INTO QUESTIONS VALUES (DEFAULT, ?, ?, ?, ?)", args);
		long questionId = jdbc.query("SELECT QUESTIONID FROM QUESTIONS WHERE SURVEYID = ? AND PROMPT = ? AND GRADETHRESHOLDS = ? AND CATEGORY = ?", 
				                     IdExtractor, args);
		for(Choice choice : choices) {
			choiceDao.create(questionId, choice.getText(), choice.getWeight());
		}
		supportingSentencesDao.create(questionId, supportingSentences[0], supportingSentences[1], supportingSentences[2]);
		
	}

	@Override
	public List<Question> select(long surveyId) {
		List<Question> questions = jdbc.query("SELECT * FROM QUESTIONS WHERE SURVEYID = ?", new Object[] { surveyId }, rowMapper);
		for(Question question : questions) {
			question.setChoices(choiceDao.select(question.getQuestionId()).toArray(new Choice[] { }));
			question.setSupportingSentences(supportingSentencesDao.select(question.getQuestionId()));
		}
		return questions;
	}
	
}
