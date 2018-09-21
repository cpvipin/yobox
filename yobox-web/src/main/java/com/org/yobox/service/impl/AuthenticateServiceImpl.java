package com.org.yobox.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.aop.EmailNotifier;
import com.org.yobox.common.Gender;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.common.Validation;
import com.org.yobox.context.ApplicationContext;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.model.Customer;
import com.org.yobox.model.NewsLetter;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.notify.SMSSender;
import com.org.yobox.service.AuthenticateService;
import com.org.yobox.util.CommonUtils;
import com.org.yobox.util.DateUtils;
import com.org.yobox.util.PasswordEncryptor;

public class AuthenticateServiceImpl extends BaseServiceImpl implements AuthenticateService {
	
	private CustomerDao customerDao;
	
	
	
	public AppDTO subscribeNewsLetter(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		String email=(String)dataMap.get("email");
		
		NewsLetter nl=new NewsLetter();
		nl.setEmail(email);
		nl.setDateAdded(DateUtils.getCurrentDate());
		nl.setDateModified(DateUtils.getCurrentDate());
		nl.setActiveStatus(true);
		customerDao.add(nl);
		
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setInfoMessage(getResourceMessage(NEWSLETTER_SUBSCRIBED));
		
		return appDTO;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.org.yobox.service.AuthenticateService#signin(com.org.yobox.AppDTO)
	 * This method will signin the user by checking the user name and password provided and update the beauty preference if set.
	 * Also the beauty preference will be removed from the session once persist in the database.
	 * 
	 */
		public AppDTO signin(AppDTO appDTO)
		{
			try{
			appDTO=customerDao.customerSignIn(appDTO);
			Customer customer = (Customer) appDTO.getDataMap().get("CUSTOMER");
			
			if(customer!=null && customer.getId().intValue()>0)
			{
			
			/*
			 * If beauty preference set then update preference.
			 * 
			 */
			updateCustomerBeautyPreference(customer);
			setCustomerSession(customer);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			else
			{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(USER_NAME_PASSWORD_MISMATCH));
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(USER_NAME_PASSWORD_MISMATCH));

			}
			return appDTO;
		}
		
/*
 * (non-Javadoc)
 * @see com.org.yobox.service.AuthenticateService#signup(com.org.yobox.AppDTO)
 * 
 * This method will let the user signed up with the credentials provided.
 * Update the user beauty preferences if already set in session.
 */
		public AppDTO signup(AppDTO appDTO)
		{
			Map dataMap=appDTO.getDataMap();
			String userId=(String) dataMap.get("USERID");
			String password=(String) dataMap.get("PASSWORD");
			Customer dupCust=customerDao.getCustomerByUserId(userId);
			
			if(dupCust!=null)
			{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(USER_ALREADY_EXIST));
				return appDTO;
			}
			
			Customer customer=new Customer();
			if(Validation.isValidEmail(userId))
			{
				customer.setEmail(userId);
			}
			else
			{
				customer.setPhone(userId);
			}
			
			customer.setActiveStatus(true);
			customer.setDateAdded(DateUtils.getCurrentDate());
			customer.setDateModified(DateUtils.getCurrentDate());
			customer.setPassword(password);
			customer.setGender(Gender.FEMALE.getValue());
			customerDao.add(customer);
			
			updateCustomerBeautyPreference(customer);
			setCustomerSession(customer);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			return appDTO;
		}
		
		
		/**
		 * This method checks whether the token already exists, if not new token will be gernate and then it will send via 
		 * registered email or mobile.
		 * @param month
		 * @param year
		 * @return
		 */

		public AppDTO generatePasswordLink(AppDTO appDTO)
		{
			Map dataMap=appDTO.getDataMap();
			String userId=(String)dataMap.get("userId");
			PasswordRequestHistory passHis=new PasswordRequestHistory();
			Customer customer=customerDao.getCustomerByUserId(userId);
			
			if(customer!=null)
			{
			
			String token="";	
			boolean tokenExists=true;
			
			
			while(tokenExists)
			{
				token=PasswordEncryptor.generateRandomPassword();
				tokenExists=customerDao.isTokenExists(token);
			}
			
			//reset all existing tokens
			customerDao.resetAllPasswordToken(customer);
			
			passHis.setDateAdded(DateUtils.getCurrentDate());
			passHis.setDateModified(DateUtils.getCurrentDate());
			passHis.setCustomer(customer);
			passHis.setActiveStatus(true);
			passHis.setToken(token);
			passHis.setRequestedUserId(userId);
			customerDao.addPasswordResetToken(passHis);
			if(Validation.isValidEmail(userId))
			appDTO.setInfoMessage(getResourceMessage(PASSWORD_RESET_SENT_EMAIL));
			else if(Validation.isValidMobile(userId))
				appDTO.setInfoMessage(getResourceMessage(PASSWORD_RESET_SENT_MOBILE));
				
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			else
			{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(USER_DOESNOT_EXIST));
			}
		return appDTO;	
		}
		
		
		
		public AppDTO validatePasswordToken(AppDTO appDTO)
		{
			Map dataMap=appDTO.getDataMap();
			String token=(String)dataMap.get("token");
			String userId=(String)dataMap.get("userId");
			PasswordRequestHistory passObj=customerDao.getPasswordRequestHistoryByToken(token);
			
			if(passObj!=null && passObj.getRequestedUserId().equals(userId))
			{
				Date currentDate=DateUtils.getCurrentDate();
				Date tokenDate=passObj.getDateAdded();
				
				if(DateUtils.differenceInDays(tokenDate,currentDate)>2)
				{
					passObj.setActiveStatus(false);
					customerDao.update(passObj);
					
					appDTO.setResponseStatus(ResponseStatus.ERROR);
					appDTO.setErrorMessage(getResourceMessage(PASSWORD_LINK_EXPIRED));
				}
				else
				{
					
					appDTO.setResponseStatus(ResponseStatus.SUCCESS);
					appDTO.setInfoMessage(getResourceMessage(RESET_YOUR_PASSWORD));
				}
						
			}
			else
			{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(PASSWORD_LINK_EXPIRED));
			
			}
			
			return appDTO;
		}
		
		
	public AppDTO updatePasswordByToken(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		PasswordRequestHistory passHistObj=(PasswordRequestHistory)dataMap.get("passwordRequestHistory");
		passHistObj=customerDao.getPasswordRequestHistoryByToken(passHistObj.getToken());
		String password=(String)dataMap.get("password");
		
		if(passHistObj!=null)
		{
			Date currentDate=DateUtils.getCurrentDate();
			Date tokenDate=passHistObj.getDateAdded();
			
			if(DateUtils.differenceInDays(tokenDate,currentDate)>2)
			{

				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(something_wrong));
				
			}
			else
			{
				Customer customer=customerDao.getCustomerByUserId(passHistObj.getRequestedUserId());
				if(customer!=null)
				{
					customer.setPassword(PasswordEncryptor.encrypt(password));
					customerDao.update(customer);
					
					passHistObj.setActiveStatus(false);
					customerDao.update(passHistObj);
					appDTO.setResponseStatus(ResponseStatus.SUCCESS);
					appDTO.setInfoMessage(getResourceMessage(PASSWORD_UPDATED));
				}
			}
		}
		
		
		
		return appDTO;
	}
	
	
	/*
	 * This method will update the customer beauty preference if set in session
	 */
	private void updateCustomerBeautyPreference(Customer customer)
	{
		String preference=(String)getHttpSession().getAttribute(BEAUTY_PREFERENCE_SESS);
		if(!CommonUtils.isEmpty(preference))
		{
		customer.setBeautyPreference(preference);
		customer.setDateModified(DateUtils.getCurrentDate());
		customerDao.update(customer);
		getHttpSession().removeAttribute(BEAUTY_PREFERENCE_SESS);
		}
		
		
	}
	
	
	private void validateSession(Customer customer) {
		try {
			HttpSession aSession = getHttpSession();
			HttpSession oldSess = (HttpSession) ApplicationContext.getActiveSessionMap().get(customer.getId());
			if (oldSess != null	&& !CommonUtils.nullSafeObjectEquals(oldSess.getId(),aSession.getId())) {
				oldSess.invalidate();
			}
			ApplicationContext.getActiveSessionMap().put(customer.getId(),aSession);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void setCustomerSession(Customer customer)
	{
		//set password  to null for avoiding vulnerablity
		customer.setPassword(null);
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER,customer);
		validateSession(customer);
	
}
	
	



	public CustomerDao getCustomerDao() {
		return customerDao;
	}



	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
}