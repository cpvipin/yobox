package com.org.yobox.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.apache.log4j.pattern.LogEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.org.yobox.common.AppConstants;
import com.org.yobox.context.ApplicationContext;
import com.org.yobox.context.BeanConstants;
import com.org.yobox.model.Customer;




public class ViewTools implements BeanConstants,AppConstants {
	
	
	private static final SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	public static String getSubStr(String aString, int length)
	{
		
		if(!CommonUtils.isEmpty(aString) && (aString.length()>length))
		{
			return  aString.substring(0, length)+"..";
		}
		
		return aString;
	}

	public static String formatDate(Object aDate) {
		String dt = "";
		if (aDate != null) {
			try {
				dt = simpleDF.format(aDate);
			} catch (Exception e) {
				// DO nothing
			}
		}
		return dt;
	}
	
	
	
	
	
	private static Object getDAOBean(String beanId) {
		return ApplicationContext.getApplicationContext().getBean(beanId);
	}
	
	public static boolean isCustomerLoggedin()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Customer customer=(Customer)session.getAttribute(LOGGED_IN_CUSTOMER);
		if(customer!=null)
			return true;
		
		return false;
	}
	
	public static String getLoggedInUserName()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Customer customer=(Customer)session.getAttribute(LOGGED_IN_CUSTOMER);
		String userName="user";
		if(!CommonUtils.isEmpty(customer.getFullName()))
		{
			userName=customer.getFullName();
		}
		
		return userName;
		
	}
	
	
	public int getCollectionSize(Collection aColl)
	{
		
		return aColl.size();
		
		
	}
	
	public static String getSubscriptionMonth()
	{
		
		return AppUtils.getSubscriptionName();
	}
	
	
	
}
