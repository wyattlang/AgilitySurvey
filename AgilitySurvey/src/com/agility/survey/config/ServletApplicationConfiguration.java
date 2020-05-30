package com.agility.survey.config;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({ "com.agility.survey.controller", "com.agility.survey.service" })
public class ServletApplicationConfiguration {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername("wyattlang2001@gmail.com");
	    mailSender.setPassword("");
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "false");
	    return mailSender;
	}
	
	@Bean
	public MimeMessage mimeMessage() {
		return javaMailSender().createMimeMessage();
	}
	
	@Bean
	public MimeMessageHelper mimeMessageHelper() {
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(mimeMessage(), true);
			helper.setSubject("Survey Results");
			helper.setTo("wyattlang2001@gmail.com");
			helper.setText("Thanks for taking our survey!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return helper;
	}
	//TODO add AsyncConfiguration
}
