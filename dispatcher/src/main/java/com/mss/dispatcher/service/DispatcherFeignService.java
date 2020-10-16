package com.mss.dispatcher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mss.question.data.Answer;
import com.mss.question.data.FinalResponse;
import com.mss.question.data.Question;
import com.mss.question.data.RandomResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@Service
public class DispatcherFeignService {

	@Autowired
	private RandomAPIClient feignRandomService;

	@Autowired
	private QuestionAPIClient feignQuestionService;

	@Autowired
	private AnswerAPIClient feignAnswerService;

	@HystrixCommand(fallbackMethod = "reliable")
	public FinalResponse getQuestionSet() {

		List<CompletableFuture<RandomResponse>> allFutures = new ArrayList<>();
		RandomResponse randResponse = null;
		FinalResponse response = null;

		try {
			allFutures.add(callFeignBasedRandomApiCall());
			CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
			randResponse = allFutures.get(0).get();
			CompletableFuture<FinalResponse> finalResponse = questionAndAnswer(
					String.valueOf(randResponse.getNumber()));

			response = finalResponse.get();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public FinalResponse reliable() {
		return new FinalResponse();
	}

	public CompletableFuture<FinalResponse> questionAndAnswer(String index) {
		// Run a task specified by a Supplier object asynchronously
		CompletableFuture<Question> questionFuture = CompletableFuture.supplyAsync(new Supplier<Question>() {
			@Override
			public Question get() {
				Question responseObj = feignQuestionService.getQuestion(index);
				return responseObj;
			}
		});
		CompletableFuture<Answer> answerFuture = CompletableFuture.supplyAsync(new Supplier<Answer>() {
			@Override
			public Answer get() {
				Answer responseObj = new Answer();
				try {
					responseObj = feignAnswerService.getAnswer(index);
				} catch (Exception e) {
					// throw new IllegalStateException(e);
					e.printStackTrace();
				}
				return responseObj;
			}
		});
		return CompletableFuture.allOf(questionFuture, answerFuture).thenApplyAsync(dummy -> {

			FinalResponse response = new FinalResponse();
			Question question = questionFuture.join();
			if (question != null) {
				response.setQuestion(question);
			}

			Answer answer = answerFuture.join();
			if (answer != null) {
				response.setAnswer(answer);
			}

			return response;
		});
	}

	@Async
	public CompletableFuture<RandomResponse> callFeignBasedRandomApiCall() {
		RandomResponse responseObj = feignRandomService.getSomeRandomNumber();
		return CompletableFuture.completedFuture(responseObj);
	}
}
