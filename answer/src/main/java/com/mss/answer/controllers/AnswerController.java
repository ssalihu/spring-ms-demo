package com.mss.answer.controllers;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mss.answer.data.Answer;
import com.mss.answer.data.PongResponse;
import com.mss.answer.services.AnswerService;

@RestController
public class AnswerController {

	@Autowired
	AnswerService answer;

	@GetMapping("/answer")
	@Produces({ MediaType.APPLICATION_JSON }) // add MediaType.APPLICATION_XML if you want XML as well (don't forget
												// @XmlRootElement)
	public ResponseEntity<Answer> recommendations() {
		return new ResponseEntity<Answer>(answer.getAnswer(), HttpStatus.OK);
	}
	
	@GetMapping("/answer/{number}")
	public Answer getAnswer(@PathVariable int number) {
		return answer.getAnswer(number);
	}

	@GetMapping("/ping")
	public ResponseEntity<PongResponse> ping() {
		return new ResponseEntity<PongResponse>(new PongResponse("pong"), HttpStatus.OK);
	}
}
