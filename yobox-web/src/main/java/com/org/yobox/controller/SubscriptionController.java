package com.org.yobox.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.common.Validation;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerAddress;
import com.org.yobox.service.SubscriptionService;
import com.org.yobox.util.CommonUtils;

@Controller
public class SubscriptionController extends BaseController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@RequestMapping("/chooseSubscription")
	public ModelAndView chooseSubscription(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("subscription");
		try
		{
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			appDTO.setDataMap(dataMap);
			appDTO=subscriptionService.listSubscriptionCycles(appDTO);
			mv.addAllObjects(appDTO.getDataMap());
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return mv;
	}
	
	
	@RequestMapping(value="/deliveryAddress", method = RequestMethod.POST)
	public ModelAndView deliveryAddress(@ModelAttribute CustomerAddress customerAddress,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("deliveryAddress");
		try
		{
			int subscriptionCycleId=Integer.parseInt(request.getParameter("subscriptionCycle"));
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			if(!CommonUtils.isEmpty(customer.getEmail()))
			{
				customerAddress.setEmail(customer.getEmail());	
			}
			if(!CommonUtils.isEmpty(customer.getPhone()))
					{
				customerAddress.setMobile(customer.getPhone());	
			}
			
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			dataMap.put("subscriptionCycle",subscriptionCycleId);
			appDTO.setDataMap(dataMap);
			appDTO=subscriptionService.populateAddressPrerequisites(appDTO);
			mv.addAllObjects(appDTO.getDataMap());
		}
		catch(NumberFormatException ne)
		{
			mv.setViewName("redirect:/chooseSubscription.htm");
			setError_message(getResourceMessage(something_wrong),true);
			ne.printStackTrace();
		}
		catch(Exception e)
		{
			mv.setViewName("redirect:/chooseSubscription.htm");
			setError_message(getResourceMessage(something_wrong),true);
			e.printStackTrace();	
		}
		return mv;
	}

	
	
	@RequestMapping(value="/confirmDeliveryAddress", method = RequestMethod.POST)
	public ModelAndView confirmDeliveryAddress(@ModelAttribute CustomerAddress customerAddress,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("deliveryAddress");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		appDTO.setDataMap(dataMap);
		
		
	try
	{
		// prerequisites for subscriptions
		appDTO=subscriptionService.populateAddressPrerequisites(appDTO);
		dataMap.putAll(appDTO.getDataMap());
		
		int subscriptionCycleId=Integer.parseInt(request.getParameter("subscriptionCycle"));
		dataMap.put("customerAddress",customerAddress);
		dataMap.put("subscriptionCycle",subscriptionCycleId);
		
			 if(CommonUtils.isEmpty(customerAddress.getFullName())||
				CommonUtils.isEmpty(customerAddress.getLocality())||					
				CommonUtils.isEmpty(customerAddress.getAddress())||
				CommonUtils.isEmpty(customerAddress.getPinCode())||
				CommonUtils.isEmpty(customerAddress.getMobile())					
		)
		{
			setError_message(getResourceMessage(ALL_FIELDS_REQUIRED), false);
		}
		else if(customerAddress.getState().getId()==0)
		{
			setError_message(getResourceMessage(ALL_FIELDS_REQUIRED), false);
			
		}
		else if(!Validation.isValidMobile(customerAddress.getMobile()))
		{
			setError_message(getResourceMessage(INVALID_MOBILE), false);
		}
		else
		{
			appDTO.setDataMap(dataMap);
			appDTO=subscriptionService.addSubscription(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			//if success values not required in datamap
			mv.setViewName("redirect:/initPayment.htm");
			dataMap.clear();
		}
		else
		{
			setError_message(appDTO.getErrorMessage(), false);
		}
		
		}
		
			 mv.addAllObjects(dataMap);
				
	}
	catch(Exception e)
	{
		e.printStackTrace();
		setError_message(getResourceMessage(something_wrong), false);

	}
	
		return mv;
	}
	
	public SubscriptionService getSubscriptionService() {
		return subscriptionService;
	}

	public void setSubscriptionService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}
	
	}
