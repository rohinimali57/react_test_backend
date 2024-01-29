package com.nellinfotech.aml.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.jayway.jsonpath.internal.function.text.Concatenate;
import com.nellinfotech.aml.commonUtility.DateValidator;
import com.nellinfotech.aml.dto.TransTrendRiskRatio;
import com.nellinfotech.aml.dto.ViolationRisk;
import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.AuxilaryInfo;
import com.nellinfotech.aml.entities.Customer;
import com.nellinfotech.aml.entities.RiskProfileMst;
import com.nellinfotech.aml.entities.RiskProfileStatus;
import com.nellinfotech.aml.entities.RiskWeightageMst;
import com.nellinfotech.aml.entities.Transaction;
//import com.nellinfotech.aml.entities.Account;
import com.nellinfotech.aml.model.Header;
import com.nellinfotech.aml.repository.AlertRepository;
import com.nellinfotech.aml.repository.AuxilaryInfoRepository;
import com.nellinfotech.aml.repository.CustomerRepository;
import com.nellinfotech.aml.repository.RiskProfileMstRepository;
import com.nellinfotech.aml.repository.RiskProfileStatusRepository;
import com.nellinfotech.aml.repository.RiskWeightageMstRepository;
import com.nellinfotech.aml.repository.TransactionRepository;
import com.nellinfotech.aml.service.RiskProfileService;

/**
 * @author TUSHAR
 */

@Service
public class RiskprofileServiceImpl implements RiskProfileService {

	@Autowired
	CustomerRepository customerRpository;
	@Autowired
	AlertRepository alertRepository;
	@Autowired
	RiskProfileMstRepository riskProfileMstepository;

	@Autowired
	RiskProfileStatusRepository riskProfilestatusrepository;

	@Autowired
	AuxilaryInfoRepository auxilaryInfoRepository;

	@Autowired
	DateValidator dateValidator;

	@Autowired
	TransactionRepository transactionRpository;

	@Autowired
	RiskWeightageMstRepository riskWeightageMstRepository;
	Transaction transaction;

	Double kycRiskvalue = 0.0;
	Double transactionRiskvalue = 0.0;
	Double transactionTrendRiskvalue = 0.0;
	Double violationRiskvalue = 0.0;

	@Override
	public List<RiskProfileMst> getKYCRiskProfile(String custCode, String branchCode) {
		List<RiskProfileMst> riskProfileMst = new ArrayList<RiskProfileMst>();
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String countryOfOrigin = null;
		String customerType = null;
		String industry = null;
		String nationality = null;
		String qualification = null;
		String incomeSource = null;
		String occupation = null;
		String isPEP = null;
		Date currentDate = new Date();

		try {
			Customer customer = customerRpository.findByCustCode(custCode);
			countryOfOrigin = customer.getCountryOfReg();
			customerType = customer.getCustomerType();
			industry = customer.getIndustryType();

			if (customer.getNationality().equals("IN")) {
				nationality = "Indian";
			}
			qualification = customer.getQualification();

			if (customer.getIsSelfEmployedYN().equals(0)) {
				incomeSource = "Self-Employed";
			}
			occupation = customer.getTypeOfBusiness();
			if (customer.getIsPEP().equals(1)) {
				isPEP = "No";
			}

			int d1 = Integer.parseInt(formatter.format(customer.getCustomerDOB()));
			int d2 = Integer.parseInt(formatter.format(currentDate));
			int age = (d2 - d1) / 10000;

			riskProfileMst = riskProfileMstepository.getKYCParameters(branchCode, countryOfOrigin, customerType,
					industry, nationality, occupation, qualification, incomeSource, isPEP);

			for (int i = 0; i < riskProfileMst.size(); i++) {
				if (riskProfileMst.get(i).getRiskParameter().equals("Country of Origin")
						&& riskProfileMst.get(i).getRiskValue() == countryOfOrigin) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Customer Type")
						&& riskProfileMst.get(i).getRiskValue() == customerType)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Industry")
						&& riskProfileMst.get(i).getRiskValue() == industry)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Nationality")
						&& riskProfileMst.get(i).getRiskValue() == nationality)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Qualification")
						&& riskProfileMst.get(i).getRiskValue() == qualification)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Age Group") && (age >= 20 || age <= 25))) {
					riskProfileMst.get(i).setRiskValue("20-25yrs");
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Income Source")
						&& riskProfileMst.get(i).getRiskValue() == incomeSource)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("Occupation")
						&& riskProfileMst.get(i).getRiskValue() == occupation)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				} else if ((riskProfileMst.get(i).getRiskParameter().equals("IS PEP")
						&& riskProfileMst.get(i).getRiskValue() == isPEP)) {
					riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return riskProfileMst;
	}

	@Override
	public List<ViolationRisk> getViolationRiskProfile(String custCode, String branchCode) {

 List<Alert[]> riskSeverity1 = alertRepository.getRiskSeverity(custCode);
		int violationCount = 0;
		int ruleSeverity=0;
		String Severity = "";
		int count;
		int value = 0;

		for (int i = 0; i <= riskSeverity1.size(); i++) {
			for (Object[] row : riskSeverity1) {
				ruleSeverity = (int) row[0];
				violationCount = Integer.parseInt(row[1].toString()); // Parse the total as an int
			}
//			ruleSeverity = Integer.parseInt(riskSeverity1.get(i).getRiskSeverity());
//			violationCount = riskSeverity1.get(i)[0].getRiskSeverity();
			count = 0;
			count = ruleSeverity * violationCount;
			value = value + count;
		}
		if (value >= 0 || value < 3) {
			Severity = "Low";
		} else if (value >= 3 || value < 7) {
			Severity = "Medium";
		} else if (value >= 7 || value < 10) {
			Severity = "High";
		}

		List<ViolationRisk> V1 = new ArrayList<ViolationRisk>();
//		V1.setRuleSeverity(Severity);
//		V1.setViolationCount(violationCount);
		return V1;
	}

	@Override
	public TransTrendRiskRatio getTransTrendRiskProfile(String custCode, String branchCode) {
		double firstMonth_Cr = 0;
		double secondMonth_Cr = 0;
		double thridMonth_Cr = 0;
		double currentMont_Cr = 0;
		double firstMonth_Dr = 0;
		double secondMonth_Dr = 0;
		double thridMonth_Dr = 0;
		double currentMont_Dr = 0;
		double avgOfCr = 0;
		double avgOfDr = 0;
		double creditDeviation = 0;
		double debitDeviation = 0;
		double avgDeviation = 0;
		TransTrendRiskRatio T1 = new TransTrendRiskRatio();

		Date dateString;
		try {
			List<Transaction> drCrTransactions = transactionRpository.getDrCrTransactions(custCode);

			for (int i = 0; i < drCrTransactions.size(); i++) {
				dateString = drCrTransactions.get(i).getTxnDtTm();
				Date dateToCheck = null;
				LocalDate currentDate = LocalDate.now();
				LocalDate previousThirdMonth = currentDate.minus(Period.ofMonths(3));
				LocalDate previousSecondMonth = currentDate.minus(Period.ofMonths(2));
				LocalDate previousfirstMonth = currentDate.minus(Period.ofMonths(1));
				int threeMonth = previousThirdMonth.getMonthValue();
				int secondMonth = previousSecondMonth.getMonthValue();
				int firstMonth = previousfirstMonth.getMonthValue();
				if (dateString.getMonth() + 1 == threeMonth) {
					if (drCrTransactions.get(i).getCashflowType().equals("cr")) {
						firstMonth_Cr = drCrTransactions.get(i).getTxnAmount().doubleValue() + firstMonth_Cr;
					}
					if (drCrTransactions.get(i).getCashflowType().equals("dr")) {
						firstMonth_Dr = drCrTransactions.get(i).getTxnAmount().doubleValue() + firstMonth_Dr;
					}
				}
				if (dateString.getMonth() + 1 == secondMonth) {
					if (drCrTransactions.get(i).getCashflowType().equals("cr")) {
						secondMonth_Cr = drCrTransactions.get(i).getTxnAmount().doubleValue() + secondMonth_Cr;
					}
					if (drCrTransactions.get(i).getCashflowType().equals("dr")) {
						secondMonth_Dr = drCrTransactions.get(i).getTxnAmount().doubleValue() + secondMonth_Dr;
					}
				}
				if (dateString.getMonth() + 1 == firstMonth) {
					if (drCrTransactions.get(i).getCashflowType().equals("cr")) {
						thridMonth_Cr = drCrTransactions.get(i).getTxnAmount().doubleValue() + thridMonth_Cr;
					}
					if (drCrTransactions.get(i).getCashflowType().equals("dr")) {
						thridMonth_Dr = drCrTransactions.get(i).getTxnAmount().doubleValue() + thridMonth_Dr;
					}
				}
				if (dateString.getMonth() + 1 == firstMonth + 1) {
					if (drCrTransactions.get(i).getCashflowType().equals("cr")) {
						currentMont_Cr = drCrTransactions.get(i).getTxnAmount().doubleValue() + currentMont_Cr;
					}
					if (drCrTransactions.get(i).getCashflowType().equals("dr")) {
						currentMont_Dr = drCrTransactions.get(i).getTxnAmount().doubleValue() + currentMont_Dr;
					}
				}
				avgOfCr = (firstMonth_Cr + secondMonth_Cr + thridMonth_Cr) / 3;
				avgOfDr = (firstMonth_Dr + secondMonth_Dr + thridMonth_Dr) / 3;

				creditDeviation = ((avgOfCr - currentMont_Cr) / avgOfCr) * 100;
				debitDeviation = ((avgOfDr - currentMont_Dr) / avgOfDr) * 100;

				avgDeviation = (creditDeviation + debitDeviation) / 2;
			}
			T1.setAvgDeviation(avgDeviation);
			T1.setCreditDeviation(creditDeviation);
			T1.setDebitDeviation(debitDeviation);
			return T1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		transactionTrendRiskvalue = 0.00;
		return null;
	}

	@Override
	public List<RiskProfileMst> getTransactionRiskProfile(String custCode, String branchCode) {
		List<RiskProfileMst> riskProfileMst = new ArrayList<RiskProfileMst>();

		int transactionCount = 0;
		Double atmWithdrawarPercent;
		Double inwardClgPercent;
		Double cashDepositsPercent;
		int atmTransCount = 0;
		int inwardClgTransCount = 0;
		int cashDepositsTransCount = 0;

		try {
			List<Transaction> transaction = transactionRpository.getATMWithdrawlCount(custCode);
			transactionCount = transaction.size();

			for (int i = 0; i < transaction.size(); i++) {
				if (transaction.get(i).getTxnType().equals("ATM")
						&& transaction.get(i).getCashflowType().equals("DR")) {
					atmTransCount = atmTransCount + 1;

				} else if (transaction.get(i).getTxnType().equals("Inward Clearing")
						&& transaction.get(i).getCashflowType().equals("DR")) {
					inwardClgTransCount = inwardClgTransCount + 1;

				} else if (transaction.get(i).getTxnType().equals("Cash Deposits")
						&& transaction.get(i).getCashflowType().equals("DR")) {
					cashDepositsTransCount = cashDepositsTransCount + 1;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		atmWithdrawarPercent = (double) ((atmTransCount * 100) / transactionCount);
		inwardClgPercent = (double) ((inwardClgTransCount * 100) / transactionCount);
		cashDepositsPercent = (double) ((cashDepositsTransCount * 100) / transactionCount);
		try {
			riskProfileMst = riskProfileMstepository.getTransactionRiskParameters(branchCode);

			for (int i = 0; i < riskProfileMst.size(); i++) {
				if (riskProfileMst.get(i).getRiskParameter().equals("ATM Withdrawal")) {
					String atmTransPercent = (riskProfileMst.get(i).getRiskValue());
					if (atmWithdrawarPercent > Double.parseDouble(atmTransPercent)) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else {
						riskProfileMst.get(i).setWeightage(0);
					}
				} else if (riskProfileMst.get(i).getRiskParameter().equals("Inward Clearing")) {
					String inwardClgTransPercent = (riskProfileMst.get(i).getRiskValue());
					if (inwardClgPercent > Double.parseDouble(inwardClgTransPercent)) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else {
						riskProfileMst.get(i).setWeightage(0);
					}
				} else if (riskProfileMst.get(i).getRiskParameter().equals("Cash Deposits")) {
					String cashDepoTransPercent = (riskProfileMst.get(i).getRiskValue());
					if (cashDepositsPercent > Double.parseDouble(cashDepoTransPercent)) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else {
						riskProfileMst.get(i).setWeightage(0);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return riskProfileMst;
	}

	@Override
	public List<RiskProfileStatus> getRiskGraphData(String custCode, String branchCode) {
		List<RiskProfileStatus> riskProfileStatus = new ArrayList<RiskProfileStatus>();
		try {
			return riskProfileStatus = riskProfilestatusrepository.getRiskStatus(branchCode, custCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RiskWeightageMst> getRiskWightageData(String bankCode) {
		List<RiskWeightageMst> riskWeightageMst = new ArrayList<RiskWeightageMst>();
		try {
			return riskWeightageMst = riskWeightageMstRepository.getWeightage(bankCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RiskProfileStatus> getRiskMovement(String custCode, String fromDate, String toDate) {
		List<RiskProfileStatus> riskProfileStatus = new ArrayList<RiskProfileStatus>();
		try {
			return riskProfileStatus = riskProfilestatusrepository.getRiskMovement(custCode, fromDate, toDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateRiskScore(String custCode, String branchCode) {

		List<RiskProfileStatus> riskProfileStatus = new ArrayList<RiskProfileStatus>();
		try {
			riskProfileStatus = riskProfilestatusrepository.getRiskStatus(branchCode, custCode);

			for (int i = 0; i < riskProfileStatus.size(); i++) {
				BigDecimal tempRiskScorevalue;
				BigDecimal riskScorevalue;

				BigDecimal divisor = new BigDecimal(4);

				tempRiskScorevalue = riskProfileStatus.get(i).getKycRisk()
						.add(riskProfileStatus.get(i).getTransactioTypeRisk()).add(riskProfileStatus.get(i)
								.getTransactiontrendRisk().add(riskProfileStatus.get(i).getViolationRisk()));

				riskScorevalue = tempRiskScorevalue.divide(divisor, 2, RoundingMode.CEILING);
				riskProfileStatus.get(i).setRiskScore(riskScorevalue);
				riskProfilestatusrepository.save(riskProfileStatus.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Risk Score updated";
	}

	@Override
	public String updateRiskProfile(String baseBrCode) {
		List<RiskProfileMst> riskProfileMst = new ArrayList<RiskProfileMst>();
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String countryOfOrigin = null;
		String customerType = null;
		String industry = null;
		String nationality = null;
		String qualification = null;
		String incomeSource = null;
		String occupation = null;
		String isPEP = null;
		Date currentDate = new Date();

		try {
			String branchCode = baseBrCode;

			List<Customer> customer = customerRpository.findBybaseBrCode(baseBrCode);

//			List<Transaction> transaction = transactionRpository.getCustATMWithdrawlCount();

//			riskProfileMst = riskProfileMstepository.getKYCParametersByBranchCust(branchCode);

			List<Transaction> transaction1 = new ArrayList<Transaction>();

			List<RiskProfileStatus> riskProfileStatus = riskProfilestatusrepository
					.getRiskStatusByBranchCode(branchCode);

			List<RiskProfileStatus> riskProfileStatus2 = new ArrayList<RiskProfileStatus>();
			for (int j = 0; j < customer.size(); j++) {

				String customerCode = null;
				int transactionCount = 0;
				Double atmWithdrawarPercent = 0.0;
				Double inwardClgPercent = 0.0;
				Double cashDepositsPercent = 0.0;
				int atmTransCount = 0;
				int inwardClgTransCount = 0;
				int cashDepositsTransCount = 0;
				Double transactionRiskvalue = 0.0;
				Double kycRiskvalue = 0.0;

				countryOfOrigin = customer.get(j).getCountryOfReg();
				customerType = customer.get(j).getCustomerType();
				industry = customer.get(j).getIndustryType();

				if (customer.get(j).getNationality().equals("IN")) {
					nationality = "Indian";
				}
				qualification = customer.get(j).getQualification();

				if (customer.get(j).getIsSelfEmployedYN() != null) {
					if (customer.get(j).getIsSelfEmployedYN().equals(0)) {
						incomeSource = "Self-Employed";
					}
				}

				occupation = customer.get(j).getTypeOfBusiness();
				if (customer.get(j).getIsPEP().equals(1)) {
					isPEP = "No";
				}

				String custCode = customer.get(j).getCustCode();

				int d1 = Integer.parseInt(formatter.format(customer.get(j).getCustomerDOB()));
				int d2 = Integer.parseInt(formatter.format(currentDate));
				int age = (d2 - d1) / 10000;

				riskProfileMst = riskProfileMstepository.getKYCParameters(branchCode, countryOfOrigin, customerType,
						industry, nationality, occupation, qualification, incomeSource, isPEP);

//			riskProfileMst = riskProfileMstepository.getKYCParametersByBranchCust(branchCode);
				RiskProfileStatus riskProfileStatus1 = new RiskProfileStatus();
//			riskProfileStatus = riskProfilestatusrepository.getRiskStatus(branchCode, custCode);

				for (int i = 0; i < riskProfileMst.size(); i++) {
					if (riskProfileMst.get(i).getRiskParameter().equals("Country of Origin")
							&& riskProfileMst.get(i).getRiskValue().equals(countryOfOrigin)) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Customer Type")
							&& riskProfileMst.get(i).getRiskValue().equals(customerType))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Industry")
							&& riskProfileMst.get(i).getRiskValue().equals(industry))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Nationality")
							&& riskProfileMst.get(i).getRiskValue().equals(nationality))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Qualification")
							&& riskProfileMst.get(i).getRiskValue().equals(qualification))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Age Group")
							&& (age >= 20 && age <= 25))) {
						riskProfileMst.get(i).setRiskValue("20-25yrs");
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Income Source")
							&& riskProfileMst.get(i).getRiskValue().equals(incomeSource))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("Occupation")
							&& riskProfileMst.get(i).getRiskValue().equals(occupation))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					} else if ((riskProfileMst.get(i).getRiskParameter().equals("IS PEP")
							&& riskProfileMst.get(i).getRiskValue().equals(isPEP))) {
						riskProfileMst.get(i).setWeightage(riskProfileMst.get(i).getWeightage());
					}
//				else {
//					riskProfileMst.get(i).setWeightage(0);
//				}

					kycRiskvalue = kycRiskvalue + riskProfileMst.get(i).getWeightage();
					System.out.println("KYC Risk Score is " + kycRiskvalue + " " + customer.get(j).getCustCode());

				}
				List<Transaction> transaction = transactionRpository.getATMWithdrawlCount(custCode);

				for (int k = 0; k < transaction.size(); k++) {
					if (customer.get(j).getCustCode().equals(transaction.get(k).getCustCode())) {
						transactionCount = transactionCount + 1;
						customerCode = customer.get(j).getCustCode();
						if (transaction.get(k).getTxnType().equals("ATM")
								&& transaction.get(k).getCashflowType().equals("DR")) {
							atmTransCount = atmTransCount + 1;

						} else if (transaction.get(k).getTxnType().equals("Inward Clearing")
								&& transaction.get(k).getCashflowType().equals("DR")) {
							inwardClgTransCount = inwardClgTransCount + 1;

						} else if (transaction.get(k).getTxnType().equals("Cash Deposits")
								&& transaction.get(k).getCashflowType().equals("DR")) {
							cashDepositsTransCount = cashDepositsTransCount + 1;

						}
					}
				}

				if (customerCode != null) {

					atmWithdrawarPercent = (double) ((atmTransCount * 100) / transactionCount);
					inwardClgPercent = (double) ((inwardClgTransCount * 100) / transactionCount);
					cashDepositsPercent = (double) ((cashDepositsTransCount * 100) / transactionCount);

				}

				List<RiskProfileMst> riskProfileMst1 = riskProfileMstepository.getTransactionRiskParameters(branchCode);

				for (int l = 0; l < riskProfileMst1.size(); l++) {
					if (riskProfileMst1.get(l).getRiskParameter().equals("ATM Withdrawal")) {
						String atmTransPercent = (riskProfileMst1.get(l).getRiskValue());
						if (atmWithdrawarPercent > Double.parseDouble(atmTransPercent)) {
							riskProfileMst1.get(l).setWeightage(riskProfileMst1.get(l).getWeightage());
						} else {
							riskProfileMst1.get(l).setWeightage(0);
						}
					} else if (riskProfileMst1.get(l).getRiskParameter().equals("Inward Clearing")) {
						String inwardClgTransPercent = (riskProfileMst1.get(l).getRiskValue());
						if (inwardClgPercent > Double.parseDouble(inwardClgTransPercent)) {
							riskProfileMst1.get(l).setWeightage(riskProfileMst1.get(l).getWeightage());
						} else {
							riskProfileMst1.get(l).setWeightage(0);
						}
					} else if (riskProfileMst1.get(l).getRiskParameter().equals("Cash Deposits")) {
						String cashDepoTransPercent = (riskProfileMst1.get(l).getRiskValue());
						if (cashDepositsPercent > Double.parseDouble(cashDepoTransPercent)) {
							riskProfileMst1.get(l).setWeightage(riskProfileMst1.get(l).getWeightage());
						} else {
							riskProfileMst1.get(l).setWeightage(0);
						}
					}
					transactionRiskvalue = transactionRiskvalue + riskProfileMst1.get(l).getWeightage();
				}

				riskProfileStatus1.setBankCode(customer.get(j).getBankCode());
				riskProfileStatus1.setBranchCode(customer.get(j).getBaseBrCode());
				riskProfileStatus1.setCustCode(customer.get(j).getCustCode());
				riskProfileStatus1.setTransactioTypeRisk(new BigDecimal(transactionRiskvalue));
				riskProfileStatus1.setKycRisk(new BigDecimal(kycRiskvalue));
				riskProfileStatus1.setTransactiontrendRisk(new BigDecimal(0.0));
				riskProfileStatus1.setViolationRisk(new BigDecimal(0.0));
				riskProfileStatus1.setCreatedDate(new Date());
				riskProfileStatus1.setAuthStatus(1);
				riskProfileStatus1.setIsActive(1);
				riskProfileStatus1.setVersion(1);

				BigDecimal tempRiskScorevalue;
				BigDecimal riskScorevalue;

				BigDecimal divisor = new BigDecimal(4);

				tempRiskScorevalue = riskProfileStatus1.getKycRisk().add(riskProfileStatus1.getTransactioTypeRisk())
						.add(riskProfileStatus1.getTransactiontrendRisk().add(riskProfileStatus1.getViolationRisk()));

				riskScorevalue = tempRiskScorevalue.divide(divisor, 2, RoundingMode.CEILING);
				riskProfileStatus1.setRiskScore(riskScorevalue);
				riskProfilestatusrepository.saveRiskProfileStatus(riskProfileStatus1.getBranchCode(),
						riskProfileStatus1.getBankCode(), riskProfileStatus1.getCreatedDate(),
						riskProfileStatus1.getIsActive(), riskProfileStatus1.getVersion(),
						riskProfileStatus1.getCustCode(), riskProfileStatus1.getKycRisk(),
						riskProfileStatus1.getTransactioTypeRisk(), riskProfileStatus1.getTransactiontrendRisk(),
						riskProfileStatus1.getViolationRisk(), riskProfileStatus1.getRiskScore(),
						riskProfileStatus1.getAuthStatus());
//			riskProfileStatus2.add(riskProfileStatus1);
			}

//			 riskProfilestatusrepository.saveAll(riskProfileStatus2);

//			 for(int k=0; k<riskProfileStatus.size(); k++) {
//			   riskProfileStatus.set
//				 riskProfileStatus.get(k).setKycRisk(new BigDecimal(kycRiskvalue));

//			 }
//			riskProfilestatusrepository.saveAll(riskProfileStatus);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return "Risk updated";
	}

	@Override
	public String updateRiskWeightage(Header header, List<RiskWeightageMst> riskWeightageMst) {
		try {
			Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
			for (int i = 0; i < riskWeightageMst.size(); i++) {
				RiskWeightageMst existingRiskWeightageMst = riskWeightageMstRepository
						.findById(riskWeightageMst.get(i).getId()).orElse(null);
				existingRiskWeightageMst.setWeightage(riskWeightageMst.get(i).getWeightage());
				existingRiskWeightageMst.setModifiedDate(currentDate);

				riskWeightageMstRepository.save(existingRiskWeightageMst);
			}
			return "Risk Weightage Updated";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void saveAuxilaryInfo(List<AuxilaryInfo> profile) {
		for (AuxilaryInfo auxilaryInfo : profile) {
			auxilaryInfoRepository.save(auxilaryInfo);
		}

	}

}
