package com.org.yobox.model;

import java.util.Date;

import com.org.yobox.model.base.AbstractModel;


/**
* @hibernate.class table="customer"    
* 
@hibernate.query name="cust.authenticateByUserIdPassword" 
query="from Customer as cust  where (cust.EmailId=:emailId or cust.phone=:phone) and password=:password 
"                  
*
*@hibernate.query name="cust.getCustomerByUserId" 
query="from Customer as cust  where (cust.EmailId=:emailId or cust.phone=:phone)"

*@hibernate.query name="cust.isEmailExistWithOtherAccount" 
query="select count(cust.id) from Customer as cust where cust.email=:emai  and cust.id!=:customerId"


*@hibernate.query name="cust.isPhoneExistWithOtherAccount" 
query="select count(cust.id) from Customer as cust where cust.phone=:phone and cust.id!=:customerId"






*/
public class Customer extends AbstractModel {

	private Integer id;
	private String fullName;
	private int gender;
	private String email;	
	private String phone;	
	private String password;	
	private boolean activeStatus;
	private String beautyPreference;
	
	/*getters and setters*/
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	
	public String getBeautyPreference() {
		return beautyPreference;
	}
	public void setBeautyPreference(String beautyPreference) {
		this.beautyPreference = beautyPreference;
	}
	
	
}
