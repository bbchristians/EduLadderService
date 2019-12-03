package model.flushing;

import responses.Question;

import java.util.List;

public class FlushingStateActionPair {

    private List<Question> state;
    private Question action;

    public FlushingStateActionPair(List<Question> state, Question action) {
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
