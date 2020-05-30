package com.agility.survey.pojo;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 3177120776598569907L;

	private String discountCode;
	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
