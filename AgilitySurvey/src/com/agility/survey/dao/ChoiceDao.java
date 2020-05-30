package com.agility.survey.dao;

import java.util.List;

import com.agility.survey.pojo.Choice;

public interface ChoiceDao {

	void create(long questionId, String text, int weight);

	List<Choice> select(long questionId);
}
