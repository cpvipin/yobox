package com.org.yobox.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.model.Customer;
import com.org.yobox.service.BeautyQuizService;


public class BeautyQuizServiceImpl extends BaseServiceImpl implements BeautyQuizService {
	
	private CustomerDao customerDao;
	
	/* 
	 * (non-Javadoc)
	 * @see com.org.yobox.service.BeautyQuizService#addBeautyPreference(com.org.yobox.AppDTO)
	 * 
	 * Preference is adding to session only(If the user is not logged in)
	 * data is not persisting to database. This will be done once the user is signed up or 
	 * logged in from sign up page by clicking already have an account link.
	 * If the user is already logged in and then reached here via edit beauty preference 
	 * then need to update the values for the particular customer
	 */
	public AppDTO addBeautyPreference(AppDTO appDTO)
	{
		
		Map dataMap=appDTO.getDataMap();
		try{
			String skinColour=(String)dataMap.get("skinColour");
			String hairColour=(String)dataMap.get("hairColour");
			String[] skinConcerns=(String[])dataMap.get("skinConcerns");
			String[] hairTypes=(String[])dataMap.get("hairTypes");
			
			JSONArray skinConcArr=new JSONArray();
			if(skinConcerns!=null)
			{
			for(String s:skinConcerns)
			{
				skinConcArr.put(s);
			}
			}
			
			JSONArray hairTypesArr=new JSONArray();
			if(hairTypes!=null)
			{
			for(String h:hairTypes)
			{
				hairTypesArr.put(h);
			}
			}
			
			
			JSONObject preferenceObj = new JSONObject();
			preferenceObj.put("skinColour", skinColour);
			preferenceObj.put("hairColour", hairColour);
			preferenceObj.put("skinConcerns", skinConcArr);
			preferenceObj.put("hairTypes", hairTypesArr);
			
			Customer customer=(Customer)getHttpSession().
								getAttribute(LOGGED_IN_CUSTOMER);
			
			/*If updating the values of beauty preference from dashboard
			 * control goes here. 
			 */
			if(customer!=null)
			{
				customer=customerDao.getCustomerById(customer.getId());
				customer.setBeautyPreference(preferenceObj.toString());
				customerDao.update(customer);
				
				//Update customer in session
				customer.setPassword("");
				getHttpSession().setAttribute(LOGGED_IN_CUSTOMER, customer);
			}
			else
			{
				getHttpSession().setAttribute(BEAUTY_PREFERENCE_SESS, preferenceObj.toString());
			}
			appDTO.setInfoMessage(getResourceMessage(BEAUTY_PROFILE_UPDATED));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(something_wrong));
			e.printStackTrace();
		}
		
		return appDTO;
	}


	public CustomerDao getCustomerDao() {
		return customerDao;
	}



	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
}
