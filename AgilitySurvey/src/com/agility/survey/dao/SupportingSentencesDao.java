package com.agility.survey.dao;

public interface SupportingSentencesDao {

	void create(long questionId, String low, String medium, String high);
	
	String[] select(long questionId);
}
