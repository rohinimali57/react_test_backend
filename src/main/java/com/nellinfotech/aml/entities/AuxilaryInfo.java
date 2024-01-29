package com.nellinfotech.aml.entities;

import javax.persistence.*;

@Entity
@Table(name = "auxilary_info") // Make sure table name matches your database table name
public class AuxilaryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This will be the primary key in the database
    
    @Column(nullable = true, length = 25)
    private String parameter;
    
    @Column(nullable = true, length = 25)
    private String status;
    
    @Column(nullable = true, length = 25)
    private String remarks;

    @Column(nullable = true, length = 25)
    private String bankCode;
    
    @Column(nullable = true, length = 25)
    private String customer_Code;
    
	public String getCustomer_Code() {
		return customer_Code;
	}

	public void setCustomer_Code(String customer_Code) {
		this.customer_Code = customer_Code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
}

