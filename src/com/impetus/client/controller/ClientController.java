package com.impetus.client.controller;

import static com.impetus.client.common.Constants.CONVERSION_TYPE_MEASUREMENT;
import static com.impetus.client.common.Constants.CONVERSION_TYPE_TEMPERATURE;
import static com.impetus.client.common.Constants.CONVERT;
import static com.impetus.client.common.Constants.FROM;
import static com.impetus.client.common.Constants.TO;
import static com.impetus.client.common.Constants.TYPE;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.impetus.client.dto.ConversionInfo;

@Controller
public class ClientController {

	@Autowired
	protected RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(ClientController.class);

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
		logger.info("Returning measurement view to render on UI");
		return "measurementConversion";
	}

	@RequestMapping("/temperaturePage")
	public String getTemperaturePage(Map<String, Object> model) {
		logger.info("Returning temperature view to render on UI");
		return "temperatureConversion";
	}

	@RequestMapping(value = "/measurement", produces = "application/json")
	public @ResponseBody ConversionInfo convertMeasurement(
			@ModelAttribute("convert") Double convert,
			@ModelAttribute("from") String from, @ModelAttribute("to") String to) {
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(CONVERT, convert.toString());
		parametersMap.put(FROM, from);
		parametersMap.put(TO, to);
		parametersMap.put(TYPE, CONVERSION_TYPE_MEASUREMENT);
		
		logger.info("Call service registered on Eureka server");
		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/calculate/{convert}/{from}/{to}/{type}",
						ConversionInfo.class, parametersMap);

	}

	@RequestMapping(value = "/temperature", produces = "application/json")
	public @ResponseBody ConversionInfo convertTemperature(
			@ModelAttribute("convert") Double convert,
			@ModelAttribute("from") String from, @ModelAttribute("to") String to) {
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(CONVERT, convert.toString());
		parametersMap.put(FROM, from);
		parametersMap.put(TO, to);
		parametersMap.put(TYPE, CONVERSION_TYPE_TEMPERATURE);

		logger.info("Call service registered on Eureka server");
		return restTemplate
				.getForObject(
						"http://MEASUREMENT-UTILITIES-SERVICE/calculate/{convert}/{from}/{to}/{type}",
						ConversionInfo.class, parametersMap);

	}
}
