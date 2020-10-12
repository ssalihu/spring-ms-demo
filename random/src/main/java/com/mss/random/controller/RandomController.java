package com.mss.random.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mss.random.model.RandomResponse;
import com.mss.random.services.RandomService;

@RestController
public class RandomController {

	@Autowired
	private RandomService service;
	
	@GetMapping("/random/{max}")
	public RandomResponse getRandom(@PathVariable int max) {
		return service.getRandom(max);
	}
	
}
