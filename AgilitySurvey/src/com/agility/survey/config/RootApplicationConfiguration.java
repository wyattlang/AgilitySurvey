package com.agility.survey.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan({ "com.agility.survey.dao" })
public class RootApplicationConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		dataSource.setUrl("jdbc:derby://localhost:1527/surveydb");
		dataSource.setUsername("survey");
		dataSource.setPassword("nm3k98pP5mMM589");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
		
}
