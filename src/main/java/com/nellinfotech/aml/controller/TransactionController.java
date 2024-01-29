package com.nellinfotech.aml.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nellinfotech.aml.commonUtility.HeaderValidator;
import com.nellinfotech.aml.dto.TransactionProfileModel;
//import com.nellinfotech.aml.entities.Customer;
import com.nellinfotech.aml.entities.Transaction;
import com.nellinfotech.aml.model.Header;
import com.nellinfotech.aml.service.TransactionService;


/**
 * @author Paresh K
 */
@CrossOrigin(origins = "*")
@RestController
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;
    
    Logger logger = LoggerFactory.getLogger(TransactionController.class);
      /**
     * @author Paresh K
     * @param 
     */
    @PostMapping("/getTransactionDetails")
    public Transaction getTransactionDetails(@RequestParam String txnNumber, @RequestParam String custCode) {
        try {
            
            return transactionService.getTransactionDetails(txnNumber,custCode);
        } catch (Exception e) {
            logger.error("Error in customer controller" + e);
        }
        return null;
    }
    
    /**
 * @author Paresh K
 * @param 
 */
@PostMapping("/findByTransactionParam")
public List<Transaction> findByTransactionParam(@RequestParam String acctNo, @RequestParam String custCode, @RequestParam String cashflowType, @RequestParam String transactionAmount,
		@RequestParam String toDate, @RequestParam String fromDate, @RequestParam String operator) {
		
		BigDecimal txnAmount=new BigDecimal(transactionAmount);
	
    try {
    	System.out.println("step1");
        return transactionService.findByTransactionParam(acctNo, custCode, cashflowType, txnAmount, toDate, fromDate, operator);
        		//transaction.getTxnAmount());
        		//, transaction.getTxnDtTm());
      
    } catch (Exception e) {
        logger.error("Error in Transaction controller -  findByTransactionParam" + e);
    }
    return null;
}

/**
* @author Paresh K
* @param 
*/
@PostMapping("/getTransactionByInterval")
public List<Transaction> getTransactionByInterval(@RequestParam String acctNo, @RequestParam String custCode, @RequestParam String interval) {

try {
	
    return transactionService.getTransactionByInterval(acctNo, custCode, interval);
    		  
} catch (Exception e) {
    logger.error("Error in Transaction controller -  findByTransactionParam" + e);
}
return null;
}

@PostMapping("/getTransactionProfile")
public List<TransactionProfileModel> getTransactionProfile(@RequestHeader HttpHeaders httpHeaders, @RequestParam String acctNo, @RequestParam String cashflow, @RequestParam String fromDate ,
		@RequestParam String toDate, @RequestParam String transactionAmount, @RequestParam String operator) 
{
    try 
    {
    	Header header = HeaderValidator.headerConversion(httpHeaders);
		String bankCode=header.getBankCode();
        return transactionService.getTransactionProfile(bankCode, acctNo, cashflow, fromDate, toDate, transactionAmount, operator);
      
    } catch (Exception e) {
        logger.error("Error in Transaction controller -  getTransactionProfile" + e);
    }
    return null;
}

}
