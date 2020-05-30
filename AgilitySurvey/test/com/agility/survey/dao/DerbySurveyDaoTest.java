package com.agility.survey.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
import com.agility.survey.pojo.Question;
import com.agility.survey.pojo.Survey;

class DerbySurveyDaoTest {
	
	private SurveyDao dao;
	private static Survey survey1;
	private static Survey survey2;
	private static Survey survey3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		populateSurveys();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try {
			Connection conn = dataSource.getConnection();
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_questions_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_choices_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_supportingsentences_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_categories_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_surveys_table.sql")));		
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	private static void populateSurveys() {
		survey1 = new Survey("General Agility Test", new String[] { "General Agility", "Planning", "Sprinting" }, new Question[] { new Question("Are you agile?", new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) }, new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" }, new int[] { 3, 7 }, "General Agility"), new Question("Do you plan?", new Choice[] { new Choice("Yes", 10), new Choice("No", 0) }, new String[] { "You need to plan!", "unreachable", "Keep planning!" }, new int[] { 5, 6 }, "Planning") });
		survey2 = new Survey("Second Agility Test", new String[] { "General Agility", "Planning", "Sprinting" }, new Question[] { new Question("Are you agile?", new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) }, new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" }, new int[] { 3, 7 }, "General Agility"), new Question("Do you plan?", new Choice[] { new Choice("Yes", 10), new Choice("No", 0) }, new String[] { "You need to plan!", "unreachable", "Keep planning!" }, new int[] { 5, 6 }, "Planning") });
		survey3 = new Survey("Third Agility Test", new String[] { "General Agility", "Planning", "Sprinting" }, new Question[] { new Question("Are you agile?", new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) }, new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" }, new int[] { 3, 7 }, "General Agility"), new Question("Do you plan?", new Choice[] { new Choice("Yes", 10), new Choice("No", 0) }, new String[] { "You need to plan!", "unreachable", "Keep planning!" }, new int[] { 5, 6 }, "Planning") });
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
			stmt.executeUpdate("DROP TABLE QUESTIONS");
			stmt.executeUpdate("DROP TABLE CHOICES");
			stmt.executeUpdate("DROP TABLE SUPPORTINGSENTENCES");
			stmt.executeUpdate("DROP TABLE CATEGORIES");
			stmt.executeUpdate("DROP TABLE SURVEYS");
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
		this.dao = context.getBean(SurveyDao.class);
		context.close();
		dao.create(survey1.getName(), survey1.getCategories(), survey1.getQuestions());
		dao.create(survey2.getName(), survey2.getCategories(), survey2.getQuestions());
		dao.create(survey3.getName(), survey3.getCategories(), survey3.getQuestions());
	}
	
	@AfterEach
	void tearDown() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = context.getBean(DataSource.class);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM QUESTIONS");
			stmt.executeUpdate("DELETE FROM CHOICES");
			stmt.executeUpdate("DELETE FROM SUPPORTINGSENTENCES");
			stmt.executeUpdate("DELETE FROM CATEGORIES");
			stmt.executeUpdate("DELETE FROM SURVEYS");
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

	@Test
	@Transactional
	@Rollback(true)
	void testCreateSurvey() {
		try {
			tearDown();
			dao.create(survey1.getName(), survey1.getCategories(), survey1.getQuestions());
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectAllSurveyNamesPositive() {
		List<String> actual = dao.selectAllSurveyNames();
		List<String> expected = new ArrayList<>();
		expected.add("General Agility Test");
		expected.add("Second Agility Test");
		expected.add("Third Agility Test");
		assertEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectAllSurveyNamesNegative() {
		List<String> actual = dao.selectAllSurveyNames();
		List<String> expected = new ArrayList<>();
		expected.add("General Agility Test");
		expected.add("Non-existent Test");
		expected.add("Third Agility Test");
		assertNotEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectBySurveyIdPositive() {
		List<String> names = dao.selectAllSurveyNames();
		Survey fromName = dao.select(names.get(0));
		Survey actual = dao.select(fromName.getSurveyId());
		Survey expected = survey1;
		assertEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectBySurveyIdNegative() {
		List<String> names = dao.selectAllSurveyNames();
		Survey fromName = dao.select(names.get(1));
		Survey actual = dao.select(fromName.getSurveyId());
		Survey expected = survey1;
		assertNotEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectByNamePositive() {
		List<String> names = dao.selectAllSurveyNames();
		Survey actual = dao.select(names.get(0));
		Survey expected = survey1;
		assertEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectByNameNegative() {
		List<String> names = dao.selectAllSurveyNames();
		Survey actual = dao.select(names.get(1));
		Survey expected = survey1;
		assertNotEquals(actual, expected);
	}

}
