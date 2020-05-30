package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class UserDiscountCodeResultSetExtractor implements ResultSetExtractor<String> {

	@Override
	public String extractData(ResultSet rs) throws SQLException, DataAccessException {
		if(rs.next() != true) throw new SQLException("Empty ResultSet in UserDiscountCodeResultSetExtractor");
		return rs.getString("DISCOUNTCODE");
	}

}
