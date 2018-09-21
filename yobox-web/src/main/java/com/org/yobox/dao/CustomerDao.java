package com.org.yobox.dao;

import java.util.Collection;

import com.org.yobox.AppDTO;
import com.org.yobox.model.Customer;
import com.org.yobox.model.PasswordRequestHistory;

public interface CustomerDao extends BaseDao {
	
	public AppDTO customerSignIn(AppDTO appDTO);

	public Customer getCustomerById(int id);
	
	public Customer getCustomerByUserId(String userId);
	
	public void addPasswordResetToken(PasswordRequestHistory passwordRequestHistory);
	
	public PasswordRequestHistory getPasswordRequestHistoryByToken(String token);

	public boolean isTokenExists(String token);
	
	public void resetAllPasswordToken(Customer customer);
	
	public boolean isEmailIdExistWithOtherAccount(String email,int customerId);
	
	
	public boolean isPhoneExistWithOtherAccount(String phone,int customerId);
	
}