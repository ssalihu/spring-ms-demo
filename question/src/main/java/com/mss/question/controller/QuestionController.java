package com.mss.question.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mss.question.data.PongResponse;
import com.mss.question.data.Question;
import com.mss.question.service.QuestionService;

import brave.sampler.Sampler;

@RestController
public class QuestionController {

	@Autowired
	QuestionService questions;



	@GetMapping("/question")
	@Produces({ MediaType.APPLICATION_JSON }) // add MediaType.APPLICATION_XML if you want XML as well (don't forget
												// @XmlRootElement)
	public ResponseEntity<Question> recommendations() {
		return new ResponseEntity<Question>(questions.getQuestion(), HttpStatus.OK);
	}

	@GetMapping("/question/{number}")
	public ResponseEntity<Question> getQuestion(@PathVariable int number) {
		return new ResponseEntity<Question>(questions.getQuestion(number), HttpStatus.OK);
	}

	@GetMapping("/ping")
	public ResponseEntity<PongResponse> ping() {
		return new ResponseEntity<PongResponse>(new PongResponse("pong"), HttpStatus.OK);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
