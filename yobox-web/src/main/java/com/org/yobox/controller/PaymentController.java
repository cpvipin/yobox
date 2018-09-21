package com.org.yobox.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RespectBinding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.service.PaymentService;



@Controller
public class PaymentController extends BaseController {
	
	@Autowired
	private PaymentService paymentService;

	
	@RequestMapping("/initPayment")
	public ModelAndView paymentInit(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("paymentInit");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try
		{
			appDTO.setDataMap(dataMap);
			appDTO=paymentService.initializePayment(appDTO);
			dataMap=appDTO.getDataMap();
			mv.addAllObjects(dataMap);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(getResourceMessage(something_wrong), true);
				mv.setViewName("redirect:/chooseSubscription.htm");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setError_message(getResourceMessage(something_wrong), true);
			mv.setViewName("redirect:/chooseSubscription.htm");
		
		}

		return mv;
	}
	

	
	
	@RequestMapping("/finPayment")
	public ModelAndView paymentFinalize(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("redirect:/dashboard.htm");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try
		{
			String encResp= request.getParameter("encResp");
			dataMap.put("encResp",encResp);
			appDTO.setDataMap(dataMap);
			appDTO=paymentService.finalizePayment(appDTO);
			dataMap=appDTO.getDataMap();
			dataMap.clear();
			
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{

				mv.setViewName("redirect:/chooseSubscription.htm");
				setError_message(appDTO.getErrorMessage(), true);
			}
			else
			{
				mv.setViewName("redirect:/dashboard.htm");
				setInfo_message(appDTO.getInfoMessage(), true);
			}
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(PAYMENT_EXCEPTION_OCCURED), true);
			mv.setViewName("redirect:/chooseSubscription.htm");
			e.printStackTrace();
		}

		return mv;
	}

	
	
	/* getters and setters */


	public PaymentService getPaymentService() {
		return paymentService;
	}


	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	

	
	
	
	
	
}
