package com.org.yobox.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.context.TransactionManager;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.dao.SubscriptionDao;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerAddress;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.State;
import com.org.yobox.model.SubscriptionCycles;
import com.org.yobox.service.BeautyQuizService;
import com.org.yobox.service.SubscriptionService;
import com.org.yobox.util.AppUtils;
import com.org.yobox.util.DateUtils;


public class SubscriptionServiceImpl extends BaseServiceImpl implements SubscriptionService {
	
	private SubscriptionDao subscriptionDao;

	

	public AppDTO listSubscriptionCycles(AppDTO appDTO) {
		
		Collection subsColl=subscriptionDao.getAllActiveSubscriptionCycles();
		
		Map dataMap=appDTO.getDataMap();
		dataMap.put("subscriptionColl", subsColl);
		appDTO.setDataMap(dataMap);
		
		return appDTO;
	}
	
	
	public AppDTO populateAddressPrerequisites(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		Collection stateColl=subscriptionDao.findAll(State.class);
		dataMap.put("stateColl",stateColl);
		appDTO.setDataMap(dataMap);
		return appDTO;
	}
	
	
	public AppDTO addSubscription(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		try{
			CustomerAddress customerAddress=(CustomerAddress)dataMap.get("customerAddress");
			int stateId=customerAddress.getState().getId();
			int subscriptionCycleId=(Integer) dataMap.get("subscriptionCycle");
			State state=(State) subscriptionDao.getStateById(stateId);
			
			SubscriptionCycles subscriptionCycle=subscriptionDao.getSubscriptionCycleById(subscriptionCycleId);
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			Date currentDate=DateUtils.getCurrentDate();
			customerAddress.setCustomer(customer);
			customerAddress.setState(state);
			
			CustomerSubscription custSubs=new CustomerSubscription();
			custSubs.setCustomer(customer);
			custSubs.setCustomerAddress(customerAddress);
			custSubs.setDateAdded(currentDate);
			custSubs.setDateModified(currentDate);
			custSubs.setTotalAmount(subscriptionCycle.getPrice());
			custSubs.setStartDate(currentDate);
			custSubs.setEndDate(DateUtils.getFutureDateAfterMonth(currentDate, subscriptionCycle.getDurationMonths()));
			custSubs.setSubscriptionCycle(subscriptionCycle);
			
			TransactionManager.begin();
			TransactionManager.txCreate(customerAddress);
			TransactionManager.txCreate(custSubs);
			TransactionManager.commit();
			
			getHttpSession().setAttribute(SUBSCRIPTION_IN_SESSION,custSubs);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
			Collection stateColl=subscriptionDao.findAll(State.class);
			dataMap.put("stateColl",stateColl);
			TransactionManager.rollback();
			e.printStackTrace();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(something_wrong));
			
			
		}
		
		return appDTO;
	}



	public SubscriptionDao getSubscriptionDao() {
		return subscriptionDao;
	}



	public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}
	
	
}
