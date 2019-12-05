package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import model.graph.QuestionRelation;
import org.postgresql.ds.PGSimpleDataSource;

import responses.Question;

public class DBConnection {

	static Connection getConnection() throws SQLException {
		return getPostgresDataSource().getConnection();
	}

	static DataSource getPostgresDataSource() {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();

		// Set dataSource Properties
		dataSource.setServerName("ec2-174-129-253-63.compute-1.amazonaws.com");
		dataSource.setPortNumber(5432);
		dataSource.setDatabaseName("d334oe73gmbr7c");
		dataSource.setUser("vkppgdtlutnedj");
		dataSource.setPassword("1964c833cfcf97395c4f31d3b0663a164f25dce503bf532b81acfe6877f9dfce");
		dataSource.setSslMode("require");

		return dataSource;
	}

	private static void executeStatement(String fileName, Connection conn) {
		StringBuilder query = new StringBuilder();
		PreparedStatement stmt;
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while((line = br.readLine()) != null) {
				query.append(line);
			}
			
			stmt = conn.prepareStatement(query.toString());
			stmt.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	static List<Question> getRankableQuestions() throws SQLException{
		List<Question> questionList = getAllQuestions();
		Collections.shuffle(questionList);
		return questionList;
	}
	
	static List<Question> getAllQuestions() throws SQLException{
		String query = "SELECT q.question_id, g.grade_level, q.text, qa.answer_text"
				+ " FROM questions q"
				+ " INNER JOIN question_answers qa"
				+ " ON qa.question_id = q.question_id"
				+ " INNER JOIN grades g"
				+ " ON g.grade_id = CAST(q.grade_id AS int4)";
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		ArrayList<Question> questionList = new ArrayList<>();
		while(rs.next()) {
			questionList.add(
					new Question(
							rs.getString(1),
							rs.getInt(2),
							rs.getString(3),
							rs.getString(4),
							""));
					// TODO this needs to include grade level
		}

		return questionList;
	}
	
	static List<QuestionRelation> getAllRelations() throws SQLException {
		// TODO
		String query = "SELECT q1.question_id, rd.question_id2, rd.relationship_score" + 
				" FROM questions q1" + 
				" INNER JOIN related_diffs rd" + 
				" ON rd.question_id1 = q1.question_id";
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		List<QuestionRelation> relations = new ArrayList<>();
		while(rs.next()) {
			relations.add(
					new QuestionRelation(
							rs.getString(1),
							rs.getString(2),
							rs.getFloat(3)));
		}
		
		return relations;
	}


	public static void main(String[] args) {
		Connection conn;
		
		try {
			conn = getConnection();
			executeStatement("postgres.txt", conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Old un-used queries


//	public static boolean answerQuestion(String sessionId, String questionId, String answer) throws SQLException {
//		String query1 = "SELECT answer_text FROM question_answers WHERE question_id = ?;";
//
//		Connection conn = getConnection();
//		PreparedStatement stmt = conn.prepareStatement(query1);
//		stmt.setInt(1, Integer.parseInt(questionId));
//		ResultSet rs = stmt.executeQuery();
//
//		while(rs.next()) {
//			String[] answers = Question.parseAnswers(rs.getString(1));
//			String query2 = "";
//
//			for(int i = 0; i < answers.length; i++) {
//				// If one of the retrieved answers match the given answer, record that
//				// the user got the question right
//				if(answers[i].equals(answer)) {
//					query2 = "INSERT INTO session_questions (session_id, question_id, is_correct, answer_text)" +
//							" VALUES (?, ?, ?, ?)";
//					stmt = conn.prepareStatement(query2);
//					stmt.setString(1, sessionId);
//					stmt.setInt(2, Integer.parseInt(questionId));
//					stmt.setBoolean(3, true);
//					stmt.setString(4, answer);
//					stmt.executeUpdate();
//
//					return true;
//				}
//			}
//
//			query2 = "INSERT INTO session_questions (session_id, question_id, is_correct, answer_text)" +
//					" VALUES (?, ?, ?, ?)";
//			stmt = conn.prepareStatement(query2);
//			stmt.setString(1, sessionId);
//			stmt.setInt(2, Integer.parseInt(questionId));
//			stmt.setBoolean(3, false);
//			stmt.setString(4, answer);
//			stmt.executeUpdate();
//			stmt.close();
//
//			return false;
//		}
//
//		return false;
//	}
//
//	public static List<Integer> getAnsweredQuestions(String sessionId) throws SQLException {
//		String query = "SELECT q.question_id" +
//				"FROM questions q" +
//				" INNER JOIN session_questions sq" +
//				" ON sq.question_id = q.question_id" +
//				" AND sq.session_id = ?;";
//
//		Connection conn = getConnection();
//		PreparedStatement stmt = conn.prepareStatement(query);
//		stmt.setString(1, sessionId);
//
//		ResultSet rs = stmt.executeQuery();
//
//		List<Integer> questionIds = new ArrayList<>();
//
//		while(rs.next()) {
//			int nextInt = rs.getInt(1);
//			questionIds.add(nextInt);
//		}
//
//		return questionIds;
//	}
}
