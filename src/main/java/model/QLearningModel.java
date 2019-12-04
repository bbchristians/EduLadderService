package model;

import com.google.common.collect.Lists;
import model.graph.QuestionRelatednessGraph;
import responses.Question;

import java.util.*;

public abstract class QLearningModel {

    Map<StateActionPair, Float> qLearningTable;

    private QuestionRelatednessGraph graph;
    // Cache of session paths
    private Map<String, List<StateActionPair>> pairsBySession;

    QLearningModel() {
        this.graph = QuestionRelatednessGraph.getInstance();
        this.pairsBySession = new HashMap<>();
        this.loadQLearningTable();
    }

    public abstract void loadQLearningTable();
    public abstract float getLearningRate();
    public abstract float getDiscountRate();
    public abstract float getExploreChance();

    Optional<Question> getBestQuestionToAsk(String sessionId, Set<Question> validQuestions, List<Question> state) {
        // If this leaves no questions remaining
        if( validQuestions.isEmpty() ) {
            // Return an empty optional to signify that the system cannot help the user further
            return Optional.empty();
        }

        // Get the best action from the Q-table
        Question bestAction = this.getBestQuestionFromQTable(state, validQuestions);

        // Determine if the system should explore
        if( Math.random() < this.getExploreChance() ) {
            // over-write the best action
            bestAction = validQuestions.iterator().next();
        }

        // Make the state action pair
        StateActionPair bestStateAction = new StateActionPair(state, bestAction);

        // Keep track of this state-action for future rewards
        if( !this.pairsBySession.containsKey(sessionId) ) {
            this.pairsBySession.put(sessionId, new ArrayList<>());
        }
        this.pairsBySession.get(sessionId).add(bestStateAction);

        // The system has a new action to be attempted
        return Optional.of(bestStateAction.getAction());
    }

    Set<Question> getQuestionFromRelatednessGraph(
            int gradeLevel, List<Question> answeredQuestions, List<Question> incorrectQuestions) {
        Set<Question> questionsFromGraph = new HashSet<>(
                this.graph.getAllAppropriateQuestions(gradeLevel, incorrectQuestions));
        questionsFromGraph.removeAll(answeredQuestions);
        return questionsFromGraph;
    }

    private Question getBestQuestionFromQTable(List<Question> curState, Set<Question> possibleActions) {
        // Find the best action given
        StateActionPair bestStateAction = null;
        for( Question questionCandidate : possibleActions ) {
            // Make a state-action pair from the question
            StateActionPair candidateSAPair =
                    new StateActionPair(curState, questionCandidate);
            if( bestStateAction == null ) {
                bestStateAction = candidateSAPair;
            } else {
                // If the state-action pair has a larger weight than the previous maximum
                if( this.qLearningTable.containsKey(candidateSAPair) &&
                         this.qLearningTable.get(candidateSAPair) > this.qLearningTable.get(bestStateAction) )  {
                    bestStateAction = candidateSAPair;
                }
            }
        }
        return bestStateAction.getAction();
    }

    public void addReward(String sessionId, int stepsToGoal) {
        for( StateActionPair saPair : Lists.reverse(this.pairsBySession.get(sessionId)) ) {
            float newQValue =
                    this.qLearningTable.get(saPair)
                            + this.getLearningRate() * (
                                    (1 / stepsToGoal) +
                                    (this.getDiscountRate() * getMaxNextState(saPair)) +
                                    this.qLearningTable.get(saPair)
                            );
            this.qLearningTable.put(saPair, newQValue);
        }
    }

    private float getMaxNextState(StateActionPair saPair) {
        List<Question> destinedState = new ArrayList<>(saPair.getState());
        destinedState.add(saPair.getAction());
        float nextReward = 0f;
        for( StateActionPair tablePair : this.qLearningTable.keySet() ) {
            if( tablePair.getState().equals(destinedState) ) {
                nextReward = Math.max(nextReward, this.qLearningTable.get(tablePair));
            }
        }
        return nextReward;
    }

}
