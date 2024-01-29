package com.nellinfotech.aml.serviceImpl;

import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.AlertSubTypeMst;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.entities.RiskProfileStatus;
import com.nellinfotech.aml.repository.CaseRepository;
import com.nellinfotech.aml.repository.ReportRepository;
import com.nellinfotech.aml.repository.RiskProfileStatusRepository;
import com.nellinfotech.aml.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    RiskProfileStatusRepository riskProfileStatusRepository;
    @Autowired
    CaseRepository caseRepository;
    
    
    @Override
    public List<Alert> getFalsePositiveReport(String bankCode, String toDate, String fromDate) {
        try {
            return reportRepository.getFalsePositiveReport(bankCode, toDate, fromDate);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Alert> getChannelWiseAlertSummary(String bankCode, String toDate, String fromDate) {
    	 try {
             return reportRepository.getChannelWiseAlertSummary(bankCode, toDate, fromDate);
         }

         catch(Exception e) {
             e.printStackTrace();
         }
         return null;
    }

	@Override
	public List<Alert> getAlertStatusReport(String bankCode, String toDate, String fromDate,String alertStatus,String alertCode) {
		try {
            return reportRepository.getAlertStatusReport(bankCode, toDate, fromDate,alertCode,alertStatus);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Alert> getAlertViolationStatistics(String bankCode, String toDate, String fromDate) {
		try {
            return reportRepository.getAlertViolationStatistics(bankCode, toDate, fromDate);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Case> getConfirmedCaseReport(String bankCode, String toDate, String fromDate, Double minTxnAmt) {
		try {
             List<Case> confirmedCaseReport = caseRepository.getConfirmedCaseReport(bankCode, toDate, fromDate, minTxnAmt);
             return confirmedCaseReport;
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Case> getCaseListing(String bankCode, String toDate, String fromDate, String alertCode) {
		try {
           List<Case> caseListing = caseRepository.getCaseListing(bankCode, toDate, fromDate, alertCode);
           return caseListing;
        }

        catch(Exception e) {
            e.printStackTrace(); 
        }
		return null;
	}

	@Override
	public List<RiskProfileStatus> getCostemerRiskmovement(String bankCode, String custCode, String toDate, String fromDate) {
		try {
             List<RiskProfileStatus> costemerRiskmovement = riskProfileStatusRepository.getCostemerRiskmovement(bankCode, custCode, fromDate, toDate);
             return costemerRiskmovement;
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Alert> getTeamPerfromanceReport(String bankCode, String toDate, String fromDate) {
		try {
            return reportRepository.getTeamPerfromanceReport(bankCode, toDate, fromDate);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<AlertSubTypeMst> getAlertUpdationAuditReport(String bankCode) {
		try {
            return reportRepository.getAlertUpdationAuditReport(bankCode);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}


}
