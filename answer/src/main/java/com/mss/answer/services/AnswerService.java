package com.mss.answer.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mss.answer.data.Answer;
import com.mss.answer.data.RandomResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AnswerService {

	private static ArrayList<String> questions = new ArrayList<String>();
	static {
		questions.add("Answer1");
		questions.add("Answer2");
		questions.add("Answer3");
		questions.add("Answer4");
		questions.add("Answer5");
		questions.add("Answer6");
		questions.add("Answer7");
		questions.add("Answer8");
		questions.add("Answer9");
		questions.add("Answer10");		
	}

	@Autowired
	RestTemplate restTemplate;
	@LoadBalanced // Make sure to create the load-balanced template
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@HystrixCommand(fallbackMethod = "reliable")
	public Answer getAnswer(int index) {
		return new Answer(questions.get(index));
	}
	
	@HystrixCommand(fallbackMethod = "reliable")
	public Answer getAnswer() {
		return getAnswerNumber();
	}
	
	public Answer getAnswerNumber() {
		RandomResponse index = restTemplate.getForObject("http://random-ms/random/10", RandomResponse.class);
		return getAnswer(index.getNumber());
	}

	public Answer reliable() {
		return new Answer("1");
	}
	
	public Answer reliable(int index) {
		return new Answer(String.valueOf(index));
	}
}
