package com.agility.survey.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

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

class DerbySupportingSentencesDaoTest {

	private SupportingSentencesDao dao;
		
	@BeforeAll
	static void SetUpBeforeClass() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = context.getBean(DataSource.class);
		try {
			ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_supportingsentences_table.sql")));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}
	
	@AfterAll
	static void tearDownAfterClass() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = context.getBean(DataSource.class);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE SUPPORTINGSENTENCES");
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
	void setUp() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		this.dao = context.getBean(SupportingSentencesDao.class);
		context.close();
	}

	@Test
	@Transactional
	@Rollback(true)
	void testCreateSupportingSentences() {
		try {
			dao.create(99999, "This is a test (LOW)", "This is a test (MEDIUM)", "This is a test (HIGH)");
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectSupportingSentencesPositive() {
		dao.create(99999, "This is a test (LOW)", "This is a test (MEDIUM)", "This is a test (HIGH)");
		String[] actual = dao.select(99999);
		String[] expected = new String[] { "This is a test (LOW)", "This is a test (MEDIUM)", "This is a test (HIGH)" };
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectSupportingSentencesNegative() {
		dao.create(99999, "This is a test (LOW)", "This is a test (MEDIUM)", "This is a test (HIGH)");
		String[] actual = dao.select(99999);
		String[] expected = new String[] { "This is a test (HIGH)", "This is a test (LOW)", "This is a test (MEDIUM)" };
		assertFalse(Arrays.equals(expected, actual));
	}

}
