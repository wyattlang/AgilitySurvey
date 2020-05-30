package com.agility.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agility.survey.dao.SurveyDao;

@Service
public class SurveyNamesService {

	private SurveyDao dao;
	
	@Autowired
	public SurveyNamesService(SurveyDao dao) {
		this.dao = dao;
	}

	public String[] getAllSurveyNames() {
		return dao.selectAllSurveyNames().toArray(new String[] {  });
	}
}
