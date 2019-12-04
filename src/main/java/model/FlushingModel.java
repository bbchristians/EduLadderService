package model;

import responses.Question;

import java.util.*;

public class FlushingModel extends QLearningModel {

    public FlushingModel() {
        super();
    }

    @Override
    public void loadQLearningTable() {
        this.qLearningTable = new HashMap<>();
    }

    @Override
    public float getLearningRate() {
        return 0.5f;
    }

    @Override
    public float getDiscountRate() {
        return 0.4f;
    }

    @Override
    public float getExploreChance() {
        return 0.05f;
    }

    public Optional<Question> getNextQuestion(String sessionId, int gradeLevel, List<Question> answeredQuestions) {
        // Get all possible questions from graph
        Set<Question> validQuestions =
                super.getQuestionFromRelatednessGraph(gradeLevel, answeredQuestions, new ArrayList<>());

        return this.getBestQuestionToAsk(sessionId, validQuestions, answeredQuestions);
    }
}
