package com.mss.dispatcher.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mss.question.data.Question;

@FeignClient(name = "question-ms")
public interface QuestionAPIClient {
	
    @RequestMapping(method = RequestMethod.GET, value="/question/")
    Question getQuestion(@RequestParam("number") String number);
}
