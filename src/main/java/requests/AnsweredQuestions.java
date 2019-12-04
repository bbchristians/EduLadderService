package requests;

import responses.Question;

import java.util.List;

public class AnsweredQuestions {

    private List<Question> correctQuestions;
    private List<Question> incorrectQuestions;

    public AnsweredQuestions(List<Question> correctQuestions, List<Question> incorrectQuestions) {
        this.correctQuestions = correctQuestions;
        this.incorrectQuestions = incorrectQuestions;
    }

    public List<Question> getCorrectQuestions() {
        return correctQuestions;
    }

    public List<Question> getIncorrectQuestions() {
        return incorrectQuestions;
    }
}
