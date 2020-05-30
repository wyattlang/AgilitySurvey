package com.agility.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agility.survey.dao.UserDao;
import com.agility.survey.pojo.User;

@Service
public class StoreUserInfoService {

	private UserDao dao;

	@Autowired
	public StoreUserInfoService(UserDao dao) {
		this.dao = dao;
	}
	
	public void store(User user, long surveyId, String comment) {
		dao.create(surveyId, user.getFirstName(), user.getLastName(), user.getEmail(), comment);
	}

	public User getDiscountCode(User user, long surveyId) {	
		user.setUserId(dao.selectUserId(user, surveyId));
		user.setDiscountCode(dao.selectUserDiscountCode(user.getUserId()));
		return user;
	}
	
}
