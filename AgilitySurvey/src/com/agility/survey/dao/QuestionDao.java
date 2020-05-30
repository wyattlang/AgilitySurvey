package com.agility.survey.dao;

import java.util.List;

import com.agility.survey.pojo.Choice;
import com.agility.survey.pojo.Question;

public interface QuestionDao {
	
	void create(long surveyId, String prompt, Choice[] choices, String[] supportingSentences, int[] gradeThresholds, String category);

	List<Question> select(long surveyId);
	
	default String toStringGradeThresholds(int[] gradeThresholdsAsInts) {
		return gradeThresholdsAsInts[0] + "," + gradeThresholdsAsInts[1];
	}
}
