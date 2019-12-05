package model.graph;

import responses.Question;

public class QuestionRelation {

    private String q1;
    private String q2;
    private float relatednessScore;

    public QuestionRelation(String q1, String q2, float relatednessScore) {
        this.q1 = q1;
        this.q2 = q2;
        this.relatednessScore = relatednessScore;
    }

    public String getQ1() {
        return q1;
    }

    public String getQ2() {
        return q2;
    }

    public float getRelatednessScore() {
        return relatednessScore;
    }

}
