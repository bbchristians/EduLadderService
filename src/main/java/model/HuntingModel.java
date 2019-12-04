package model;

import responses.Question;

import java.util.*;

public class HuntingModel extends QLearningModel {

    public HuntingModel() {
        super();
    }

    @Override
    public void loadQLearningTable() {
        this.qLearningTable = new HashMap<>(); // TODO load from persistence
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

    public Optional<Question> getNextQuestion(
            String sessionId, int gradeLevel, List<Question> answeredQuestions, List<Question> incorrectQuestions) {
        // Get all possible questions from graph
        Set<Question> validQuestions =
                super.getQuestionFromRelatednessGraph(gradeLevel, answeredQuestions, incorrectQuestions);

        return this.getBestQuestionToAsk(sessionId, validQuestions, incorrectQuestions);
    }

}
