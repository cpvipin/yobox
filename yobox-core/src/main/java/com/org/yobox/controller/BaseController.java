package com.org.yobox.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.AbstractRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.org.yobox.beans.Page;
import com.org.yobox.common.AppConstants;
import com.org.yobox.context.ApplicationContext;
import com.org.yobox.context.BeanConstants;
import com.org.yobox.util.ViewTools;


/**
 * @author(name="vipin c p") 
 */

public class BaseController implements Controller,BeanConstants,AppConstants{
	
	private Page page=new Page();
	
	private FlashMap flashmap;
	
	private static ResourceBundle appMessage;
	
	private static Properties appProps=new Properties();
	
	private static Properties appSystemProps=new Properties();
	
	
	private static ViewTools viewTools=new ViewTools();
	
	
	
	
	static{
		try{
	appMessage = ResourceBundle.getBundle("messages",
					Locale.ENGLISH);
	//appProps.load(BaseController.class.getResourceAsStream(APP_PROPERTIES));
	InputStream sysInput = new FileInputStream(System.getProperty(APP_SYSTEM_PROPERTIES));
	appSystemProps.load(sysInput);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public static String getResourceMessage(String messageKey) {
		return appMessage.getString(messageKey);
	}
	
	public static ApplicationContext getApplicationContext()
	{
		
		return ApplicationContext.getApplicationContext();
		
		
	}
	
	
	
	protected HttpSession getHttpSession() 
	{
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		
		
		return session;
	}
	
	
	/**
	 * @param info_message
	 */
	
	public void setInfo_message(String info_message,boolean redirect) 
	{
	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	if(redirect)
	{
		flashmap=RequestContextUtils.getOutputFlashMap(attr.getRequest());
		flashmap.put("info_message", info_message);
	}
	else
	{
	attr.getRequest().setAttribute("info_message",info_message );
	}
	
	}

	public static Object getDAOBean(String beanId) {
		return getApplicationContext().getBean(beanId);
	}

		
	
	
	
	/**
	 * @param info_message
	 */
	
	public void setError_message(String error_message,boolean redirect) 
	{
	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	
	if(redirect)
	{
		flashmap=RequestContextUtils.getOutputFlashMap(attr.getRequest());
		flashmap.put("error_message",error_message );
	}
	else
	{
	attr.getRequest().setAttribute("error_message",error_message  );
	}
	
	}
	
	public static ResourceBundle getAppMessage() {
		return appMessage;
	}
	
	public static String getAppProperties(String key) {
		return appProps.getProperty(key, "");
	}


	public static String getAppSystemProperties(String key) {
		return appSystemProps.getProperty(key, "");
	}

	
	/*getters and setters*/
	
	@ModelAttribute
	public static ViewTools getViewTools() {
		return viewTools;
	}
	@ModelAttribute
	public static void setViewTools(ViewTools viewTools) {
		BaseController.viewTools = viewTools;
	}
	
	
	@ModelAttribute
	public Page getPage() {
		return page;
	}
	@ModelAttribute
	public void setPage(Page page) {
		this.page = page;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
