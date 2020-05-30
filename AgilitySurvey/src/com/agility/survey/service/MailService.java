package com.agility.survey.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private MimeMessage mail;
	@Autowired
	private MimeMessageHelper helper;
	
	//TODO add Async
	public void sendMail(String pdfName) {
		try {
			FileSystemResource file = new FileSystemResource(new File("C:/Users/Wyatt/eclipse-ee-workspace/AgilitySurvey/pdf/" + pdfName + ".pdf"));
			helper.addAttachment("Results.pdf", file);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		emailSender.send(mail);
	}
	
}
