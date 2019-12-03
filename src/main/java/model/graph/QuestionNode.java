package model.graph;

import responses.Question;

import java.util.List;

public class QuestionNode {

    private List<QuestionNode> relatedNodes;
    private int gradeLevel;
    private Question question;

    public QuestionNode(List<QuestionNode> relatedNodes, int gradeLevel, Question question) {
        this.relatedNodes = relatedNodes;
        this.gradeLevel = gradeLevel;
        this.question = question;
    }

    public List<QuestionNode> getRelatedNodes() {
        return relatedNodes;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public Question getQuestion() {
        return question;
    }
}
