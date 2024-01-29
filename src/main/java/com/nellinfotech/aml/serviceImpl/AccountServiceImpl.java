package com.nellinfotech.aml.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nellinfotech.aml.commonUtility.DateValidator;
import com.nellinfotech.aml.dto.AccountProfileModel;
import com.nellinfotech.aml.entities.Account;
import com.nellinfotech.aml.repository.AccountRepository;
import com.nellinfotech.aml.service.AccountService;

/**
 * @author Paresh K
 */

@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountRepository accountRpository;
    
        @Autowired
    DateValidator dateValidator;

        /**
         * @author Paresh K
         * @param 
         */
		@Override
		public List<Account> getAccountNumber(String custCode) {
			try {
				return accountRpository.findByCustCode(custCode);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public List<Account> getAccountNumberByBank(String bankCode) {
			try {
				return accountRpository.findByBankCode(bankCode);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public List<AccountProfileModel> getAccountProfile(String bankCode, String custCode) 
		{
			List<AccountProfileModel> accProfileList = new ArrayList<>();
			try 
			{
				List<Account> accountEntityList = accountRpository.findByBankCodeAndCustCode(bankCode, custCode);
				if(accountEntityList!=null && !accountEntityList.isEmpty())
				{
					AccountProfileModel accountProfileModel;
					for(Account acc : accountEntityList)
					{
						accountProfileModel = new AccountProfileModel();
						accountProfileModel.setAccNumber(acc.getAcctNo());
						accountProfileModel.setName("");
						accountProfileModel.setType(acc.getAcctType());
						accountProfileModel.setBranch("");
						accountProfileModel.setStatus(acc.getStatus());
						accountProfileModel.setOpenDate(acc.getAcctClosedDt());
						accountProfileModel.setOperationMode(String.valueOf(acc.getAcctOprnMode()));
						if(acc.getAcctHasNominee()==1)
						{
							accountProfileModel.setNomineeYn("Y");
						}
						else if(acc.getAcctHasNominee()==0) 
						{
							accountProfileModel.setNomineeYn("N");
						}
						accProfileList.add(accountProfileModel);
					}
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return accProfileList;
		}

		@Override
		public List<Account> getCustAccount(String custCode) {
			try {
				return accountRpository.findByCustCode(custCode);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
        
        

//		@Override
//		public Customer getCustomer(String custCode) {
//			try {
//				return customerRpository.findByCustCode(custCode);
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		public List<Customer> findByCustomerParam(String customerType, String custCode, String customerName, String baseBrCode,
//				String countryOfReg, String customerCategory, String nationalId1, String addr1, String city,
//				String postalCode, String mobileNo, Double riskManual) {
//			try {
//				return customerRpository.findByCustomerParam(customerType, custCode, customerName, baseBrCode, countryOfReg, customerCategory, nationalId1, addr1,
//						city, postalCode, mobileNo, riskManual);
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
    
    
        
}
