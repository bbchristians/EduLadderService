package model.hunting;

import responses.Question;

import java.util.List;

public class HuntingStateActionPair {

    private List<Question> state; // Incorrectly answered Qs
    private Question action;

    public HuntingStateActionPair(List<Question> state, Question action) {
        this.state = state;
        this.action = action;
    }

    public List<Question> getState() {
        return state;
    }

    public Question getAction() {
        return action;
    }
}
