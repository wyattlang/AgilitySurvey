package com.agility.survey.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.agility.survey.pojo.Survey;
import com.agility.survey.pojo.User;
import com.agility.survey.service.MailService;
import com.agility.survey.service.PdfService;
import com.agility.survey.service.StoreUserInfoService;
import com.agility.survey.service.SurveyService;

@Controller
public class SurveyController {

	private StoreUserInfoService userService;
	private SurveyService surveyService;
	private PdfService pdfService;
	private MailService mailService;
	
	@Autowired
	public SurveyController(StoreUserInfoService userService, SurveyService surveyService, 
			                PdfService pdfService, MailService mailService) {
		this.userService = userService;
		this.surveyService = surveyService;
		this.pdfService = pdfService;
		this.mailService = mailService;
	}
	
	@RequestMapping("/survey")
	public ModelAndView takeSurvey(HttpServletRequest request,
			                       @RequestParam("firstName") String firstName, 
			                       @RequestParam("lastName") String lastName, 
			                       @RequestParam("emailField") String email) {
		User user = new User(firstName, lastName, email);
		HttpSession session = request.getSession();
		session.setAttribute("survey", surveyService.getCurrentlyEnabledSurvey());
		session.setAttribute("user", user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("survey-form");
		return mav;
	}
	
	@RequestMapping("/survey/completed")
	public ModelAndView finishSurvey(HttpServletRequest request,
									 @SessionAttribute("survey") Survey survey, 
			                         @SessionAttribute("user") User user,
			                         @RequestParam("comment") String comment) {
		surveyService.gradeSurvey(request, survey);
		userService.store(user, survey.getSurveyId(), comment);
		userService.getDiscountCode(user, survey.getSurveyId());
		pdfService.create(user, survey);
		mailService.sendMail(user.getDiscountCode());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("survey-end");	
		return mav;
	}
	
}
