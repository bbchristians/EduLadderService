import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import model.flushing.FlushingModel;
import model.hunting.HuntingModel;
import requests.AnsweredQuestions;
import responses.NoMoreQuestions;
import responses.Question;

public class EduLadderController {

	private DBConnection db = null;
	private Connection conn = null;


	private HuntingModel huntingModel;
	private FlushingModel flushingModel;


	public EduLadderController() throws SQLException {
		this.huntingModel = new HuntingModel();
		this.flushingModel = new FlushingModel();
		try {
			this.conn = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Question getQuestion(
			String sessionId, int gradeLevel, List<Question> correctQuestions, List<Question> incorrectQuestions) {
		if( incorrectQuestions.size() > 0 ) {
			return this.huntingModel
					.getBestAppropriateQuestion(sessionId, gradeLevel, correctQuestions, incorrectQuestions)
					.orElseGet(() -> new NoMoreQuestions(2));
		} else {
			return  this.flushingModel
					.getNextQuestion(sessionId, gradeLevel, correctQuestions)
					.orElseGet(() -> new NoMoreQuestions(1));
		}
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
