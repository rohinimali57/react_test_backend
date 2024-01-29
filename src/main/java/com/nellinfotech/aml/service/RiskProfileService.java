package com.nellinfotech.aml.service;

import java.util.ArrayList;
import java.util.List;

import com.nellinfotech.aml.dto.RiskProfile;
import com.nellinfotech.aml.dto.TransTrendRiskRatio;
import com.nellinfotech.aml.dto.ViolationRisk;
import com.nellinfotech.aml.entities.AuxilaryInfo;
import com.nellinfotech.aml.entities.Customer;
import com.nellinfotech.aml.entities.HolidayMst;
import com.nellinfotech.aml.entities.RiskProfileMst;
import com.nellinfotech.aml.entities.RiskProfileStatus;
import com.nellinfotech.aml.entities.RiskWeightageMst;
import com.nellinfotech.aml.model.Header;

public interface RiskProfileService {
    	List<RiskProfileMst> getKYCRiskProfile(String custCode, String branchCode);

		List<ViolationRisk> getViolationRiskProfile(String custCode, String branchCode);

		TransTrendRiskRatio getTransTrendRiskProfile(String custCode, String branchCode);

		List<RiskProfileMst> getTransactionRiskProfile(String custCode, String branchCode);

		List<RiskProfileStatus> getRiskGraphData(String custCode, String branchCode);

		List<RiskWeightageMst> getRiskWightageData(String bankCode);

		
		

		List<RiskProfileStatus> getRiskMovement(String custCode, String fromDate, String toDate);
		
		
		String updateRiskScore(String custCode, String branchCode);

		String updateRiskProfile(String baseBrCode);

		String updateRiskWeightage(Header header, List<RiskWeightageMst> riskWeightageMst);

		void saveAuxilaryInfo(List<AuxilaryInfo> profile);
    
}
