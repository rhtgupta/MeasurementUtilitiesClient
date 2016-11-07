package main.java.poc.microservices.client.controller;

import java.util.Map;

import main.java.poc.microservices.client.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@RestController
public class ClientRestController {

	private Map<String, Integer> totalRequestCountMap;

	public ClientRestController() {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		totalRequestCountMap = instance.getMap("requestCount");
	}

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	@RequestMapping(value = "/testUser", produces = "application/json")
	public void test(
			@ModelAttribute("convertFromTextField") double convertFromTextField,
			@ModelAttribute("convertFrom") String convertFrom,
			@ModelAttribute("convertTo") String convertTo) {
		System.out.println("ClientController test method......!!!!!");
		/*for (int i = 0; i < 10; i++) {
			User user = restTemplate.getForObject(
					"http://MEASUREMENT-UTILITIES-SERVICE/getUser", User.class);
			System.out.println(user);
		}*/
		User user = restTemplate.getForObject(
				"http://MEASUREMENT-UTILITIES-SERVICE/getUser", User.class);
		System.out.println(totalRequestCountMap.get("user"));

	}

}
