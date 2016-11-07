package main.java.poc.microservices.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.poc.microservices.client.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	@RequestMapping("/")
	public String getIndex1(Map<String, Object> model) {
		return "index";
	}

	@RequestMapping("/index")
	public String getIndex() {
		return "index";
	}

	@RequestMapping("/userPortal")
	public String getUserPortal(Map<String, Object> model) {
		List<String> measurmentUnitList = new ArrayList<String>();
		measurmentUnitList.add("inch");
		measurmentUnitList.add("feet");
		measurmentUnitList.add("meter");
		model.put("measurmentUnitList", measurmentUnitList);
		model.put("user", new User());
		return "user";
	}

	@RequestMapping("/adminPortal")
	public String getAdminPortal() {
		return "admin";
	}

	@RequestMapping(value = "/convert", produces = "application/json")
	public @ResponseBody Double calculate(
			@ModelAttribute("convertFromTextField") Double convertFromTextField,
			@ModelAttribute("convertFrom") String convertFrom,
			@ModelAttribute("convertTo") String convertTo) {
		System.out.println("calculate method ");
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("convertFromTextField",
				convertFromTextField.toString());
		parametersMap.put("convertFrom", convertFrom);
		parametersMap.put("convertTo", convertTo);

		return restTemplate.getForObject(
				"http://MEASUREMENT-UTILITIES-SERVICE/getUserConversionResult/{convertFromTextField}/{convertFrom}/{convertTo}",
				Double.class, parametersMap);

	}
}
