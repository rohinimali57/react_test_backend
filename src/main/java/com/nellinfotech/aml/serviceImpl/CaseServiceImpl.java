package com.nellinfotech.aml.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nellinfotech.aml.commonUtility.DateValidator;
import com.nellinfotech.aml.constant.ResponseStatus;
import com.nellinfotech.aml.dto.AlertStatistics;
import com.nellinfotech.aml.dto.CaseInfo;
import com.nellinfotech.aml.dto.TemplateDTO;
import com.nellinfotech.aml.entities.Account;
import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.Case;
import com.nellinfotech.aml.model.Header;
import com.nellinfotech.aml.repository.AccountRepository;
import com.nellinfotech.aml.repository.AlertRepository;
import com.nellinfotech.aml.repository.CaseRepository;
import com.nellinfotech.aml.service.CaseService;

import io.netty.util.AsciiString;

/**
 * @author TUSHAR
 */

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	DateValidator dateValidator;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * @author TUSHAR
	 * @param holidayMst return
	 */

	@Override
	public Case saveCase(Header header, Case amlCase) {
		long caseNumber;
		String caseID = null;
		String pattern = "dd/MM/yyyy";
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
//            Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
			Date currentDate = new Date();

			String date = simpleDateFormat.format(currentDate);
			List<Case> getLastReord = new ArrayList<Case>();
			getLastReord = caseRepository.getLastRecord();
			if (getLastReord.isEmpty())
				caseNumber = 0;
			else
				caseNumber = getLastReord.get(0).getId();
			caseNumber = caseNumber + 1;

			caseID = date.toString() + "/" + caseNumber;

			amlCase.setCaseID(caseID);
			amlCase.setBankCode(amlCase.getBankCode());
			if (header != null)
				amlCase.setCreatedBy(header.getUserId());
			amlCase.setCreatedDate(currentDate);
			amlCase.setCaseStatus("Created");
			return caseRepository.save(amlCase);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<Case> getCaseList(String bankCode, String toDate, String fromDate, String alertType) {
		try {

			return caseRepository.getCaseList(bankCode, toDate, fromDate, alertType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Case> getDashboardCaseList(String bankCode, String toDate, String fromDate, String alertType,
			String alertSubCode, String customerRisk) {
		try {

			return caseRepository.getDashboardCaseList(bankCode, toDate, fromDate, alertType, alertSubCode,
					customerRisk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Case> getCaseListByCustomerID(String bankCode, String custCode) {
		try {
			return caseRepository.findByCustCode(bankCode, custCode);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Case> findAllCases(String bankCode) {
		try {
			return caseRepository.findAllCases(bankCode);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Case> getCaseListByCaseID(String bankCode, String caseID) {
		try {
			return caseRepository.findByCaseID(bankCode, caseID);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Case> getConfirmedFraudList(String bankCode, String toDate, String fromDate, String operator,
			String amount) {
		try {

			// return caseRepository.getConfirmedFraudList(bankCode, toDate, fromDate,
			// operator, amount);
			return caseRepository.getConfirmedFraudList(bankCode, toDate, fromDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Case> searchCase(String bankCode, String toDate, String fromDate, String caseID, String creatorName,
			String customerName, String customerNumber, String status) {

		System.out.println("ToDate " + toDate);
		System.out.println("fromDate " + fromDate);
		try {

			return caseRepository.searchCase(bankCode, toDate, fromDate, caseID, creatorName, customerName,
					customerNumber, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Case> updateCaseStatus(Header header, List<Case> amlCase) {
		try {
			for (int i = 0; i < amlCase.size(); i++) {
				Case existingCase = caseRepository.findById(amlCase.get(i).getId()).orElse(null);
				// Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
				existingCase.setCaseStatus(amlCase.get(i).getCaseStatus());
//				existingCase.setModifiedBy(header.getUserId());
//				existingCase.setCaseID(amlCase.get(i).getCaseID());
				existingCase.setReportTYpe(amlCase.get(i).getReportTYpe());
				existingCase.setCaseSeverity(amlCase.get(i).getCaseSeverity());
				existingCase.setClassification(amlCase.get(i).getClassification());
				existingCase.setDescription(amlCase.get(i).getDescription());
				existingCase.setRecordsReferred(amlCase.get(i).getRecordsReferred());
				
				existingCase.setMoneyLaundering(amlCase.get(i).getMoneyLaundering());
				existingCase.setStructuring(amlCase.get(i).getStructuring());
				existingCase.setTerroristFinancing(amlCase.get(i).getTerroristFinancing());
				existingCase.setOtherActivity(amlCase.get(i).getOtherActivity());
				
				// Assuming existingCase and amlCase are instances of the Case class

				existingCase.setInternet(amlCase.get(i).getInternet());
				existingCase.setMobile(amlCase.get(i).getMobile());
				existingCase.setIvr(amlCase.get(i).getIvr());
				existingCase.setPos(amlCase.get(i).getPos());
				existingCase.setInstrument(amlCase.get(i).getInstrument());
				existingCase.setTeller(amlCase.get(i).getTeller());
				existingCase.setAtm(amlCase.get(i).getAtm());
				existingCase.setOtherChannel(amlCase.get(i).getOtherChannel());
				
				
				existingCase.setMultipleViolations(amlCase.get(i).getMultipleViolations());
				existingCase.setSuspiciousCustomer(amlCase.get(i).getSuspiciousCustomer());
				existingCase.setHabitualOffender(amlCase.get(i).getHabitualOffender());
				existingCase.setFirstTimer(amlCase.get(i).getFirstTimer());
				existingCase.setSuspiciousLinks(amlCase.get(i).getSuspiciousLinks());
				existingCase.setWatchlistIdetified(amlCase.get(i).getWatchlistIdetified());
				existingCase.setAssociateLinks(amlCase.get(i).getAssociateLinks());
				existingCase.setUnusalHighVolumeAlert(amlCase.get(i).getUnusalHighVolumeAlert());
				existingCase.setUnusalHighCaseeAlert(amlCase.get(i).getUnusalHighCaseeAlert());
				existingCase.setReestablishedIdentified(amlCase.get(i).getReestablishedIdentified());
				existingCase.setHighRiskPeerGroup(amlCase.get(i).getHighRiskPeerGroup());
				existingCase.setPoliticallyExposed(amlCase.get(i).getPoliticallyExposed());
				
				existingCase.setComments(amlCase.get(i).getComments());
				existingCase.setFileLink(amlCase.get(i).getFileLink());
				existingCase.setAssignedTo(amlCase.get(i).getAssignedTo());

				
				// existingCase.setModifiedDate(currentDate);
				caseRepository.save(existingCase);
			}
			return amlCase;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Case> updateConfirmFraud(Header header, List<Case> amlCase) {
		try {
			for (int i = 0; i < amlCase.size(); i++) {
				Case existingCase = caseRepository.findById(amlCase.get(i).getId()).orElse(null);
//	           Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
				existingCase.setConfirmedCase(amlCase.get(i).getConfirmedCase());
//	            existingCase.setModifiedDate(currentDate);
				existingCase.setModifiedBy(header.getUserId());
				caseRepository.save(existingCase);
			}
			return amlCase;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Case> getCase(String bankCode, String caseID) {
		try {

			return caseRepository.getCase(bankCode, caseID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author TUSHAR
	 * @param branchHolidayMap return
	 */
//    @Override
//    public BranchHolidayMap saveBranchHoliday(Header header, BranchHolidayMap branchHolidayMap) {
//        
//        try {
//            Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
//            branchHolidayMap.setCreatedBy(header.getUserId());
//            branchHolidayMap.setCreatedDate(currentDate);
//            branchHolidayMap.setBankCode(header.getBankCode());
//            return branchHolidayRpository.save(branchHolidayMap);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return null;
//    }
//    
//    /**
//     * @author TUSHAR
//     * @param brCode
//     *            return
//     */
//    @Override
//    public List<BranchHolidayMap> getBranchHollidayList(String brCode) {
//        try {
//            Integer isactive = 1;
//            return branchHolidayRpository.findBybrCode(brCode, isactive);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return null;
//    }
//    
//    /**
//     * @author TUSHAR
//     * @param branchCode
//     *            return
//     */
//    @Override
//    public HolidayMst getHolidaylist(String branchCode) {
//        try {
//            return holidayRpository.findByBranchCode(branchCode);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return null;
//    }
//    
//    /**
//     * @author TUSHAR
//     * @param holidayMst
//     *            return
//     */
//    @Override
//    public HolidayMst updateHolidayMaster(Header header, HolidayMst holidayMst) {
//        try {
//            HolidayMst existingHolidayMst = holidayRpository.findById(holidayMst.getId()).orElse(null);
//            Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
//            existingHolidayMst.setModifiedBy(header.getUserId());
//            existingHolidayMst.setModifiedDate(currentDate);
//            existingHolidayMst.setBranchCode(holidayMst.getBankCode());
//            existingHolidayMst.setHolidayCode(holidayMst.getHolidayCode());
//            existingHolidayMst.setHolidayDesc(holidayMst.getHolidayDesc());
//            existingHolidayMst.setHolidayDate(holidayMst.getHolidayDate());
//            existingHolidayMst.setBranchCode(holidayMst.getBranchCode());
//            existingHolidayMst.setBankCode(holidayMst.getBankCode());
//            return holidayRpository.save(existingHolidayMst);
//            
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return null;
//    }
//    
//    /**
//     * @author TUSHAR
//     * @param id
//     *            return
//     */
//    @Override
//    public String deleteHolidayMst(Long id) {
//        try {
//            HolidayMst existingHolidayMst = holidayRpository.findById(id).orElse(null);
//            existingHolidayMst.setIsActive(1);
//            holidayRpository.save(existingHolidayMst);
//            return ResponseStatus.SUCCESS;
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return null;
//    }

	@Override
	public List<AlertStatistics> alertStatistics(Header header) {
		List<AlertStatistics> lst = new ArrayList<AlertStatistics>();
		List<TemplateDTO> obj = new ArrayList<TemplateDTO>();
		List<TemplateDTO> obj1 = new ArrayList<TemplateDTO>();
		List<TemplateDTO> obj2 = new ArrayList<TemplateDTO>();
		try {
			obj = jdbcTemplate.query(
					"select alert.alertsubtype_code as name,COUNT(alert.alertsubtype_code) as totalCount from alert where bank_code=?  group by alertsubtype_code ",
					new Object[] { header.getBankCode() }, new BeanPropertyRowMapper<TemplateDTO>(TemplateDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			obj1 = jdbcTemplate.query(
					"select alertsubtype_code as name,count(created_date) as totalCount from alert where bank_code=? and CURDATE()=date(created_date) group by alertsubtype_code ",
					new Object[] { header.getBankCode() }, new BeanPropertyRowMapper<TemplateDTO>(TemplateDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			obj2 = jdbcTemplate.query(
					"select alertsubtype_code as name,count(created_date)as totalCount from alert where bank_code=? and CURDATE()>created_date group by alertsubtype_code ",
					new Object[] { header.getBankCode() }, new BeanPropertyRowMapper<TemplateDTO>(TemplateDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (obj.size() > 0) {
			for (TemplateDTO templateDTO : obj) {
				AlertStatistics stat = new AlertStatistics();
				if (obj1.size() > 0) {
					for (TemplateDTO templateDTO1 : obj1) {

						if (templateDTO.getName().contentEquals(templateDTO1.getName())) {
							stat.setAlertName(templateDTO.getName());
							stat.setTotalCount(templateDTO.getTotalCount());
							stat.setDayCount(templateDTO1.getTotalCount());

						}
					}
				}
				if (obj2.size() > 0) {
					for (TemplateDTO templateDTO2 : obj2) {

						if (templateDTO.getName().contentEquals(templateDTO2.getName())) {
							stat.setAlertName(templateDTO.getName());
							stat.setTotalCount(templateDTO.getTotalCount());
							stat.setPrevCount(templateDTO2.getTotalCount());
							;
						}
					}
				}
				lst.add(stat);
			}
		}
		return lst;
	}

	@Override
	public CaseInfo getCaseInfo(String bankCode, String caseId) {
		CaseInfo caseInfo = new CaseInfo();
		try {
			List<Case> caseList = caseRepository.getCase(bankCode, caseId);
			if (!caseList.isEmpty()) {
				Case caseObj = caseList.get(0); // Assuming caseList is a list of Case objects

				// Extracting values from caseObj and setting them in caseInfo
				caseInfo.setCaseId(caseObj.getCaseID());
				caseInfo.setCaseSeverity(caseObj.getCaseSeverity());
				caseInfo.setClassification(caseObj.getClassification());
				caseInfo.setDescription(caseObj.getDescription());
				caseInfo.setRecordsReferred(caseObj.getRecordsReferred());
				caseInfo.setReportTYpe(caseObj.getReportTYpe());

				// Convert boolean string values and set them in CaseInfo

				caseInfo.setMoneyLaunderingChecked(caseObj.getMoneyLaundering());
				caseInfo.setStructuringChecked(caseObj.getStructuring());
				caseInfo.setTerroristFinancingChecked(caseObj.getTerroristFinancing());
				caseInfo.setOtherSpecifyChecked(caseObj.getOtherActivity());

				caseInfo.setInternet(caseObj.getInternet());
				caseInfo.setMobile(caseObj.getMobile());
				caseInfo.setIvr(caseObj.getIvr());
				caseInfo.setPos(caseObj.getPos());
				caseInfo.setInstrument(caseObj.getInstrument());
				caseInfo.setTeller(caseObj.getTeller());
				caseInfo.setAtm(caseObj.getAtm());
				caseInfo.setOther(caseObj.getOtherChannel());

				caseInfo.setViolatingMultipleScenarios(caseObj.getMultipleViolations());
				caseInfo.setCustomerAssociatesLinks(caseObj.getAssociateLinks());
				caseInfo.setSuspiciousCustomer(caseObj.getSuspiciousCustomer());
				caseInfo.setHabitualOffender(caseObj.getHabitualOffender());
				caseInfo.setFirstTimeOffender(caseObj.getFirstTimer());
				caseInfo.setCustomerHasSuspiciousLinks(caseObj.getSuspiciousLinks());
				caseInfo.setIdentifiedInWatchlist(caseObj.getWatchlistIdetified());
				caseInfo.setHighRiskPeerGroups(caseObj.getHighRiskPeerGroup());
				caseInfo.setIsPoliticallyExposedPerson(caseObj.getPoliticallyExposed());

				// Set other string fields from caseObj to the respective fields in CaseInfo
				caseInfo.setComments(caseObj.getComments());
//				caseInfo.setFileLink(caseObj.getFileLink());
				caseInfo.setAssignedTo(caseObj.getAssignedTo());

				// Ensure to handle null values or validations as needed


				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caseInfo;
	}

	@Override
	public String rejectCase(String bankCode, String remark, Long caseId) {
		try {
			Integer alert = caseRepository.rejectAlert(bankCode, remark, caseId);
			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String cancelCase(String bankCode, Long caseId) {
		try {
			Integer alert = caseRepository.cancelCase(bankCode, caseId);
			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String confirmedCase(String bankCode, Long caseId) {
		try {
			Integer alert = caseRepository.cancelCase(bankCode, caseId);
			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String ecalateCase(String bankCode, Long caseId) {
		try {
			Integer alert = caseRepository.ecalateCase(bankCode, caseId);
			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String escalateCase(String bankCode, String caseId, String assignedTo) {
		try {
			Integer alert = caseRepository.escalateCase(assignedTo, bankCode, caseId);
			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseStatus.FAILED;
		}
	}

	@Override
	public String createCase(String bankCode, String alertId, String custNumber, String userId, String custName) {
		try {
			Alert a = alertRepository.getAlert(bankCode, Long.valueOf(alertId));

//			Account acc = accountRepository.findByAcctNo(a.getAccountNo());

			LocalDate localDate = LocalDate.now();

			// Convert LocalDate to Date
			Date date1 = java.util.Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = dateFormat.format(date1);
			Date date = null;
			try {
				date = dateFormat.parse(formattedDate);
			} catch (ParseException e) {
				e.printStackTrace(); // Handle the parsing exception accordingly
			}
			Case amlCase = new Case();
//			amlCase.setAccountId(acc.getId());
			amlCase.setAlertCode(a.getAlertCode());
			amlCase.setAlertID(alertId);
			amlCase.setAlertSubTypeCode(a.getAlertSubTypeCode());
			amlCase.setAssignedTo(a.getAssignedUserId());
			amlCase.setAssignedToName(a.getAssignedUser());
			amlCase.setAssociateLinks("");
			amlCase.setAtm(custNumber);
			amlCase.setAuthStatus(1);
			amlCase.setBankCode(bankCode);
			amlCase.setBranchCode(a.getBranchCode());
			amlCase.setCaseSeverity("");
			amlCase.setCaseStatus("created");
			amlCase.setClassification("");
			amlCase.setComments("");
			amlCase.setConfirmedCase("");
			amlCase.setConfirmedFraud("");
			amlCase.setCreatedBy(userId);
			amlCase.setCreatedByUserName(userId);
			amlCase.setCreatedDate(date);
			amlCase.setCustId(custNumber);
			amlCase.setCustomerName(custName);
			amlCase.setCustomerRiskLevel("");
			amlCase.setDescription(null);
			amlCase.setFileLink(null);
			amlCase.setFirstTimer(null);
			amlCase.setHabitualOffender(null);
			amlCase.setHighRiskPeerGroup(null);
			amlCase.setInstrument(null);
			amlCase.setInternet(null);
			amlCase.setIsActive(null);
			amlCase.setMobile(null);
			amlCase.setModifiedBy(null);
			amlCase.setModifiedDate(null);
			amlCase.setMoneyLaundering("");
			amlCase.setMultipleViolations("");
			amlCase.setOtherActivity("");
			amlCase.setOtherChannel("");
			amlCase.setPoliticallyExposed("");
			amlCase.setPos("");
			amlCase.setRecordsReferred("");
			amlCase.setReestablishedIdentified("");
			amlCase.setReportTYpe("");
			amlCase.setScenarioID("");
			amlCase.setStructuring("");
			amlCase.setSuspiciousCustomer("");
			amlCase.setSuspiciousLinks("");
			amlCase.setTeller("");
			amlCase.setTerroristFinancing("");
			amlCase.setUnusalHighCaseeAlert("");
			amlCase.setUnusalHighVolumeAlert("");
			amlCase.setVersion(null);
			amlCase.setWatchlistIdetified("");
			saveCase(null, amlCase);
//			caseRepository.save(amlCase);

			return ResponseStatus.SUCCESS;
		} catch (Exception e) {
			return ResponseStatus.FAILED;
		}
	}

	@Override
	public String rejectAlert(String bankCode, String remark, Long caseId) {
		// TODO Auto-generated method stub
		return null;
	}
}
