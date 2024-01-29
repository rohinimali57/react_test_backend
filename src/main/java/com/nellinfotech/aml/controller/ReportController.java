package com.nellinfotech.aml.controller;

import com.nellinfotech.aml.commonUtility.HeaderValidator;
import com.nellinfotech.aml.dto.AlertTransaction;
import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.AlertSubTypeMst;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.entities.RiskProfileStatus;
import com.nellinfotech.aml.model.Header;
import com.nellinfotech.aml.repository.AlertRepository;
import com.nellinfotech.aml.service.AlertService;
import com.nellinfotech.aml.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ReportController {
	@Autowired
	ReportService reportService;

	@Autowired
	AlertService alertService;

	Logger logger = LoggerFactory.getLogger(BankController.class);

	@PostMapping("/getFalsePositiveReport")
	public List<Alert> getFalsePositiveReport(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getFalsePositiveReport(bankCode, toDate, fromDate);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getChannelWiseAlertSummary")
	public List<Alert> getChannelWiseAlertSummary(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getChannelWiseAlertSummary(bankCode, toDate, fromDate);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getAlertStatusReport")
	public List<Alert> getAlertStatusReport(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate, String alertStatus, String alertCode) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getAlertStatusReport(bankCode, toDate, fromDate, alertStatus, alertCode);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getAlertViolationStatistics")
	public List<AlertTransaction> getAlertViolationStatistics(@RequestHeader HttpHeaders httpHeaders,
			@RequestParam String toDate, @RequestParam String fromDate) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return alertService.getAlertViolationStatistics(bankCode, toDate, fromDate);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getConfirmedCaseReport")
	public List<Case> getConfirmedCaseReport(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate, @RequestParam Double minTxnAmt) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getConfirmedCaseReport(bankCode, toDate, fromDate, minTxnAmt);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getCaseListing")
	public List<Case> getCaseListing(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate, @RequestParam String alertCode) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getCaseListing(bankCode, toDate, fromDate, alertCode);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getCostemerRiskmovement")
	public List<RiskProfileStatus> getCostemerRiskmovement(@RequestHeader HttpHeaders httpHeaders, @RequestParam String custCode,
			@RequestParam String toDate, @RequestParam String fromDate) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getCostemerRiskmovement(bankCode, custCode, toDate, fromDate);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getTeamPerfromanceReport")
	public List<Alert> getTeamPerfromanceReport(@RequestHeader HttpHeaders httpHeaders, @RequestParam String toDate,
			@RequestParam String fromDate) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return reportService.getTeamPerfromanceReport(bankCode, toDate, fromDate);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}

	@PostMapping("/getAlertUpdationAuditReport")
	public List<AlertSubTypeMst> getAlertUpdationAuditReport(@RequestHeader HttpHeaders httpHeaders) {
		try {
			Header header = HeaderValidator.headerConversion(httpHeaders);
			String bankCode = header.getBankCode();
			return alertService.getAlertSubType(bankCode);

		} catch (Exception e) {
			logger.error("Error in customer controller" + e);
		}
		return null;

	}
}
