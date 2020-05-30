package com.agility.survey.dao;

import java.util.List;

public interface CategoryDao {

	void create(long surveyId, String category);
	
	List<String> select(long surveyId);
	
}
