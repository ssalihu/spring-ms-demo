package com.mss.random.services;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.mss.random.model.RandomResponse;

@Service
public class RandomService {

	private static RandomUtils util = new RandomUtils();

	public RandomResponse getRandom(int max) {
		return new RandomResponse(util.nextInt(max));
	}

}
