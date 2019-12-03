package model.hunting;

import model.graph.QuestionRelatednessGraph;
import responses.Question;

import java.util.Map;
import java.util.Optional;

public class HuntingModel {

    public Map<HuntingStateActionPair, Float> qLearningTable;
    public QuestionRelatednessGraph relatednessGraph;


    public Optional<Question> getBestAppropriateQuestion(state) {
        relatednessGraph
    }

}
