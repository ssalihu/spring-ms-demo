package com.mss.dispatcher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.dispatcher.service.DispatcherService;
import com.mss.question.data.FinalResponse;

import brave.sampler.Sampler;


@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());


	@Autowired
	DispatcherService dispatcherService;
    
	@GetMapping("/question")
	public ResponseEntity<FinalResponse> getQuestion() throws Exception {
		LOG.info("Inside method2");
		return new ResponseEntity<FinalResponse>(dispatcherService.getRandomNumber(), HttpStatus.OK);
		//return dispatcherService.getRandomNumber();
	}
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
