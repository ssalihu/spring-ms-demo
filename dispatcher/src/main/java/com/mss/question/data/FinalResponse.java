package com.mss.question.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinalResponse {

	private Question question;
	private Answer answer;
	
	
	public FinalResponse() {
		super();
	}
	public FinalResponse(Question question, Answer answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
}
