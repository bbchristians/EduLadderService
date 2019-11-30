
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import responses.Question;

public class DBConnection {

	public static Connection getConnection() throws SQLException {
		Connection conn = getPostgressDataSource().getConnection();
		return conn;
	}

	public static DataSource getPostgressDataSource() {
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

	public static void executeStatement(String fileName, Connection conn) {
		String query = "";
		PreparedStatement stmt = null;
		String line = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while((line = br.readLine()) != null) {
				query += line;
			}
			
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean answerQuestion(String sessionId, String questionId, String answer) throws SQLException {
		String query1 = "SELECT answer_text FROM question_answers WHERE question_id = ?;";
		
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query1);
		stmt.setInt(1, Integer.parseInt(questionId));
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			String[] answers = Question.parseAnswers(rs.getString(1));
			String query2 = "";
			
			for(int i = 0; i < answers.length; i++) {
				// If one of the retrieved answers match the given answer, record that 
				// the user got the question right
				if(answers[i].equals(answer)) {
					query2 = "INSERT INTO session_questions (session_id, question_id, is_correct, answer_text)" + 
							" VALUES (?, ?, ?, ?)";
					stmt = conn.prepareStatement(query2);
					stmt.setString(1, sessionId);
					stmt.setInt(2, Integer.parseInt(questionId));
					stmt.setBoolean(3, true);
					stmt.setString(4, answer);
					stmt.executeUpdate();
					
					return true;
				}
			}
			
			query2 = "INSERT INTO session_questions (session_id, question_id, is_correct, answer_text)" + 
					" VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(query2);
			stmt.setString(1, sessionId);
			stmt.setInt(2, Integer.parseInt(questionId));
			stmt.setBoolean(3, false);
			stmt.setString(4, answer);
			stmt.executeUpdate();
			stmt.close();
			
			return false;
		}	
		
		return false;
	}
	
	public static List<Integer> getAnsweredQuestions(String sessionId) throws SQLException {
		String query = "SELECT q.question_id" + 
				"FROM questions q" + 
				" INNER JOIN session_questions sq" + 
				" ON sq.question_id = q.question_id" + 
				" AND sq.session_id = ?;";
		
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, sessionId);
		
		ResultSet rs = stmt.executeQuery();
		
		List<Integer> questionIds = new ArrayList<Integer>();
		int nextInt = 0;
		
		while(rs.next()) {
			nextInt = rs.getInt(1);
			questionIds.add(nextInt);
			System.out.println(nextInt);
		}
		
		return questionIds;
	}
	
	public static List<Question> getRankableQuestions() throws SQLException{
		String query = "SELECT q.question_id, q.text, qa.answer_text" 
				+ " FROM questions q" 
				+ " INNER JOIN question_answers qa"
				+ " ON qa.question_id = q.question_id";
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<Question> questionList = new ArrayList<Question>();
		Question question = null;
		while(rs.next()) {
			question = new Question(rs.getString(1), rs.getString(2), rs.getString(3), "");
			System.out.println(question);
			questionList.add(question);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			conn = getConnection();
			executeStatement("postgres.txt", conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
