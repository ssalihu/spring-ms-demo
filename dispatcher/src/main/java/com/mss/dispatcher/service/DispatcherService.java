package com.mss.dispatcher.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mss.question.data.Answer;
import com.mss.question.data.FinalResponse;
import com.mss.question.data.Question;
import com.mss.question.data.RandomResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@Service
public class DispatcherService {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${random.service.endpoint}")
	private String randomServiceEndpoint;

	@Value("${question.service.endpoint}")
	private String questionServiceEndpoint;
	
	@Value("${answer.service.endpoint}")
	private String answerServiceEndpoint;
	
	@Value("${random.service.max.num}")
	private String randomMax;
	
	@HystrixCommand(fallbackMethod = "reliable")
	public FinalResponse getRandomNumber() {

		Instant start = Instant.now();
		List<CompletableFuture<RandomResponse>> allFutures = new ArrayList<>();
		RandomResponse randResponse = null;
		FinalResponse response = null;

		try {
			allFutures.add(callOtherService());
			CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
			//System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

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
				String localSlowServiceEndpoint = questionServiceEndpoint.concat(index);
				Question responseObj = restTemplate.getForObject(localSlowServiceEndpoint, Question.class);
				return responseObj;
			}
		});
		CompletableFuture<Answer> answerFuture = CompletableFuture.supplyAsync(new Supplier<Answer>() {
			@Override
			public Answer get() {
				Answer responseObj = new Answer();
				try {
					String localSlowServiceEndpoint = answerServiceEndpoint.concat(index);
					responseObj = restTemplate.getForObject(localSlowServiceEndpoint, Answer.class);
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
	public CompletableFuture<RandomResponse> callOtherService() {
		String localSlowServiceEndpoint = randomServiceEndpoint.concat(randomMax);//"http://random-ms/random/10";
		RandomResponse responseObj = restTemplate.getForObject(localSlowServiceEndpoint, RandomResponse.class);
		return CompletableFuture.completedFuture(responseObj);
	}

	@Async
	public CompletableFuture<Question> getQuestion(String index) {
		String localSlowServiceEndpoint = "http://question-ms/question/".concat(index);
		Question responseObj = restTemplate.getForObject(localSlowServiceEndpoint, Question.class);
		return CompletableFuture.completedFuture(responseObj);
	}

	@Async
	public CompletableFuture<Answer> getAnswer(String index) {
		String localSlowServiceEndpoint = "http://answer-ms/question/".concat(index);
		Answer responseObj = restTemplate.getForObject(localSlowServiceEndpoint, Answer.class);
		return CompletableFuture.completedFuture(responseObj);
	}
}
