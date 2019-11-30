import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import responses.Question;

public class EduLadderController {

	private DBConnection db = null;
	Connection conn = null;
	private HashMap<Integer,HashMap<Integer, Double>> complexityGraph = new HashMap<>();

	public EduLadderController() throws SQLException {
		try {
			this.conn = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Question getQuestion(String sessionId, int gradeLevel) {
		return new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, "");
	}

	public Question[] getRankableQuestions() {
		return new Question[] { new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, ""),
				new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, "") };
	}

	public Question getQuestion(String sessionId) {

		try {
			List<Integer> answeredQuestionIds = db.getAnsweredQuestions(sessionId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean answerQuestion(String sessionId, String questionId, String answer) {
		try {
			return db.answerQuestion(sessionId, questionId, answer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void classifyQuestions(int questionId1, int questionId2, int relationScore, int complexity) {
	
	}
}
