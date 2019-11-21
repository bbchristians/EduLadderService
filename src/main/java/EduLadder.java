import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EduLadder {

	Connection conn = null;

	public EduLadder() throws SQLException {
		try {
			conn = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Question getQuestion(String sessionId, int gradeLevel) {
		return new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, "");
	}

	public List<Question> getRankableQuestions() {
		return null;
	}
	
	public void answerQuestion(int sessionId, String questionId, String answer) {

	}

	public void classifyQuestions(int questionId1, int questionId2, int relationScore, int complexity) {

	}
}
