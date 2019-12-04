package responses;

public class NoMoreQuestions extends Question {

    private int response;

    public NoMoreQuestions(int response) {
        super(null, null, new String[0], null);
        this.response = response;
    }

    public int getResponse() {
        return this.response;
    }
}
