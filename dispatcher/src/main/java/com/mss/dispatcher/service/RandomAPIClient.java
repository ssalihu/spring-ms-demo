package com.mss.dispatcher.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mss.question.data.RandomResponse;

@FeignClient(name = "random-ms")
public interface RandomAPIClient {
	
    @RequestMapping(method = RequestMethod.GET, value="/random/${random.service.max.num}")
    RandomResponse getSomeRandomNumber();
}
