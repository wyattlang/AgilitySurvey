package com.agility.survey.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.agility.survey.config.ApplicationConfigurationTest;

class DerbyCategoryDaoTest {

	private CategoryDao dao;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try {
			ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_categories_table.sql")));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = context.getBean(DataSource.class);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE CATEGORIES");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			context.close();
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		this.dao = context.getBean(CategoryDao.class);
		context.close();
	}

	@Test
	@Transactional
	@Rollback(true)
	void testCreateCategory() {
		try {
			dao.create(1, "General Agility");
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	void testSelectCategoryPositive() {
		List<String> expected = new ArrayList<>();
		expected.add("General Agility");
		expected.add("Planning");
		expected.add("Sprinting");
		dao.create(99999, "General Agility");
		dao.create(99999, "Planning");
		dao.create(99999, "Sprinting");
		List<String> actual = dao.select(99999);
		assertTrue(actual.equals(expected));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectCategoryNegative() {
		List<String> expected = new ArrayList<>();
		expected.add("Extreme Agility");
		expected.add("Not Planning");
		expected.add("Jogging");
		dao.create(99999, "General Agility");
		dao.create(99999, "Planning");
		dao.create(99999, "Sprinting");
		List<String> actual = dao.select(99999);
		assertFalse(actual.equals(expected));
	}
}
