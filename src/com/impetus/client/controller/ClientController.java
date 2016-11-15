package com.impetus.client.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.impetus.client.dto.ConversionInfo;

@Controller
public class ClientController {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@RequestMapping("/")
	public String getIndex1(Map<String, Object> model) {
		return "index";
	}

	@RequestMapping("/index")
	public String getIndex() {
		return "index";
	}

	@RequestMapping("/measurementPage")
	public String getMeasurementPage(Map<String, Object> model) {
		logger.info("Returning Measurement view to render on UI");
		return "measurementConversion";
	}

	@RequestMapping("/temperaturePage")
	public String getTemperaturePage(Map<String, Object> model) {
		logger.info("Returning Temprature view to render on UI");
		return "temperatureConversion";
	}

	@RequestMapping(value = "/measurement", produces = "application/json")
	public @ResponseBody ConversionInfo convertMeasurement(
			@ModelAttribute("convert") Double convert,
			@ModelAttribute("from") String from, @ModelAttribute("to") String to) {
		logger.info("Entering measurement controller");
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("convert", convert.toString());
		parametersMap.put("from", from);
		parametersMap.put("to", to);
		
		logger.info("Rest call to measurement service registered on Eureka Server");		
		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/measurement/{convert}/{from}/{to}",
						ConversionInfo.class, parametersMap);

	}

	@RequestMapping(value = "/temperature", produces = "application/json")
	public @ResponseBody ConversionInfo convertTemperature(
			@ModelAttribute("convert") Double convert,
			@ModelAttribute("from") String from, @ModelAttribute("to") String to) {
		logger.info("Entering temperature controller");
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("convert", convert.toString());
		parametersMap.put("from", from);
		parametersMap.put("to", to);
		
		logger.info("Rest call to temperature service registered on Eureka Server");		
		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/temperature/{convert}/{from}/{to}",
						ConversionInfo.class, parametersMap);

	}
}
