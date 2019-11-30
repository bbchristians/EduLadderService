package responses;

public class Question {

	private String questionId;
	private String questionText;
	private String[] answers;
	private String units;
	
	public Question(String questionId, String questionText, String answerString, String units) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.units = units;
		
		String[] answerArray = answerString.split(",");
		this.answers = new String[answerArray.length];
		for(int i = 0; i < answers.length; i++) {
			answers[i] = answerArray[i].trim();
		}
	}
	
	public Question(String questionId, String questionText, String[] answers, String units) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.answers = answers;
		this.units = units;
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

	public String[] getAnswer() {
		return answers;
	}

	public void setAnswer(String[] answers) {
		this.answers = answers;
	}

	public String getUnits() { return units; }

	public void setUnits(String units) { this.units = units; }
	
	public static String[] parseAnswers(String answerString) {
		
		String[] answerArray = answerString.split(",");
	
		for(int i = 0; i < answerArray.length; i++) {
			answerArray[i] = answerArray[i].trim();
		}
		
		return answerArray;
	}
	
	@Override
	public String toString() {
		String questionString = "[" + questionId + "]: " + questionText;
		
		for(String answer : answers) {
			questionString += "\n\t" + "Answer: " + answer;
		}
				
		return questionString;
	}
}

