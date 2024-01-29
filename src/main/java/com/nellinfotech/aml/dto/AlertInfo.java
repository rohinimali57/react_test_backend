package com.nellinfotech.aml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertInfo {
		
	
	@JsonProperty("Attribute")
	private String attribute;
	
	@JsonProperty("Value")
	private String value;
	
	@JsonProperty("Violation Parameter")
	private String violationParameter;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getViolationParameter() {
		return violationParameter;
	}

	public void setViolationParameter(String violationParameter) {
		this.violationParameter = violationParameter;
	}

}
