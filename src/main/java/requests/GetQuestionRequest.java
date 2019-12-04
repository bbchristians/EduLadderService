package requests;

public class GetQuestionRequest {

    private String sessionId;
    private int gradeLevel;
    private AnsweredQuestions answeredQuestions;

    GetQuestionRequest(String sessionId, int gradeLevel, AnsweredQuestions answeredQuestions) {
        this.sessionId = sessionId;
        this.gradeLevel = gradeLevel;
        this.answeredQuestions = answeredQuestions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public AnsweredQuestions getAnsweredQuestions() {
        return answeredQuestions;
    }
}
