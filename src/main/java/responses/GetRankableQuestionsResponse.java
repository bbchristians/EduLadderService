package responses;

public class GetRankableQuestionsResponse {

    public Question question1;
    public Question question2;

    public GetRankableQuestionsResponse(Question question1, Question question2) {
        this.question1 = question1;
        this.question2 = question2;
    }

}
