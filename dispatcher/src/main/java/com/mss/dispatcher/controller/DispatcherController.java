package com.mss.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.dispatcher.service.DispatcherService;
import com.mss.question.data.FinalResponse;


@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {


	@Autowired
	DispatcherService dispatcherService;
    
	@GetMapping("/question")
	public ResponseEntity<FinalResponse> getQuestion() throws Exception {
		return new ResponseEntity<FinalResponse>(dispatcherService.getRandomNumber(), HttpStatus.OK);
		//return dispatcherService.getRandomNumber();
	}

}
