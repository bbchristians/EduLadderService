package model;

import responses.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateActionPair {

    private List<Question> state; // Incorrectly answered Qs
    private Question action;

    public StateActionPair(List<Question> state, Question action) {
        this.state = state;
        this.action = action;
    }

    public List<Question> getState() {
        return state;
    }

    public Question getAction() {
        return action;
    }

    public static StateActionPair fromString(List<Question> questions, String str) {
        String[] split = str.split("|");
        // Get correct questions
        return new StateActionPair(
                getQuestionsFromString(questions, split[0]),
                getQuestionsFromString(questions, split[1]).get(0)
        );
    }

    private static List<Question> getQuestionsFromString(List<Question> questions, String splitString) {
        List<Question> foundQuestions = new ArrayList<>();
        List<String> requestedQuestions = Arrays.asList(splitString.split(";"));
        for( Question checkedQuestion : questions ) {
            if( requestedQuestions.contains(checkedQuestion.getQuestionId() ) ) {
                foundQuestions.add(checkedQuestion);
            }
        }
        return foundQuestions;
    }
}
