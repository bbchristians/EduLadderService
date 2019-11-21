import com.google.gson.Gson;
import com.google.gson.JsonObject;
import requests.GetQuestionRequest;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

import java.sql.SQLException;

public class Main {

    //http://localhost:4567/hello

    private static EduLadder controller = null;

    public static void main(String[] args) {
    	try {
			controller = new EduLadder();

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
        Gson gson = new Gson();
        GetQuestionRequest reqBody = gson.fromJson(req.body(), GetQuestionRequest.class);
        return gson.toJson(controller.getQuestion(reqBody.sessionId, reqBody.gradeLevel));
    }

    private static String getRankableQuestions(Request req, Response res) {
        return "TODO";
    }

    private static String submitRankings(Request req, Response res) {
        return "TODO";
    }

    private static String submitAnswer(Request req, Response res) {
        return "TODO";
    }
}
