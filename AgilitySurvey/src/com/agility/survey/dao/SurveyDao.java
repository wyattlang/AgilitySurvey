package com.agility.survey.dao;

import java.util.List;

import com.agility.survey.pojo.Question;
import com.agility.survey.pojo.Survey;

public interface SurveyDao {

	void create(String name, String[] categories, Question[] questions);

	List<String> selectAllSurveyNames();
	
	Survey select(long surveyId);
	
	Survey select(String name);
	
	void setCurrentlyEnabled(String name);

	Survey selectCurrentlyEnabled();
}
