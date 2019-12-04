package model.flushing;

import com.google.common.collect.Lists;
import model.graph.QuestionRelatednessGraph;
import responses.Question;

import java.util.*;

public class FlushingModel {

    private Map<FlushingStateActionPair, Float> qLearningTable;

    // Q learning vars
    private float exploreChance = 0.05f;
    private float learningRate = 0.5f;
    private float discountRate = 0.4f;

    private QuestionRelatednessGraph graph;
    // Cache of session paths
    private Map<String, List<FlushingStateActionPair>> pairsBySession;

    public FlushingModel() {
        this.qLearningTable = new HashMap<>(); // TODO load from persistence
        this.graph = QuestionRelatednessGraph.getInstance();
        this.pairsBySession = new HashMap<>();
    }

    public Optional<Question> getNextQuestion(String sessionId, int gradeLevel, List<Question> answeredQuestions) {
        // Get all possible questions from graph
        Set<Question> validQuestions = new HashSet<>(graph.getAllAppropriateQuestions(gradeLevel));

        // Remove all questions that have been answered already
        validQuestions.removeAll(answeredQuestions);

        // If this leaves no questions remaining
        if( validQuestions.isEmpty() ) {
            // Return an empty optional to signify that the system cannot help the user further
            return Optional.empty();
        }

        // Find the best action given
        FlushingStateActionPair bestStateAction = null;
        List<FlushingStateActionPair> explorableActions = new ArrayList<>();
        for( Question questionCandidate : validQuestions ) {
            // Make a state-action pair from the question
            FlushingStateActionPair candidateSAPair =
                    new FlushingStateActionPair(answeredQuestions, questionCandidate);
            if( bestStateAction == null ) {
                bestStateAction = candidateSAPair;
            } else {
                // If the state-action has not been explored
                if( !this.qLearningTable.containsKey(candidateSAPair) ) {
                    // Add it to a set of potentially explorable actions
                    explorableActions.add(candidateSAPair);
                }
                // If the state-action pair has a larger weight than the previous maximum
                else if( this.qLearningTable.get(candidateSAPair) > this.qLearningTable.get(bestStateAction) )  {
                    explorableActions.add(bestStateAction);
                    bestStateAction = candidateSAPair;
                }
            }
        }
        // Determine if the system should explore
        if( Math.random() < this.exploreChance) {
            // Change to a random explorable
            bestStateAction = explorableActions.get((int)(Math.random() * explorableActions.size()));
        }

        // Keep track of this state-action for future rewards
        if( !this.pairsBySession.containsKey(sessionId) ) {
            this.pairsBySession.put(sessionId, new ArrayList<>());
        }
        this.pairsBySession.get(sessionId).add(bestStateAction);

        // The system has a new action to be attempted
        return Optional.of(bestStateAction.getAction());
    }

    public void addReward(String sessionId, int stepsToGoal) {
        for( FlushingStateActionPair saPair : Lists.reverse(this.pairsBySession.get(sessionId)) ) {
            float newQValue =
                    this.qLearningTable.get(saPair)
                    + this.learningRate * (
                            (1 / stepsToGoal) +
                            (discountRate * getMaxNextState(saPair)) +
                            this.qLearningTable.get(saPair)
                    );
            this.qLearningTable.put(saPair, newQValue);
        }
    }

    private float getMaxNextState(FlushingStateActionPair saPair) {
        List<Question> destinedState = new ArrayList<>(saPair.getState());
        destinedState.add(saPair.getAction());
        float nextReward = 0f;
        for( FlushingStateActionPair tablePair : this.qLearningTable.keySet() ) {
            if( tablePair.getState().equals(destinedState) ) {
                nextReward = Math.max(nextReward, this.qLearningTable.get(tablePair));
            }
        }
        return nextReward;
    }
}
