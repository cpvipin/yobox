package com.org.yobox.service.impl;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javassist.bytecode.Descriptor.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.dao.DashboardDao;
import com.org.yobox.model.Customer;
import com.org.yobox.service.DashboardService;
import com.org.yobox.util.CommonUtils;
import com.org.yobox.util.DateUtils;
import com.org.yobox.util.PasswordEncryptor;


public class DashboardServiceImpl extends BaseServiceImpl implements DashboardService {
	
	private DashboardDao dashboardDao;
	
	private CustomerDao customerDao;
	
	
	public AppDTO populateDashboardElements(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		Customer customer=(Customer) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		Collection customerSubsColl=dashboardDao.getCustomerSubscriptions(customer);
		Map beautyPreference=this.generateBeautyPreferenceResponse();
		dataMap.put("customer", customer);
		dataMap.put("beautyPreference",beautyPreference);
		dataMap.put("customerSubsColl",customerSubsColl);
		appDTO.setDataMap(dataMap);
		
		return appDTO;
	}
	
	
	public AppDTO updateCustomerProfile(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		
		String email=(String)dataMap.get("email");
		String phone=(String)dataMap.get("phone");
		String fullName=(String)dataMap.get("fullName");
		
		Customer sessCustomer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		Customer custDb=customerDao.getCustomerById(sessCustomer.getId());
		
		// update customer only if the email is not associated with any other with any  other account
		if(!CommonUtils.isEmpty(email))
		{
			boolean isEmailExist=customerDao.isEmailIdExistWithOtherAccount(email,sessCustomer.getId());
			if(isEmailExist)	
			{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(EMAIL_ALREADY_EXISTS))	;
			return appDTO;
			}
				
		}
		else if(CommonUtils.isEmpty(phone))
		{
			//phone check for empty value. It is not possible to update phone and email as null at the same time!
			
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(PHONE_EMAIL_CANNOT_EMPTY))	;
			return appDTO;
			
		}
			
		
		// update customer only if the phone doesn't exist with any  other account
		if(!CommonUtils.isEmpty(phone))
		{
			boolean isPhoneExist=customerDao.isPhoneExistWithOtherAccount(phone,sessCustomer.getId());
			if(isPhoneExist)	
			{
			appDTO.setResponseStatus(ResponseStatus.ERROR);	
			appDTO.setErrorMessage(getResourceMessage(MOBILE_ALREADY_EXISTS))	;
			return appDTO;
			}
				
		}
		custDb.setEmail(email);	
		custDb.setPhone(phone);	
		
		custDb.setFullName(fullName);
		custDb.setDateModified(DateUtils.getCurrentDate());
		customerDao.update(custDb);
		//update customer in session with password null for safety purpose 
		custDb.setPassword("");
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER,custDb);
		return appDTO;
	}
	
	
	
	public AppDTO updateProfilePassword(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		String password=(String)dataMap.get("oldPassword");
		String encPass=PasswordEncryptor.encrypt(password);
		String newPassword=(String)dataMap.get("newPassword");
		
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER); // This doesnt have password in it
		customer=customerDao.getCustomerById(customer.getId());
		if(customer!=null && customer.getPassword().equals(encPass))
		{
			customer.setPassword(PasswordEncryptor.encrypt(newPassword));
			customer.setDateModified(DateUtils.getCurrentDate());
			customerDao.update(customer);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage(PASSWORD_UPDATED));
		}
		else
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(WRONG_PASSWORD));
		}
		
		return appDTO;
		
	}
	
	
	private Map generateBeautyPreferenceResponse()
	{
		Map prefMap=new HashMap();
		try{
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		JSONObject prefObj=new JSONObject(customer.getBeautyPreference());
		
		
		prefMap.put("Hair Colour", prefObj.get("hairColour"));
		prefMap.put("Skin Colour", prefObj.get("skinColour"));
		StringBuilder hairTypes=new StringBuilder();
		JSONArray hairTypeArr=(JSONArray)prefObj.get("hairTypes");
		for(int i=0;i<hairTypeArr.length();i++)
		{
			hairTypes.append("<span>"+hairTypeArr.getString(i)+"</span>");
			//hairTypes.append(",");
		}

		prefMap.put("Hair Types",hairTypes.toString());
		
		
		StringBuilder skinConcerns=new StringBuilder();
		JSONArray skinConcernsArr=(JSONArray)prefObj.get("skinConcerns");
		for(int i=0;i<skinConcernsArr.length();i++)
		{
			skinConcerns.append("<span>"+skinConcernsArr.getString(i)+"</span>");
			//skinConcerns.append(",");
		}
		prefMap.put("Skin Concerns",skinConcerns.toString());
	
		}
		catch(JSONException je)
		{
			je.printStackTrace();
		}
		
		return prefMap;
		
	}

	public DashboardDao getDashboardDao() {
		return dashboardDao;
	}

	public void setDashboardDao(DashboardDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}


	public CustomerDao getCustomerDao() {
		return customerDao;
	}


	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	
}
