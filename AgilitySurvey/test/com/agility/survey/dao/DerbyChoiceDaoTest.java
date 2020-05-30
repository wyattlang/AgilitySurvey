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
import com.agility.survey.pojo.Choice;

class DerbyChoiceDaoTest {

	private ChoiceDao dao;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try {
			ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_choices_table.sql")));
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
			stmt.executeUpdate("DROP TABLE CHOICES");
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
		this.dao = context.getBean(ChoiceDao.class);
		context.close();
	}

	@Test
	@Transactional
	@Rollback(true)
	void testCreateChoice() {
		try {
			dao.create(99999, "I'm agile!", 10);
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectChoicesPositive() {
		dao.create(99999, "I swear I'm agile!", 10);
		dao.create(99999, "I'm a little agile", 1);
		dao.create(99999, "What's agile?", 0);
		dao.create(99999, "I dabble in agile", 5);
		List<Choice> actual = dao.select(99999);
		List<Choice> expected = new ArrayList<Choice>();
		expected.add(new Choice("I swear I'm agile!", 10));
		expected.add(new Choice("I'm a little agile", 1));
		expected.add(new Choice("What's agile?", 0));
		expected.add(new Choice("I dabble in agile", 5));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectChoicesNegative() {
		dao.create(99999, "I swear I'm agile!", 10);
		dao.create(99999, "I'm a little agile", 1);
		dao.create(99999, "What's agile?", 0);
		dao.create(99999, "I dabble in agile", 5);
		List<Choice> actual = dao.select(99999);
		List<Choice> expected = new ArrayList<Choice>();
		expected.add(new Choice("I swear I'm agile!", 2));
		expected.add(new Choice("I'm a little agile", 10));
		expected.add(new Choice("What's agile?", 7));
		expected.add(new Choice("I dabble in agile", 4));
		assertFalse(expected.equals(actual));
	}

}
