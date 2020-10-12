package com.mss.answer.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomResponse {
	
	int number;

	public RandomResponse() {}
	public RandomResponse(int anumber) {
		this.number = anumber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
