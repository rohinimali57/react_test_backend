package com.nellinfotech.aml.service;

import java.util.List;

import com.nellinfotech.aml.dto.AlertStatistics;
import com.nellinfotech.aml.dto.CaseInfo;
import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.model.Header;

public interface CaseService {
    
//    BranchHolidayMap saveBranchHoliday(Header header, BranchHolidayMap branchHolidayMap);
//    
//    List<BranchHolidayMap> getBranchHollidayList(String brCode);
//    
    Case saveCase(Header header, Case amlCase);

	List<Case> getCaseList(String bankCode, String toDate, String fromDate, String alertType);

	List<Case> getDashboardCaseList(String bankCode, String toDate, String fromDate, String alertType,
			String alertSubCode, String customerRisk);

	List<Case> getCaseListByCustomerID(String bankCode, String custCode);

	List<Case> getCaseListByCaseID(String bankCode, String caseID);

	List<Case> searchCase(String bankCode, String toDate, String fromDate, String caseID, String creatorName,
			String customerName, String customerNumber, String status);

	List<Case> updateCaseStatus(Header header, List<Case> amlCase);

	List<Case> updateConfirmFraud(Header header, List<Case> amlCase);

	List<Case> getConfirmedFraudList(String bankCode, String toDate, String fromDate, String operator, String amount);

	List<Case> getCase(String bankCode, String custCode);

	List<AlertStatistics> alertStatistics(Header header);

	CaseInfo getCaseInfo(String bankCode, String caseId);

	String rejectAlert(String bankCode, String remark, Long caseId);

	String cancelCase(String bankCode, Long caseId);

	String confirmedCase(String bankCode, Long caseId);

	String ecalateCase(String bankCode, Long caseId);

	String escalateCase(String bankCode, String caseId, String assignedTo);

	String createCase(String bankCode, String alertId, String custNumber, String userId,String custName);

	List<Case> findAllCases(String bankCode);

	String rejectCase(String bankCode, String remark, Long caseId);

//    
}
