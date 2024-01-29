package com.nellinfotech.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.AlertSubTypeMst;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.entities.RiskProfileStatus;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Alert, Long> {

	@Query(value = "select * from alert\n"
			+ "where bank_code=:bankCode and (created_date BETWEEN :fromDate and :toDate)", nativeQuery = true)
	List<Alert> getFalsePositiveReport(String bankCode, String toDate, String fromDate);


	@Query(value = "SELECT * " + "FROM alert "
			+ "where bank_code=:bankCode and (created_date BETWEEN :fromDate and :toDate)"
			+ "GROUP BY alert_code", nativeQuery = true)
	List<Alert> getChannelWiseAlertSummary(String bankCode, String toDate, String fromDate);

 	@Query(value = "SELECT * FROM alert " + "WHERE bank_code = :bankCode "
			+ "AND created_date BETWEEN :fromDate AND :toDate " + "AND alert_code = :alertCode "
			+ "AND alert_status = :alertStatus", nativeQuery = true)
	List<Alert> getAlertStatusReport(String bankCode, String toDate, String fromDate, String alertCode,
			String alertStatus);

	@Query(value = "SELECT alert_code, alertsubtype_code, SUM(txn_amt) AS total_amount, COUNT(id) AS violation_count "
			+ "FROM alert " + "WHERE bank_code = :bankCode " + "AND created_date BETWEEN :fromDate AND :toDate "
			+ "GROUP BY alert_code", nativeQuery = true)
	List<Alert> getAlertViolationStatistics(String bankCode, String fromDate, String toDate);

//	@Query(value = "SELECT cust_code, created_date, case_id, createdby_username, description " + "FROM cases "
//			+ "WHERE bank_code = :bankCode " + "AND created_date BETWEEN :fromDate AND :toDate "
//			+ "AND confirmed_case = 'Yes' " + "AND txn_amt > :minTxnAmt", nativeQuery = true)
//	@Query(value = "SELECT *" + "FROM cases "
//		+ "WHERE bank_code = :bankCode " + "AND created_date BETWEEN :fromDate AND :toDate "
//			+ "AND confirmed_case = 'Yes' " + "AND atm > :minTxnAmt", nativeQuery = true)
//	List<Case> getConfirmedCaseReport(String bankCode, String toDate, String fromDate, Double minTxnAmt);
//
//	@Query(value = "SELECT * FROM cases " + "WHERE bank_code = :bankCode " + "AND alert_code = :alertCode "
//			+ "AND created_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
//	List<Case> getCaseListing(String bankCode, String toDate, String fromDate, String alertCode);

//	@Query(value = "SELECT * FROM riskprofile_status " + "WHERE bank_code = :bankCode " + "AND cust_code = :custCode "
//			+ "AND created_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
//	List<RiskProfileStatus> getCostemerRiskmovement(String bankCode, String custCode, String fromDate, String toDate);

	@Query(value = "SELECT *" + "FROM alert "
			+ "WHERE bank_code = :bankCode " + "AND created_date BETWEEN :fromDate AND :toDate "
			+ "GROUP BY assigned_userid", nativeQuery = true)
	List<Alert> getTeamPerfromanceReport(String bankCode, String toDate, String fromDate);

	@Query(value = "SELECT * FROM alertsubtype_mst WHERE bank_code = :bankCode", nativeQuery = true)
	List<AlertSubTypeMst> getAlertUpdationAuditReport(String bankCode);

}
