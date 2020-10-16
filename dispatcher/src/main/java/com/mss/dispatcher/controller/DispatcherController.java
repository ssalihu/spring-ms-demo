package com.mss.dispatcher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.dispatcher.service.DispatcherFeignService;
import com.mss.dispatcher.service.DispatcherService;
import com.mss.dispatcher.service.RandomAPIClient;
import com.mss.question.data.FinalResponse;
import com.mss.question.data.RandomResponse;


@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	DispatcherService dispatcherService;
	
	@Autowired
	private RandomAPIClient feignService;
	
	@Autowired
	DispatcherFeignService dispatcherFeignService;
    
	@GetMapping("/question")
	public ResponseEntity<FinalResponse> getQuestion() throws Exception {
		LOG.info("Get a question");
		return new ResponseEntity<FinalResponse>(dispatcherService.getQuestionSet(), HttpStatus.OK);
	}
	
	@GetMapping("/feign/question")
	public ResponseEntity<FinalResponse> getFeignQuestion() throws Exception {
		return new ResponseEntity<FinalResponse>(dispatcherFeignService.getQuestionSet(), HttpStatus.OK);
	}
	
	@GetMapping("/random")
	public ResponseEntity<RandomResponse> getAnotherQuestion() throws Exception {
		return new ResponseEntity<RandomResponse>(feignService.getSomeRandomNumber(), HttpStatus.OK);
	}
	
}
