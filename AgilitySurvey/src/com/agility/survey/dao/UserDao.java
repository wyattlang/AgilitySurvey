package com.agility.survey.dao;

import com.agility.survey.pojo.User;

public interface UserDao {

	void create(long surveyId, String firstName, String lastName, String email, String comment);

	long selectUserId(User user, long surveyId);

	String selectUserDiscountCode(long userId);
	
}
