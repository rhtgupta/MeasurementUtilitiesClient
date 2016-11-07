package main.java.poc.microservices.client.dto;

public class User {
	private String convertFrom;
	private String convertTo;
	private double convertFromTextField;

	public String getConvertFrom() {
		return convertFrom;
	}

	public void setConvertFrom(String convertFrom) {
		this.convertFrom = convertFrom;
	}

	public String getConvertTo() {
		return convertTo;
	}

	public void setConvertTo(String convertTo) {
		this.convertTo = convertTo;
	}

	public double getConvertFromTextField() {
		return convertFromTextField;
	}

	public void setConvertFromTextField(double convertFromTextField) {
		this.convertFromTextField = convertFromTextField;
	}

}
