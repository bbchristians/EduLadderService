

public class Question {

	private String questionId;
	private String questionText;
	private String answer;
	
	public Question(String questionId, String questionText, String answer) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.answer = answer;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
