package com.nellinfotech.aml.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nellinfotech.aml.entities.UserMst;
import com.nellinfotech.aml.model.Header;

/**
 * @author ASHWIN
 */
public interface UserMstService {
    
    public UserMst saveUserMst(UserMst userMst);
    
    public UserMst getUserMstByUserId(String userId);
    
    public ResponseEntity userRegistration(Header header, UserMst user);
    
    public UserMst userLock(UserMst user);
    
    public UserMst getUserMstByBankCodeAndUserId(String bankCode, String userId);
    
    public List<UserMst> getUser(String bankCode);

	public UserMst deleteUser(Header header, UserMst user);

	public ResponseEntity updateUserRegistration(Header header, UserMst user);
    
}