package kuizu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Quiz {

	String question;
	String correct;
	String falseAnswer1;
	String falseAnswer2;
	JSONObject details;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public String getFalseAnswer1() {
		return falseAnswer1;
	}

	public void setFalseAnswer1(String falseAnswer1) {
		this.falseAnswer1 = falseAnswer1;
	}

	public String getFalseAnswer2() {
		return falseAnswer2;
	}

	public void setFalseAnswer2(String falseAnswer2) {
		this.falseAnswer2 = falseAnswer2;
	}

	public Quiz(String question, String correct, String falseAnswer1,
			String falseAnswer2, JSONObject details) {
		super();
		this.question = question;
		this.correct = correct;
		this.falseAnswer1 = falseAnswer1;
		this.falseAnswer2 = falseAnswer2;
		this.details = details;
	}

	@Override
	public String toString() {
		return "Quizz [question=" + question + ", correct=" + correct
				+ ", falseAnswer1=" + falseAnswer1 + ", falseAnswer2="
				+ falseAnswer2 + "]";
	}

	public JSONObject getDetails() {
		return details;
	}

	public void setDetails(JSONObject details) {
		this.details = details;
	}
}
