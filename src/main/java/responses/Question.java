package responses;

public class Question {

	private String questionId;
	private String questionText;
	private String[] answers;
	private String units;
	private int gradeLevel;
	
	public Question(String questionId, String questionText, String answerString, String units) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.units = units;
		this.gradeLevel = -1;
		
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
		this.gradeLevel = -1;
	}

	public Question(String questionId, String questionText, String[] answers, String units, int gradeLevel) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.answers = answers;
		this.units = units;
		this.gradeLevel = gradeLevel;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getUnits() { return units; }

	public String[] getAnswers() {
		return answers;
	}

	public int getGradeLevel() {
		return gradeLevel;
	}

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

