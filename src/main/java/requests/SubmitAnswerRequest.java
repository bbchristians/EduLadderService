package requests;

public class SubmitAnswerRequest {

    public String sessionId;
    public String questionId;
    public String answer;

    public SubmitAnswerRequest(String sessionId, String questionId, String answer) {
        this.sessionId = sessionId;
        this.questionId = questionId;
        this.answer = answer;
    }

}
