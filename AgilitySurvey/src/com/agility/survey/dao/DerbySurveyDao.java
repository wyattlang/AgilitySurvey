package com.agility.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.Question;
import com.agility.survey.pojo.Survey;

@Component
public class DerbySurveyDao implements SurveyDao {

	private JdbcTemplate jdbc;
	private CategoryDao categoryDao;
	private QuestionDao questionDao;
	private SurveyNameRowMapper surveyNameRowMapper;
	private SurveyResultSetExtractor surveyExtractor;
	private SurveyIdResultSetExtractor idExtractor;
	
	@Autowired
	public DerbySurveyDao(JdbcTemplate jdbc, CategoryDao categoryDao, QuestionDao questionDao, SurveyNameRowMapper surveyNameRowMapper, 
			              SurveyResultSetExtractor surveyExtractor, SurveyIdResultSetExtractor idExtractor) {
		this.jdbc = jdbc;
		this.categoryDao = categoryDao;
		this.questionDao = questionDao;
		this.surveyNameRowMapper = surveyNameRowMapper;
		this.surveyExtractor = surveyExtractor;
		this.idExtractor = idExtractor;
	}

	@Override
	public void create(String name, String[] categories, Question[] questions) {
		jdbc.update("INSERT INTO SURVEYS VALUES (DEFAULT, ?, FALSE)", new Object[] { name });
		long surveyId = jdbc.query("SELECT SURVEYID FROM SURVEYS WHERE NAME = ?", idExtractor, new Object[] { name });
		for(String category : categories) {
			categoryDao.create(surveyId, category);
		}
		for(Question question : questions) {
			questionDao.create(surveyId, question.getPrompt(), question.getChoices(), question.getSupportingSentences(), 
					           question.getGradeThresholds(), question.getCategory());
		}
	}

	@Override
	public List<String> selectAllSurveyNames() {
		return jdbc.query("SELECT NAME FROM SURVEYS", surveyNameRowMapper);
	}

	@Override
	public Survey select(long surveyId) {
		Survey survey = jdbc.query("SELECT SURVEYID, NAME FROM SURVEYS WHERE SURVEYID = ?", surveyExtractor, new Object[] { surveyId });
		survey.setCategories(categoryDao.select(surveyId).toArray(new String[] {  }));
		survey.setQuestions(questionDao.select(surveyId).toArray(new Question[] {  }));
		return survey;
	}

	@Override
	public Survey select(String name) {
		Survey survey = jdbc.query("SELECT SURVEYID, NAME FROM SURVEYS WHERE NAME = ?", surveyExtractor, new Object[] { name });
		survey.setCategories(categoryDao.select(survey.getSurveyId()).toArray(new String[] {  }));
		survey.setQuestions(questionDao.select(survey.getSurveyId()).toArray(new Question[] {  }));
		return survey;
	}

	@Override
	public void setCurrentlyEnabled(String name) {
		jdbc.update("UPDATE SURVEYS SET ENABLED = FALSE WHERE ENABLED = TRUE");
		jdbc.update("UPDATE SURVEYS SET ENABLED = TRUE WHERE SURVEYID = ?", new Object[] { name });
	}

	@Override
	public Survey selectCurrentlyEnabled() {
		long surveyId = jdbc.query("SELECT SURVEYID FROM SURVEYS WHERE ENABLED = TRUE", idExtractor);
		return select(surveyId);
	}
	
}
