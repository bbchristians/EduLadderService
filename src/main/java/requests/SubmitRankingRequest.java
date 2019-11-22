package requests;

public class SubmitRankingRequest {

    public boolean isRelated;
    public double complexityScore;
    public String leftCardId;
    public String rightCardId;

    public SubmitRankingRequest(boolean isRelated, double complexityScore, String leftCardId, String rightCardId) {
        this.isRelated = isRelated;
        this.complexityScore = complexityScore;
        this.leftCardId = leftCardId;
        this.rightCardId = rightCardId;
    }

}
