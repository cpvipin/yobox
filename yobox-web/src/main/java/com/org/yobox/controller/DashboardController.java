package com.org.yobox.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.common.Validation;
import com.org.yobox.model.Customer;
import com.org.yobox.service.BeautyQuizService;
import com.org.yobox.service.DashboardService;
import com.org.yobox.util.CommonUtils;
import com.org.yobox.util.DateUtils;

@Controller
public class DashboardController extends BaseController {
	

	private static Logger logger = (Logger) Logger
			.getInstance(DashboardController.class);
	
	@Autowired
	private DashboardService dashboardService;
	
	
	
	
	/*
	 * user logged in will be checking by interceptor
	 */
	@RequestMapping(value="/dashboard")
	public ModelAndView dashboard(HttpServletRequest request)
	{
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		ModelAndView mv=new ModelAndView("dashboard");
		try{
		appDTO.setDataMap(dataMap);		
		appDTO=dashboardService.populateDashboardElements(appDTO);
		mv.addAllObjects(appDTO.getDataMap());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
		
	}
	
	/*
	 * update all values in customer
	 * then redirect to dashboard
	 */
	@RequestMapping(value="/updateCustomerProfile", method = RequestMethod.POST)
	public ModelAndView updateCustomerProfile(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("redirect:/dashboard.htm");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
	try{
		String email=request.getParameter("email");
		String fullName=request.getParameter("fullName");
		String phone=request.getParameter("phone");
		
		
		dataMap.put("email",email);
		dataMap.put("fullName",fullName);
		dataMap.put("phone",phone);
		appDTO.setDataMap(dataMap);
		
	
		
		appDTO=dashboardService.updateCustomerProfile(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(), true);
			
		}
		else
		{	
		setInfo_message(getResourceMessage(PROFILE_UPDATED),true);
		}
		
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		setError_message(getResourceMessage(something_wrong), true);
	}
	return mv;	
	}
	
	
	
	
	@RequestMapping(value="/changeOldPassword", method = RequestMethod.POST)
	public ModelAndView changeOldPassword(HttpServletRequest request)
	{
		
		ModelAndView mv=new ModelAndView("redirect:/dashboard.htm#chg_pass");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try{
			
			String oldPassword=request.getParameter("oldPassword");
			String newPassword=request.getParameter("newPassword");
			String confirmPassword=request.getParameter("confirmPassword");
			
			if(CommonUtils.isEmpty(oldPassword)||CommonUtils.isEmpty(newPassword)||CommonUtils.isEmpty(confirmPassword))
			{
				setError_message(getResourceMessage(ALL_FIELDS_REQUIRED), true);
				return mv;
					
			}
			else if(!newPassword.equals(confirmPassword))
			{
			setError_message(getResourceMessage(PASSWORD_DONOT_MATCH), true);
			return mv;
			}
			else
			{
			dataMap.put("oldPassword",oldPassword);
			dataMap.put("newPassword", newPassword);
			appDTO.setDataMap(dataMap);
			appDTO=dashboardService.updateProfilePassword(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				
				setInfo_message(appDTO.getInfoMessage(), true);
			}
			else
			{
				setError_message(appDTO.getErrorMessage(), true);
			}
				
				
			}
				
			
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(something_wrong),true);
			e.printStackTrace();
		}
		
		return mv;
	
		
	}
	
	
	
	
	
	// getters and setters

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	
	
	}
