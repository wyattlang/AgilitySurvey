package com.agility.survey.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootApplicationConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletApplicationConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
