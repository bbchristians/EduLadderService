package model.graph;

import responses.Question;

public class QuestionRelation {

    private Question q1;
    private Question q2;
    private float relatednessScore;

    public QuestionRelation(Question q1, Question q2, float relatednessScore) {
        this.q1 = q1;
        this.q2 = q2;
        this.relatednessScore = relatednessScore;
    }

    public Question getQ1() {
        return q1;
    }

    public Question getQ2() {
        return q2;
    }

    public float getRelatednessScore() {
        return relatednessScore;
    }

}
