package requests;

public class GetQuestionRequest {

    public String sessionId;
    public int gradeLevel;

    GetQuestionRequest(String sessionId, int gradeLevel) {
        this.sessionId = sessionId;
        this.gradeLevel = gradeLevel;
    }
}
