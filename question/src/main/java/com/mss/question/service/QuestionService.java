package com.mss.question.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mss.question.data.Question;
import com.mss.question.data.RandomResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class QuestionService {

	private static ArrayList<String> questions = new ArrayList<String>();
	static {
		questions.add("Quest1");
		questions.add("Quest2");
		questions.add("Quest3");
		questions.add("Quest4");
		questions.add("Quest5");
		questions.add("Quest6");
		questions.add("Quest7");
		questions.add("Quest8");
		questions.add("Quest9");
		questions.add("Quest10");		
	}

	@Autowired
	RestTemplate restTemplate;
	@LoadBalanced // Make sure to create the load-balanced template
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@HystrixCommand(fallbackMethod = "reliable")
	public Question getQuestion(int index) {
		return new Question(questions.get(index));
	}
	
	
	@HystrixCommand(fallbackMethod = "reliable")
	public Question getQuestion() {
		return getQuestionNumber();
	}
	
	public Question getQuestionNumber() {
		RandomResponse index = restTemplate.getForObject("http://random-ms/random/10", RandomResponse.class);
		return getQuestion(index.getNumber());
	}

	public Question reliable() {
		return new Question("1");
	}
	
	public Question reliable(int index) {
		return new Question(String.valueOf(index));
	}
}
