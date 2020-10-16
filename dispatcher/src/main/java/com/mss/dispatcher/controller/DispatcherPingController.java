package com.mss.dispatcher.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.dispatcher.service.AnswerAPIClient;
import com.mss.dispatcher.service.QuestionAPIClient;
import com.mss.dispatcher.service.RandomAPIClient;
import com.mss.question.data.Answer;
import com.mss.question.data.Question;
import com.mss.question.data.RandomResponse;

@RestController
@RequestMapping("/ping")
public class DispatcherPingController {

	@Autowired
	private RandomAPIClient feignRandomService;

	@Autowired
	private QuestionAPIClient feignQuestionService;

	@Autowired
	private AnswerAPIClient feignAnswerService;
	
	Random rand = new Random(10);

	
	@GetMapping("/random")
	public ResponseEntity<RandomResponse> getRandom() throws Exception {
		return new ResponseEntity<RandomResponse>(feignRandomService.getSomeRandomNumber(), HttpStatus.OK);
	}
	@GetMapping("/question")
	public ResponseEntity<Question> getQuestion() throws Exception {
		return new ResponseEntity<Question>(feignQuestionService.getQuestion(String.valueOf(rand.nextInt())), HttpStatus.OK);
	}
	@GetMapping("/answer")
	public ResponseEntity<Answer> getAnswer() throws Exception {
		return new ResponseEntity<Answer>(feignAnswerService.getAnswer(String.valueOf(rand.nextInt())), HttpStatus.OK);
	}
}
