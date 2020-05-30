package com.agility.survey.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agility.survey.service.SurveyNamesService;

@Controller
public class AdminController {

	private SurveyNamesService service;
	
	@Autowired
	public AdminController(SurveyNamesService service) {
		this.service = service;
	}

	@RequestMapping("/admin")
	public ModelAndView redirect(HttpServletRequest request) {
		String[] surveyNames = service.getAllSurveyNames();
		request.setAttribute("surveyNames", surveyNames);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin");
		return mav;
	}

}
