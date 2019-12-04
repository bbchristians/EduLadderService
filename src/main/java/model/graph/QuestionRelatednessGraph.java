package model.graph;

import responses.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionRelatednessGraph {

    private static QuestionRelatednessGraph thisInstance = null;

    private Map<Integer, List<QuestionNode>> nodesByGradeLevel;
    private Map<String, QuestionNode> nodesByQuestionId;

    private QuestionRelatednessGraph() {
        // TODO what question Data do we have?
        this.nodesByGradeLevel = new HashMap<>();
        this.nodesByQuestionId = new HashMap<>();
    }

    private List<Question> getAllAppropriateQuestions(int gradeLevel) {
        ArrayList<Question> questions = new ArrayList<>();
        // If grade level does not have any questions
        if( !this.nodesByGradeLevel.containsKey(gradeLevel) ) {
            return questions;
        }
        // If grade level does have questions
        for( QuestionNode questionNode : this.nodesByGradeLevel.get(gradeLevel) ) {
            questions.add(questionNode.getQuestion());
        }
        return questions;
    }

    public List<Question> getAllAppropriateQuestions(int gradeLevel, List<Question> relatedToNodes) {
        if( relatedToNodes.isEmpty() ) {
            return getAllAppropriateQuestions(gradeLevel);
        }
        ArrayList<Question> questions = new ArrayList<>();
        for( Question q : relatedToNodes ) {
            for( QuestionNode qNode : this.nodesByQuestionId.get(q.getQuestionId()).getRelatedNodes()) {
                if( qNode.getGradeLevel() >= gradeLevel ) {
                    questions.add(qNode.getQuestion());
                }
            }
        }
        return questions;
    }

    public static QuestionRelatednessGraph getInstance() {
        // TODO initialize
        if( thisInstance == null ) {
            thisInstance = new QuestionRelatednessGraph();
        }
        return thisInstance;
    }
}
