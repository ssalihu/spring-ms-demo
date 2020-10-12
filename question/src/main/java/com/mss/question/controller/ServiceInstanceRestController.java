package com.mss.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
class ServiceInstanceRestController {

	@Autowired
	private EurekaClient discoveryClient;

	@GetMapping("/service-instances/{applicationName}")
	public Application serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getApplication(applicationName);
		//return this.discoveryClient.getInstancesById(applicationName);
	}
}