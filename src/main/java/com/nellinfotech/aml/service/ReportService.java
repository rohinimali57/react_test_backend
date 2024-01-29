package com.nellinfotech.aml.service;

import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.AlertSubTypeMst;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.entities.RiskProfileStatus;

import org.springframework.stereotype.Service;

import java.util.List;

public interface ReportService {
	
	List<Alert> getFalsePositiveReport(String bankCode, String toDate, String fromDate);

	List<Alert> getChannelWiseAlertSummary(String bankCode, String toDate, String fromDate);

	List<Alert> getAlertViolationStatistics(String bankCode, String toDate, String fromDate);

	List<Alert> getTeamPerfromanceReport(String bankCode, String toDate, String fromDate);

	List<Alert> getAlertStatusReport(String bankCode, String toDate, String fromDate, String alertStatus,
			String alertCode);

	List<Case> getConfirmedCaseReport(String bankCode, String toDate, String fromDate, Double minTxnAmt);

	List<Case> getCaseListing(String bankCode, String alertCode, String fromDate, String toDate);

	List<RiskProfileStatus> getCostemerRiskmovement(String bankCode, String custCode, String fromDate, String toDate);

	List<AlertSubTypeMst> getAlertUpdationAuditReport(String bankCode);

}
