package com.agility.survey.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.agility.survey.pojo.User;

@Component
public class DerbyUserDao implements UserDao {

	private JdbcTemplate jdbc;
	private UserIdResultSetExtractor idExtractor;
	private UserDiscountCodeResultSetExtractor discountCodeExtractor;
	
	@Autowired
	public DerbyUserDao(JdbcTemplate jdbc, UserIdResultSetExtractor idExtractor, UserDiscountCodeResultSetExtractor discountCodeExtractor) {
		this.jdbc = jdbc;
		this.idExtractor = idExtractor;
		this.discountCodeExtractor = discountCodeExtractor;
	}

	@Override
	public void create(long surveyId, String firstName, String lastName, String email, String comment) {
		jdbc.update("INSERT INTO USERS VALUES (DEFAULT, ?, ?, ?, ?, 'TEMPCODE', ?)", 
				    new Object[] { surveyId, firstName, lastName, email, comment });
		long userId = jdbc.query("SELECT USERID FROM USERS WHERE SURVEYID = ? AND FIRSTNAME = ? AND LASTNAME = ? AND EMAIL = ?", 
				                 idExtractor, new Object[] { surveyId, firstName, lastName, email });
		String discountCode = generateDiscountCode(userId, surveyId, firstName, lastName);
		jdbc.update("UPDATE USERS SET DISCOUNTCODE = ? WHERE USERID = ?",
				    new Object[] { discountCode, userId });
	}

	private String generateDiscountCode(long userId, long surveyId, String firstName, String lastName) {
		return firstName + lastName + surveyId + "-" + userId;
	}

	@Override
	public long selectUserId(User user, long surveyId) {
		return jdbc.query("SELECT USERID FROM USERS WHERE SURVEYID = ? AND FIRSTNAME = ? AND LASTNAME = ? AND EMAIL = ?", idExtractor, new Object[] { surveyId, user.getFirstName(), user.getLastName(), user.getEmail() });
	}

	@Override
	public String selectUserDiscountCode(long userId) {
		return jdbc.query("SELECT DISCOUNTCODE FROM USERS WHERE USERID = ?", discountCodeExtractor, new Object[] { userId });
	}

}
