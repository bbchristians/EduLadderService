import responses.Question;

import java.sql.Connection;
import java.sql.SQLException;

public class EduLadderController {

	private Connection conn = null;

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
		return new Question[]{
				new Question("123123", "5 + 5 = ?", new String[]{"10", "ten"}, ""),
				new Question("123123", "5 + 5 = ?", new String[]{"10", "ten"}, "")
		};
	}
	
	public void answerQuestion(String sessionId, String questionId, String answer) {

	}

	public void classifyQuestions(int questionId1, int questionId2, int relationScore, int complexity) {

	}
}
