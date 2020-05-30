package com.agility.survey.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.agility.survey.dao.CategoryDao;
import com.agility.survey.dao.CategoryResultSetExtractor;
import com.agility.survey.dao.CategoryRowMapper;
import com.agility.survey.dao.ChoiceDao;
import com.agility.survey.dao.ChoiceResultSetExtractor;
import com.agility.survey.dao.ChoiceRowMapper;
import com.agility.survey.dao.DerbyCategoryDao;
import com.agility.survey.dao.DerbyChoiceDao;
import com.agility.survey.dao.DerbyQuestionDao;
import com.agility.survey.dao.DerbySupportingSentencesDao;
import com.agility.survey.dao.DerbySurveyDao;
import com.agility.survey.dao.QuestionDao;
import com.agility.survey.dao.QuestionIdResultSetExtractor;
import com.agility.survey.dao.QuestionResultSetExtractor;
import com.agility.survey.dao.QuestionRowMapper;
import com.agility.survey.dao.SupportingSentencesDao;
import com.agility.survey.dao.SupportingSentencesResultSetExtractor;
import com.agility.survey.dao.SurveyDao;
import com.agility.survey.dao.SurveyIdResultSetExtractor;
import com.agility.survey.dao.SurveyNameResultSetExtractor;
import com.agility.survey.dao.SurveyNameRowMapper;
import com.agility.survey.dao.SurveyResultSetExtractor;

public class ApplicationConfigurationTest {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		dataSource.setUrl("jdbc:derby:memory:surveydbtest;create=true");
		dataSource.setUsername("");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
		
	@Bean
	public SupportingSentencesResultSetExtractor supportingSentencesResultSetExtractor() {
		return new SupportingSentencesResultSetExtractor();
	}
	
	@Bean
	public SupportingSentencesDao supportingSentencesDao() {
		return new DerbySupportingSentencesDao(jdbcTemplate(), supportingSentencesResultSetExtractor());
	}

	@Bean
	public ChoiceResultSetExtractor choiceResultSetExtractor() {
		return new ChoiceResultSetExtractor();
	}
	
	@Bean
	public ChoiceRowMapper choiceRowMapper() {
		return new ChoiceRowMapper(choiceResultSetExtractor());
	}
	
	@Bean
	public ChoiceDao choiceDao() {
		return new DerbyChoiceDao(jdbcTemplate(), choiceRowMapper());
	}
	
	@Bean
	public QuestionResultSetExtractor questionResultSetExtractor() {
		return new QuestionResultSetExtractor();
	}
	
	@Bean
	public QuestionIdResultSetExtractor questionIdResultSetExtractor() {
		return new QuestionIdResultSetExtractor();
	}
	
	@Bean
	public QuestionRowMapper questionRowMapper() {
		return new QuestionRowMapper(questionResultSetExtractor());
	}
	
	@Bean
	public QuestionDao questionDao() {
		return new DerbyQuestionDao(jdbcTemplate(), questionRowMapper(), questionIdResultSetExtractor(), 
				                    choiceDao(), supportingSentencesDao());
	}
	
	@Bean
	public CategoryResultSetExtractor categoryResultSetExtractor() {
		return new CategoryResultSetExtractor();
	}
	
	@Bean
	public CategoryRowMapper categoryRowMapper() {
		return new CategoryRowMapper(categoryResultSetExtractor());
	}
 
	@Bean
	public CategoryDao categoryDao() {
		return new DerbyCategoryDao(jdbcTemplate(), categoryRowMapper());
	}
	
	@Bean
	public SurveyResultSetExtractor surveyResultSetExtractor() {
		return new SurveyResultSetExtractor();
	}
	
	@Bean
	public SurveyIdResultSetExtractor surveyIdResultSetExtractor() {
		return new SurveyIdResultSetExtractor();
	}
	
	@Bean
	public SurveyNameResultSetExtractor surveyNameResultSetExtractor() {
		return new SurveyNameResultSetExtractor();
	}
	
	@Bean
	public SurveyNameRowMapper surveyNameRowMapper() {
		return new SurveyNameRowMapper(surveyNameResultSetExtractor());
	}
	
	@Bean
	public SurveyDao surveyDao() {
		return new DerbySurveyDao(jdbcTemplate(), categoryDao(), questionDao(), surveyNameRowMapper(), 
				                  surveyResultSetExtractor(), surveyIdResultSetExtractor());
	}
}
