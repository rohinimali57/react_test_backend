
package com.nellinfotech.aml.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nellinfotech.aml.entities.Alert;
import com.nellinfotech.aml.entities.Case;

public interface CaseRepository extends JpaRepository<Case, Long> {
	// public static final EntityManager em = null;

	@Query(value = " SELECT * FROM cases order by id desc limit 1", nativeQuery = true)
	List<Case> getLastRecord();

	@Query(value = " SELECT * FROM cases where case_status !='Closed' and bank_code=:bankCode and alert_code=:alertType and (created_date BETWEEN :fromDate and :toDate)", nativeQuery = true)
	List<Case> getCaseList(String bankCode, String toDate, String fromDate, String alertType);

	// @Query(value = " SELECT * FROM cases where case_status !='Closed' and
	// bank_code=:bankCode and alert_code=:alertType and (created_date BETWEEN
	// :toDate and :fromDate)", nativeQuery = true)
	@Query(value = " SELECT * "
			+ " FROM cases c WHERE c.bank_code=:bankCode and c.case_status !='Close' and (c.created_date BETWEEN :fromDate and :toDate) "
			+ " and (c.alert_code =:alertType) and (c.alertsubtype_code =:alertSubCode ) "
			+ " and (c.CUSTOMER_RISK_LEVEL =:customerRisk)", nativeQuery = true)
	List<Case> getDashboardCaseList(String bankCode, String toDate, String fromDate, String alertType,
			String alertSubCode, String customerRisk);

	@Query(value = " SELECT * FROM cases where bank_code=:bankCode and cust_code=:custCode", nativeQuery = true)
	List<Case> findByCustCode(String bankCode, String custCode);
	
	@Query(value = " SELECT * FROM cases where bank_code=:bankCode", nativeQuery = true)
	List<Case> findAllCases(String bankCode);

	@Query(value = " SELECT * FROM cases where bank_code=:bankCode and case_id=:caseID", nativeQuery = true)
	List<Case> findByCaseID(String bankCode, String caseID);

//   	@Query(value = " SELECT * FROM cases where bank_code=:bankCode and case_id=:caseID", nativeQuery = true)
//	List<Case> getConfirmedCaseList(String bankCode, String toDate, String fromDate, String operator);

	@Query(value = "SELECT * FROM cases  WHERE (created_date between :fromDate and :toDate) and bank_code=:bankCode and case_status =:status "
			+ "or (case_id =:caseID || created_by =:creatorName || cust_code =:customerNumber || customer_name =:customerName)", nativeQuery = true)
	List<Case> searchCase(String bankCode, String toDate, String fromDate, String caseID, String creatorName,
			String customerName, String customerNumber, String status);

	@Query(value = "SELECT * FROM cases  WHERE (created_date between :toDate and  :fromDate) and bank_code=:bankCode and confirmed_fraud='Yes'", nativeQuery = true)
	List<Case> getConfirmedFraudList(String bankCode, String toDate, String fromDate);

//	@Query(value = "select * from cases where bank_code=:bankCode and case_id=:caseID", nativeQuery = true)
	@Query(value = "select * from cases where bank_code=:bankCode and id=:caseId", nativeQuery = true)
	List<Case> getCase(String bankCode, String caseId);

	@Transactional
	@Modifying
	@Query(value = "Update cases set description=:remark, case_status='Reject' where bank_code=:bankCode and id=:caseId", nativeQuery = true)
	Integer rejectAlert(String bankCode, String remark, Long caseId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE cases SET case_status = 'Cancelled' WHERE bank_code = :bankCode AND id = :caseId", nativeQuery = true)
	Integer cancelCase(String bankCode, Long caseId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE cases SET case_status = 'Inprocess' WHERE bank_code = :bankCode AND id = :caseId", nativeQuery = true)
	Integer ecalateCase(String bankCode, Long caseId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE cases SET confirmedCase = 'Yes' WHERE bank_code = :bankCode AND id = :caseId", nativeQuery = true)
	Integer confirmedCase(String bankCode, Long caseId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE cases SET case_status = 'Escalated', assigned_to =:assignedTo WHERE bank_code = :bankCode AND case_id = :caseID", nativeQuery = true)
	Integer escalateCase(String assignedTo, String bankCode, String caseID);

	@Query(value = "SELECT *" + "FROM cases " + "WHERE bank_code = :bankCode "
			+ "AND created_date BETWEEN :fromDate AND :toDate " + "AND confirmed_case = 'Yes' "
			+ "AND atm > :minTxnAmt", nativeQuery = true)
	List<Case> getConfirmedCaseReport(String bankCode, String toDate, String fromDate, Double minTxnAmt);
 
	@Query(value = "SELECT * FROM cases " + "WHERE bank_code = :bankCode " + "AND alert_code = :alertCode "
			+ "AND created_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
	List<Case> getCaseListing(String bankCode, String toDate, String fromDate, String alertCode);
//   	static List<Case> getDashboardCaseList(String bankCode, String toDate, String fromDate, String alertType,
//			String alertSubCode, String customerRisk) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Case> cq = cb.createQuery(Case.class);
//
//        Root<Case> cases = cq.from(Case.class);
//        Predicate bankCodePredicate = cb.equal(cases.get("bank_code"), bankCode);
//        //Predicate titlePredicate = cb.like(Case.get("title"), "%" + title + "%");
//        cq.where(bankCodePredicate);
//
//        TypedQuery<Case> query = em.createQuery(cq);
//        return query.getResultList();
//    }

}
