package model.graph;

import responses.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionRelatednessGraph {

    private static float RELATEDNESS_THRESHOLD = 0.5f;

    private static QuestionRelatednessGraph thisInstance = null;

    private Map<Integer, List<QuestionNode>> nodesByGradeLevel;
    private Map<String, QuestionNode> nodesByQuestionId;

    private QuestionRelatednessGraph(
            Map<Integer, List<QuestionNode>> nodesByGradeLevel, Map<String, QuestionNode> nodesByQuestionId) {
        this.nodesByGradeLevel = nodesByGradeLevel;
        this.nodesByQuestionId = nodesByQuestionId;
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

    public static void initializeInstance(List<Question> allQuestions, List<QuestionRelation> relations) {
        List<QuestionNode> generatedNodes = new ArrayList<>();

        // Fields to be used in the instance
        Map<String, QuestionNode> questionsById = new HashMap<>();
        Map<Integer, List<QuestionNode>> questionsByGradeLevel = new HashMap<>();

        // Take a while and find all relations
        for( Question curQuestion : allQuestions ) {
            // Make node
            QuestionNode curNode = generateOrFindNode(curQuestion, generatedNodes);

            // Add to access structures in the graph
            questionsById.put(curQuestion.getQuestionId(), curNode);
            if( !questionsByGradeLevel.containsKey(curQuestion.getGradeLevel()) ) {
                questionsByGradeLevel.put(curQuestion.getGradeLevel(), new ArrayList<>());
            }
            questionsByGradeLevel.get(curQuestion.getGradeLevel()).add(curNode);

            for( Question curRelatedQuestion : allQuestions ) {
                if( !curQuestion.equals(curRelatedQuestion) ) {
                    for( QuestionRelation rel : relations ) {
                        if( rel.getRelatednessScore() >= RELATEDNESS_THRESHOLD &&
                                rel.getQ1().equals(curQuestion.getQuestionId()) &&
                                rel.getQ2().equals(curRelatedQuestion.getQuestionId()) ) {
                            curNode.getRelatedNodes().add(generateOrFindNode(curRelatedQuestion, generatedNodes));
                        }
                    }
                }
            }
        }

        thisInstance = new QuestionRelatednessGraph(questionsByGradeLevel, questionsById);
    }

    private static QuestionNode generateOrFindNode(Question q, List<QuestionNode> generatedNodes) {
        for( QuestionNode node : generatedNodes ) {
            if( node.getQuestion().equals(q) ) {
                return node;
            }
        }
        QuestionNode newNode = new QuestionNode(new ArrayList<>(), q.getGradeLevel(), q);
        generatedNodes.add(newNode);
        return newNode;
    }

    public static QuestionRelatednessGraph getInstance() {
        if( thisInstance == null ) {
            throw new RuntimeException("Relatedness graph not instantiated but was accessed.");
        }
        return thisInstance;
    }
}
