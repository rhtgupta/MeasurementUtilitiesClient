package main.java.poc.microservices.client.controller;

import java.util.HashMap;
import java.util.Map;

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

	@RequestMapping("/measurementConversionPage")
	public String getMeasurementConversionPage(Map<String, Object> model) {
		return "measurementConversion";
	}

	@RequestMapping("/temperatureConversionPage")
	public String getTemperatureConversionPage(Map<String, Object> model) {
		return "temperatureConversion";
	}

	@RequestMapping(value = "/measurementConversion", produces = "application/json")
	public @ResponseBody Double convertMeasurement(
			@ModelAttribute("convertFromTextField") Double convertFromTextField,
			@ModelAttribute("convertFrom") String convertFrom,
			@ModelAttribute("convertTo") String convertTo) {
		System.out.println("calculate method ");
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("convertFromTextField",
				convertFromTextField.toString());
		parametersMap.put("convertFrom", convertFrom);
		parametersMap.put("convertTo", convertTo);

		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/getMeasurementConversionResult/{convertFromTextField}/{convertFrom}/{convertTo}",
						Double.class, parametersMap);

	}

	@RequestMapping(value = "/temperatureConversion", produces = "application/json")
	public @ResponseBody Double convertTemperature(
			@ModelAttribute("convertFromTextField") Double convertFromTextField,
			@ModelAttribute("convertFrom") String convertFrom,
			@ModelAttribute("convertTo") String convertTo) {
		System.out.println("calculate method ");
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("convertFromTextField",
				convertFromTextField.toString());
		parametersMap.put("convertFrom", convertFrom);
		parametersMap.put("convertTo", convertTo);

		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/getTemperatureConversionResult/{convertFromTextField}/{convertFrom}/{convertTo}",
						Double.class, parametersMap);

	}
}
