package com.nellinfotech.aml.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nellinfotech.aml.commonUtility.DateValidator;
import com.nellinfotech.aml.entities.UserMst;
import com.nellinfotech.aml.model.Header;
import com.nellinfotech.aml.repository.UserMstRepository;
import com.nellinfotech.aml.service.UserMstService;

/**
 * @author ASHWIN
 */

@Service
public class UserMstServiceImpl implements UserMstService {
    
    @Autowired
    UserMstRepository userMstRepository;
    
    @Autowired
    DateValidator dateValidator;
    
    Logger logger=LoggerFactory.getLogger(UserMstServiceImpl.class);
    /**
     * @author ASHWIN
     * @param userMst
     */
    @Override
    public UserMst saveUserMst(UserMst userMst) {
        return userMstRepository.save(userMst);
    }
    
    /**
     * @author ASHWIN
     * @param userId
     */
    @Override
    public UserMst getUserMstByUserId(String userId) {
        return userMstRepository.findByUserId(userId);
    }
    
    @Override
    public ResponseEntity userRegistration(Header header, UserMst user) {
    	logger.info("inside UserMstServiceImpl: --userRegistration" );
    	 UserMst user1=userMstRepository.findByBankCodeAndUserId(header.getBankCode(), user.getUserId()).orElse(null);
     	if(user1!=null) {
     		return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
     	}
        try {
            Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
            user.setUserId(user.getUserId());
            user.setCreatedDate(currentDate);
            user.setBankCode(header.getBankCode());
            userMstRepository.save(user);
            logger.info("Return from UserMstServiceImpl: --userRegistration" );
            return new ResponseEntity<String>("Records Inserted", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
    
    /**
     * @author ASHWIN
     */
    @Override
    public UserMst getUserMstByBankCodeAndUserId(String bankCode, String userId) {
        return userMstRepository.findByBankCodeAndUserId(bankCode, userId).orElse(null);
    }
    
    @Override
    public List<UserMst> getUser(String bankCode) {
        // TODO Auto-generated method stub
    	Integer isActive=1;
        return userMstRepository.findAllByIsActiveAndBankCode(isActive,bankCode);
    }

	@Override
	public UserMst deleteUser(Header header, UserMst user) {
		try{
			UserMst existingUser = userMstRepository.findById(user.getId()).orElse(null);
        
        Date currentDate = dateValidator.getStrToDate(header.getCurrentDate());
        existingUser.setBankCode(header.getBankCode());
        existingUser.setModifiedBy(header.getUserId());
        existingUser.setModifiedDate(currentDate);
         existingUser.setIsActive(2);
        return userMstRepository.save(existingUser);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return null;

	}

	@Override
	public UserMst userLock(UserMst user) {
		try{
			UserMst existingUser = userMstRepository.findById(user.getId()).orElse(null);
        
        existingUser.setIsUserLocked(0);
        existingUser.setBadLoginCount(0);
       
        return userMstRepository.save(existingUser);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return null;

	}

	@Override
	public ResponseEntity updateUserRegistration(Header header, UserMst user) {
		String userId = user.getUserId();
		UserMst ExistingUser=null;
		try {
	ExistingUser=userMstRepository.findByBankCodeAndUserId(header.getBankCode(), user.getUserId()).orElseThrow(null);
//			if(ExistingUser==null) {
//				return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
//			}
		}
		catch (NullPointerException e) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>("Record Not Updated", HttpStatus.BAD_REQUEST);
		}
		ExistingUser.setCreatedBy(user.getCreatedBy());
		ExistingUser.setFirstName(user.getFirstName());
		ExistingUser.setBaseRoleCode(user.getBaseRoleCode());
		ExistingUser.setLastName(user.getLastName());
		ExistingUser.setBadLoginCount(user.getBadLoginCount());
		ExistingUser.setIsUserLocked(user.getIsUserLocked());
		ExistingUser.setIsLoginSuspended(user.getIsLoginSuspended());
		ExistingUser.setPwdChangeNextLogin(user.getPwdChangeNextLogin());
		ExistingUser.setPwdExpiryDate(user.getPwdExpiryDate());
		ExistingUser.setEmail(user.getEmail());
		ExistingUser.setMobileNo(user.getMobileNo());
		ExistingUser.setDivision(user.getDivision());
		ExistingUser.setRoleRiskSeverity(user.getRoleRiskSeverity());
		ExistingUser.setStatus(user.getStatus());
		ExistingUser.setBranch(user.getBranch());
		userMstRepository.save(ExistingUser);
		 return new ResponseEntity<String>("Records Updated", HttpStatus.OK);
	}
    
}