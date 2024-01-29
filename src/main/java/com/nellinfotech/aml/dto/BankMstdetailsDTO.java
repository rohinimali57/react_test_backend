package com.nellinfotech.aml.dto;

public class BankMstdetailsDTO {
	
	private int id;
	private String bankCode;
	private String bank_name;
	private String bankLogo	;
	private String bank_ifsc;
	private String bank_address;
	private Long bank_contact_no;
	public BankMstdetailsDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBankLogo() {
		return bankLogo;
	}
	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}
	public String getBank_ifsc() {
		return bank_ifsc;
	}
	public void setBank_ifsc(String bank_ifsc) {
		this.bank_ifsc = bank_ifsc;
	}
	public String getBank_address() {
		return bank_address;
	}
	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}
	public Long getBank_contactNo() {
		return bank_contact_no;
	}
	public void setBank_contactNo(Long bank_contactNo) {
		this.bank_contact_no =  bank_contactNo;
	}
	public BankMstdetailsDTO(int id, String bankCode, String bank_name, String bankLogo, String bank_ifsc,
			String bank_address, Long bank_contactNo) {
		super();
		this.id = id;
		this.bankCode = bankCode;
		this.bank_name = bank_name;
		this.bankLogo = bankLogo;
		this.bank_ifsc = bank_ifsc;
		this.bank_address = bank_address;
		this.bank_contact_no = bank_contactNo;
	}
	@Override
	public String toString() {
		return "BankMstdetailsDTO [id=" + id + ", bankCode=" + bankCode + ", bank_name=" + bank_name + ", bankLogo="
				+ bankLogo + ", bank_ifsc=" + bank_ifsc + ", bank_address=" + bank_address + ", bank_contactNo="
				+ bank_contact_no + "]";
	}
	
		}
