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
	
	public Question getQuestion(int sessionId, int gradeLevel) {
		return null;
	}

	public List<Question> getRankableQuestions() {
		return null;
	}
	
	public void answerQuestion(int sessionId, String questionId, String answer) {

	}

	public void classifyQuestions(int questionId1, int questionId2, int relationScore, int complexity) {

	}
}
