package com.org.yobox.service.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.org.yobox.common.AppConstants;
import com.org.yobox.context.ApplicationContext;
import com.org.yobox.context.BeanConstants;
import com.org.yobox.controller.BaseController;
import com.org.yobox.service.BaseService;



public class BaseServiceImpl implements BaseService,BeanConstants,AppConstants {
	
	private static ResourceBundle resourceBundle;
	
	
	protected HttpSession getHttpSession() 
	{
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		
		
		return session;
	}
		
	public Object getDAOBean(String beanId) 
	{
		return getApplicationContext().getBean(beanId);
	}
	
	/**
	 * @return Returns the applicationContext.
	 */
	public static ApplicationContext getApplicationContext() 
	{
		
			
		return ApplicationContext.getApplicationContext();
	}
	
	protected String getResourceMessage(String messageKey) {
		return getDTOMessage().getString(messageKey);
	}
	
	protected String getAppProperties(String key)
	{
		return BaseController.getAppProperties(key);
	}
	

	protected String getAppSystemProperties(String key)
	{
		return BaseController.getAppSystemProperties(key);
	}
	
	
	//TODO
	/* for multi language support get session bundle*/
	public ResourceBundle getDTOMessage() {
			resourceBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
			return resourceBundle;
	}
	
	
}
