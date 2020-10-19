package com.mss.dispatcher.service;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mss.question.data.Answer;

@RefreshScope
@FeignClient(name = "${answer.service.ribbon-name}")
public interface AnswerAPIClient {
	
    @RequestMapping(method = RequestMethod.GET, value="/answer/")
    Answer getAnswer(@RequestParam("number") String number);
}
