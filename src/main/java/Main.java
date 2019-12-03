import com.google.gson.Gson;
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
    	try {
			controller = new EduLadderController();

            after((Filter) (request, response) -> {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET,POST");
            });

	        get("/getRankableQuestions", Main::getRankableQuestions);
	        post("/getQuestion", Main::getQuestion);
	        post("/submitRankings", Main::submitRankings);
	        post("/submitAnswer", Main::submitAnswer);
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static String getQuestion(Request req, Response res) {
        GetQuestionRequest reqBody = gson.fromJson(req.body(), GetQuestionRequest.class);
        res.body(
                gson.toJson(
                        new GetQuestionResponse(
                                controller.getQuestion(
                                        reqBody.sessionId, reqBody.gradeLevel
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
        return "TODO";
    }

    private static String submitAnswer(Request req, Response res) {
        return "TODO";
    }
}
