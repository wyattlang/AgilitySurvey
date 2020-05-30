package com.agility.survey.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CategoryResultSetExtractor implements ResultSetExtractor<String> {

	@Override
	public String extractData(ResultSet rs) throws SQLException, DataAccessException {
		return rs.getString("NAME");
	}

}
