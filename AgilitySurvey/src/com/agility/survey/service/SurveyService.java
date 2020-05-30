package com.agility.survey.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agility.survey.dao.SurveyDao;
import com.agility.survey.pojo.Survey;

@Service
public class SurveyService {

	private SurveyDao dao;

	@Autowired
	public SurveyService(SurveyDao dao) {
		this.dao = dao;
	}
	
	public Survey getCurrentlyEnabledSurvey() {
		return dao.selectCurrentlyEnabled();
	}	
	
	public void gradeSurvey(HttpServletRequest request, Survey survey) {
		Enumeration<String> parameters = request.getParameterNames();
		while(parameters.hasMoreElements()) {
			String paramName = parameters.nextElement();
			if(!paramName.startsWith("q")) continue;
			String[] qAndCStrings = paramName.replace("q", "").split("c");
			int questionNumber = Integer.parseInt(qAndCStrings[0]);
			int choiceNumber = Integer.parseInt(qAndCStrings[1]);
			survey.getQuestions()[questionNumber].getChoices()[choiceNumber].setSelected(true);
		}
		survey.grade();
	}

}
