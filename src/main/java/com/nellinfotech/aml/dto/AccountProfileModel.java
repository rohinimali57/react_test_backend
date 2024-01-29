package com.nellinfotech.aml.dto;

import java.util.Date;

public class AccountProfileModel 
{

	private String accNumber;
	
	private String name;
	
	private String type;
	
	private String branch;
	
	private String status;
	
	private Date openDate;
	
	private String operationMode;
	
	private String nomineeYn;

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public String getNomineeYn() {
		return nomineeYn;
	}

	public void setNomineeYn(String nomineeYn) {
		this.nomineeYn = nomineeYn;
		
	}
}
