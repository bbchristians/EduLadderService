import com.google.gson.Gson;
import controller.EduLadderController;
import requests.GetQuestionRequest;
import responses.GetQuestionResponse;
import responses.GetRankableQuestionsResponse;
import responses.Question;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

import java.sql.SQLException;

public class Main {

    // http://localhost:4567/

    private static EduLadderController controller = null;
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        controller = new EduLadderController();

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST");
        });

        get("/getRankableQuestions", Main::getRankableQuestions);
        post("/getQuestion", Main::getQuestion);
        post("/submitRankings", Main::submitRankings);
        post("/submitAnswer", Main::submitAnswer);
    }

    private static String getQuestion(Request req, Response res) {
        GetQuestionRequest reqBody = gson.fromJson(req.body(), GetQuestionRequest.class);
        res.body(
                gson.toJson(
                        new GetQuestionResponse(
                                controller.getQuestion(
                                        reqBody.getSessionId(),
                                        reqBody.getGradeLevel(),
                                        reqBody.getAnsweredQuestions().getCorrectQuestions(),
                                        reqBody.getAnsweredQuestions().getIncorrectQuestions()
                                )
                        )
                )
        );
        return "";
    }

    private static String getRankableQuestions(Request req, Response res) throws InterruptedException {
        Question[] questions = controller.getRankableQuestions();
        res.body(
                gson.toJson(
                        new GetRankableQuestionsResponse(
                                questions[0], questions[1]
                        )
                )
        );
        return "";
    }

    private static String submitRankings(Request req, Response res) {
        // This does not need to be implemented for this project
        return "TODO";
    }

    private static String submitAnswer(Request req, Response res) {
        // This does not need to be implemented for this project
        // All question validation will be done on the front end.
        return "TODO";
    }
}
