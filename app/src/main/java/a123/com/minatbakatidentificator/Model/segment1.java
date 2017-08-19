package a123.com.minatbakatidentificator.Model;

/**
 * Created by USER on 7/10/2017.
 */

public class segment1 {
    private int questionid;
    private String question;
    private String AnswerA;
    private String AnswerB;
    private String AnswerC;

    public segment1(int questionid, String question, String AnswerA, String AnswerB, String AnswerC) {
        this.questionid = questionid;
        this.question = question;
        this.AnswerA = AnswerA;
        this.AnswerB = AnswerB;
        this.AnswerC = AnswerC;
    }

    public int getQuestionid() { return questionid;}

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String AnswerA) {
        this.AnswerA = AnswerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerb(String AnswerB) {this.AnswerB = AnswerB;}

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String AnswerC) {
        this.AnswerC = AnswerC;
    }
}
