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
import com.agility.survey.pojo.Question;

class DerbyQuestionDaoTest {

	private QuestionDao dao;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try {
			Connection conn = dataSource.getConnection();
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_questions_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_choices_table.sql")));
			ScriptUtils.executeSqlScript(conn, new EncodedResource(new FileSystemResource("lib/test-sql-scripts/create_supportingsentences_table.sql")));
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
			stmt.executeUpdate("DROP TABLE QUESTIONS");
			stmt.executeUpdate("DROP TABLE CHOICES");
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
	void setUp() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
		this.dao = context.getBean(QuestionDao.class);
		context.close();
	}

	@Test
	@Transactional
	@Rollback(true)
	void testCreateQuestion() {
		try {
			Choice[] choices = new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) };
			String[] supportingSentences = new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" };
			int[] gradeThresholds = new int[] { 3, 7 };
			dao.create(1, "Are you agile?", choices, supportingSentences, gradeThresholds, "General Agility");
			assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	void testSelectQuestionsPositive() {
		Choice[] choices1 = new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) };
		String[] supportingSentences1 = new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" };
		int[] gradeThresholds1 = new int[] { 3, 7 };
		dao.create(99999, "Are you agile?", choices1, supportingSentences1, gradeThresholds1, "General Agility");
		Choice[] choices2 = new Choice[] { new Choice("Yes", 10), new Choice("No", 0) };
		String[] supportingSentences2 = new String[] { "You need to plan!", "unreachable", "Keep planning!" };
		int[] gradeThresholds2 = new int[] { 5, 6 };
		dao.create(99999, "Do you plan?", choices2, supportingSentences2, gradeThresholds2, "Planning");
		List<Question> actual = dao.select(99999);
		List<Question> expected = new ArrayList<>();
		expected.add(new Question(99999, "Are you agile?", choices1, supportingSentences1, gradeThresholds1, "General Agility"));
		expected.add(new Question(99999, "Do you plan?", choices2, supportingSentences2, gradeThresholds2, "Planning"));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testSelectQuestionsNegative() {
		Choice[] choices1 = new Choice[] { new Choice("I swear I'm agile!", 10), new Choice("I'm a little agile", 1), new Choice("What's agile?", 0), new Choice("I dabble in agile", 5) };
		String[] supportingSentences1 = new String[] { "You should improve upon that.", "You're doing ok. Do better.", "You're doing great!" };
		int[] gradeThresholds1 = new int[] { 3, 7 };
		dao.create(99999, "Are you agile (?", choices1, supportingSentences1, gradeThresholds1, "General Agility");
		Choice[] choices2 = new Choice[] { new Choice("Yes", 10), new Choice("No", 0) };
		String[] supportingSentences2 = new String[] { "You need to plan!", "unreachable", "Keep planning!" };
		int[] gradeThresholds2 = new int[] { 5, 6 };
		dao.create(99999, "Do you plan?", choices2, supportingSentences2, gradeThresholds2, "Planning");
		List<Question> actual = dao.select(99999);
		List<Question> expected = new ArrayList<>();
		expected.add(new Question(99999, "Are you agile?", choices1, supportingSentences1, gradeThresholds1, "General Agility"));
		expected.add(new Question(99999, "Do you plan?", choices2, supportingSentences2, gradeThresholds2, "Planning"));
		assertFalse(expected.equals(actual));
	}
}
