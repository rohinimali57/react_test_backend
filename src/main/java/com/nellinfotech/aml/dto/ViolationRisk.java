package com.nellinfotech.aml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ViolationRisk {
		    private String ruleSeverity;
	    private double violationCount;

		public String getRuleSeverity() {
			return ruleSeverity;
		}
		public void setRuleSeverity(String ruleSeverity) {
			this.ruleSeverity = ruleSeverity;
		}
		public double getViolationCount() {
			return violationCount;
		}
		public void setViolationCount(double violationCount) {
			this.violationCount = violationCount;
		}
}
