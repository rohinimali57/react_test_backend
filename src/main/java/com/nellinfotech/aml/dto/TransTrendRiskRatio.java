package com.nellinfotech.aml.dto;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TransTrendRiskRatio {
	public double getCreditDeviation() {
		return creditDeviation;
	}
	public void setCreditDeviation(double creditDeviation) {
		this.creditDeviation = creditDeviation;
	}
	public double getDebitDeviation() {
		return debitDeviation;
	}
	public void setDebitDeviation(double debitDeviation) {
		this.debitDeviation = debitDeviation;
	}
	public double getAvgDeviation() {
		return avgDeviation;
	}
	public void setAvgDeviation(double avgDeviation) {
		this.avgDeviation = avgDeviation;
	}
	@JsonProperty("creditDeviation")
	  private double creditDeviation;
	    private double debitDeviation;
	    private double avgDeviation;
}
