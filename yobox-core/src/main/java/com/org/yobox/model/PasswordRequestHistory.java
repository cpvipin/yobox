package com.org.yobox.model;

import java.util.Date;

import com.org.yobox.model.base.AbstractModel;
import com.org.yobox.model.base.BaseModel;

/**
* @hibernate.class table="customer"    
* 
@hibernate.query name="passHist.getpasswordRequestHistoryByToken" 
query="from PasswordRequestHistory as passHist  where passHist.token=:token"  

@hibernate.query name="passHist.isTokenExists" 
query="select count(passHist.id) from PasswordRequestHistory as passHist where passHist.token =:token"  

*/

public class PasswordRequestHistory extends AbstractModel {

	private Integer id;
	private Customer customer;
	private String token;
	private String requestedUserId;
	private boolean activeStatus;
	
	
	
	/* getters and setters*/
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRequestedUserId() {
		return requestedUserId;
	}
	public void setRequestedUserId(String requestedUserId) {
		this.requestedUserId = requestedUserId;
	}
	public boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

}
