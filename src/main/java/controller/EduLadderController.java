package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import model.FlushingModel;
import model.HuntingModel;
import responses.NoMoreQuestions;
import responses.Question;

public class EduLadderController {

	private Connection conn = null;

	private HuntingModel huntingModel;
	private FlushingModel flushingModel;

	private Set<String> sessionsFlushing;


	public EduLadderController() {
		this.huntingModel = new HuntingModel();
		this.flushingModel = new FlushingModel();
		this.sessionsFlushing = new HashSet<>();
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
			if( this.sessionsFlushing.remove(sessionId) ) {
				this.flushingModel.addReward(sessionId, correctQuestions.size() + incorrectQuestions.size());
			}
			return this.huntingModel
					.getNextQuestion(sessionId, gradeLevel, correctQuestions, incorrectQuestions)
					.orElseGet(() -> {
						this.huntingModel.addReward(sessionId, correctQuestions.size() + incorrectQuestions.size());
						return new NoMoreQuestions(2);
					});
		} else {
			this.sessionsFlushing.add(sessionId);
			return  this.flushingModel
					.getNextQuestion(sessionId, gradeLevel, correctQuestions)
					.orElseGet(() -> new NoMoreQuestions(1));
		}
//		return new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, "");
	}

	public Question[] getRankableQuestions() {

		// TODO 2 random questions from DB (This functionality won't be implemented...
		// Alex is going to manually classify each question for the sake of the class
		return new Question[] { new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, ""),
				new Question("123123", "5 + 5 = ?", new String[] { "10", "ten" }, "") };
	}
}
